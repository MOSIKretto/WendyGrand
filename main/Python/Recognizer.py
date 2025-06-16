from Voiceover import ActionsVoiceover
import sounddevice as sd
import subprocess
import asyncio
import vosk
import time
import re


lastСommand = ""
commandTimer = 0
q = asyncio.Queue(maxsize=1500)
model = vosk.Model("../WendyGrand/main/Resources/model_small")
samplerate = int(sd.query_devices(sd.default.device[0], 'input')['default_samplerate'])
removeWord = re.compile(r"\b(привет|чем|могу|помочь|я|здравствуйте|здесь|естественно|застав|засос)\b", re.IGNORECASE)
WardHandler = "../WendyGrand/main/Java/Handlers/WordHandler.java"


# Приветствие
ActionsVoiceover.HelloVoiceover()

# Очистка текста от лишних слов
def clearText(text):
    return removeWord.sub("", text).strip()

# Прекращение прослушки
def goodbye(text):
    print(f"Распознано: {text}")
    ActionsVoiceover.ByeVoiceover()
    raise asyncio.CancelledError("Программа завершена")

# Обработка команды
def handleCommand(text):
    global lastСommand, commandTimer

    # Команды для завершения работы
    stopCommands = ("венди пока", "среда пока", "вэнди пока", "вэнди закройся", "венди закройся", "среда закройся")
    if any(text.startswith(prefix) for prefix in stopCommands):
        goodbye(text)
        return

    # Поиск ключевых слов
    match = re.search(r"\b(венди|вэнди|среда)\b", text, re.IGNORECASE)
    if match:
        recognizedText = text[match.start():].strip()
        print(f"Распознано: {recognizedText}")

        if len(recognizedText) > 5:
            subprocess.run(["java", WardHandler, recognizedText])
        else:
            lastСommand = recognizedText
            commandTimer = time.time()
            ActionsVoiceover.CallHelloVoiceover()
        return

    # Обработка последней команды в течение 10 секунд
    if lastСommand and time.time() - commandTimer <= 10:
        recognizedText = clearText(text)
        if recognizedText:
            if recognizedText in ("пока", "закройся"):
                goodbye(recognizedText)
            else:
                print(f"Распознано: {recognizedText}")
                subprocess.run(["java", WardHandler, recognizedText])
                lastСommand = ""

# Прослушка и распознавание речи
async def Recognizer(q):
    rec = vosk.KaldiRecognizer(model, samplerate)

    while True:
        data = await q.get()
        if rec.AcceptWaveform(data):
            text = rec.Result()[14:-3]
            handleCommand(text.lower())
        else:
            rec.PartialResult()

# Подключение к микрофону
async def captureAudio(q):
    def callback(indata, frames, time, status):
        try:
            q.put_nowait(bytes(indata))
        except asyncio.QueueFull:
            pass
        
    with sd.RawInputStream(samplerate=samplerate, blocksize=3000, device=sd.default.device[0], dtype='int16', channels=1, callback=callback):
        while True:
            await asyncio.sleep(0.01)

# Запуск прослушки и передачи с микрофона в текст асинхронно
async def main():
    recognizerTask = asyncio.create_task(Recognizer(q))
    captureTask = asyncio.create_task(captureAudio(q))

    try:
        await asyncio.gather(recognizerTask, captureTask)
    except asyncio.CancelledError:
        recognizerTask.cancel()
        captureTask.cancel()
        await asyncio.gather(recognizerTask, captureTask, return_exceptions=True)

if __name__ == "__main__":
    asyncio.run(main())