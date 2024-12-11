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
device = sd.default.device
samplerate = int(sd.query_devices(device[0], 'input')['default_samplerate'])
last_command = ""
command_timer = 0
remove_word = re.compile(r"\b(привет|чем|могу|помочь|я|здравствуйте|здесь|естественно)\b", re.IGNORECASE)

#прекращение прослушки vosk
def goodbye(text):
    print("Распознано:", text)
    ActionsVoiceover.ByeVoiceover()
    raise asyncio.CancelledError("Program finished")

#проверка сказанного
def Checking(text):
    global last_command, command_timer

    if any(text.startswith(prefix) for prefix in ("венди пока", "среда пока", "вэнди пока", "вэнди закройся", "венди закройся", "среда закройся")):
        goodbye(text)
        return

    if any(text.startswith(prefix) for prefix in ("венди", "среда", "вэнди")):
        if len(text) > 5:
            print("Распознано:", text)
            subprocess.run(["java", "-cp", ".", "Java_Dictionary", text])
        else:
            print("Распознано:", text)
            last_command = text
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

#прослушка
async def Recognizer(q):
    rec = vosk.KaldiRecognizer(model, samplerate)

    while True:
        data = await q.get()
        if rec.AcceptWaveform(data):
            text = rec.Result()[14:-3]
            Checking(text)
        else:
            rec.PartialResult()

#подключение к микро
async def capture_audio(q):
    def callback(indata, frames, time, status):
        try:
            q.put_nowait(bytes(indata))
        except asyncio.QueueFull:
            pass

    with sd.RawInputStream(samplerate=samplerate, blocksize=3000, device=device[0], dtype='int16',
                           channels=1, callback=callback):
        while True:
            await asyncio.sleep(0.05)

#запуск прослушки и передачи с микро в текст асенхронно
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