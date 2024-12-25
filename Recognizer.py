from Voiceover import ActionsVoiceover
import sounddevice as sd
import subprocess
import asyncio
import vosk
import time
import re


ActionsVoiceover.HelloVoiceover()

q = asyncio.Queue(maxsize=1000)
model = vosk.Model("model_small")
samplerate = int(sd.query_devices(sd.default.device[0], 'input')['default_samplerate'])
last_command = ""
command_timer = 0
remove_word = re.compile(r"\b(привет|чем|могу|помочь|я|здравствуйте|здесь|естественно)\b", re.IGNORECASE)

# Прекращение прослушки vosk
def goodbye(text):
    print("Распознано:", text)
    ActionsVoiceover.ByeVoiceover()
    raise asyncio.CancelledError("Program finished")

# Проверка сказанного
def Checking(text):
    global last_command, command_timer

    if any(text.startswith(prefix) for prefix in ("венди пока", "среда пока", "вэнди пока", "вэнди закройся", "венди закройся", "среда закройся")):
        goodbye(text)
        return

    match = re.search(r"\b(венди|вэнди|среда)\b", text, re.IGNORECASE)
    if match:
        recognized_text = text[match.start():].strip()
        print("Распознано:", recognized_text)
        
        if len(recognized_text) > 5:
            subprocess.run(["java", "-cp", ".", "Java_Dictionary", recognized_text])
        else:
            last_command = recognized_text
            command_timer = time.time()
            ActionsVoiceover.CallHelloVoiceover()
        return

    if last_command and time.time() - command_timer <= 10:
        text = remove_word.sub("", text).strip()
        if text:
            if text in ("пока", "закройся"):
                goodbye(text)
            else:
                print("Распознано:", text)
                subprocess.run(["java", "-cp", ".", "Java_Dictionary", text])
                last_command = ""

# Прослушка
async def Recognizer(q):
    rec = vosk.KaldiRecognizer(model, samplerate)

    while True:
        data = await q.get()
        if rec.AcceptWaveform(data):
            text = rec.Result()[14:-3]
            Checking(text)
        else:
            rec.PartialResult()

# Подключение к микрофону
async def capture_audio(q):
    def callback(indata, frames, time, status):
        q.put_nowait(bytes(indata))

    with sd.RawInputStream(samplerate=samplerate, blocksize=3000, device=sd.default.device[0], dtype='int16', channels=1, callback=callback):
        while True:
            await asyncio.sleep(0.01)

# Запуск прослушки и передачи с микрофона в текст асинхронно
async def main():
    recognizer_task = asyncio.create_task(Recognizer(q))
    capture_task = asyncio.create_task(capture_audio(q))

    try:
        await asyncio.gather(recognizer_task, capture_task)
    except asyncio.CancelledError:
        recognizer_task.cancel()
        capture_task.cancel()
        await asyncio.gather(recognizer_task, capture_task, return_exceptions=True)

asyncio.run(main())
