from Voiceover import ActionsVoiceover
import sounddevice as sd
import subprocess
import threading
import queue
import vosk
import time
import sys
import re


ActionsVoiceover.HelloVoiceover()

q = queue.Queue()
model = vosk.Model("model_small")
device = sd.default.device
samplerate = int(sd.query_devices(device[0], 'input')['default_samplerate'])
last_command = ""
command_timer = 0
remove_word = re.compile(r"привет|чем|могу|помочь|я|здравствуйте|здесь|естественно")


def goodbye(text):
    print("Распознано:", text)
    ActionsVoiceover.ByeVoiceover()
    sys.exit(0)

def Checking(text):
    global last_command, command_timer

    if text.startswith(("венди пока", "среда пока", "вэнди пока", "вэнди закройся", "венди закройся", "среда закройся")):
        goodbye(text)

    elif text.startswith(("венди", "среда", "вэнди")):
        if len(text) != 5:
            print("Распознано:", text)
            subprocess.run(["java", "Java_Dictionary.java", text])
            last_command = text
            command_timer = time.time()
        else:
            print("Распознано:", text)
            last_command = text
            command_timer = time.time()
            ActionsVoiceover.CallHelloVoiceover()

    elif last_command and time.time() - command_timer <= 10:
        text = remove_word.sub("", text).strip()
        if text:
            if (text == "пока") or (text == "закройс"):
                goodbye(text)
            else:
                print("Распознано:", text)
                subprocess.run(["java", "Java_Dictionary.java", text])
                last_command = ""

def Recognizer():
    rec = vosk.KaldiRecognizer(model, samplerate)

    while True:
        data = q.get()
        if rec.AcceptWaveform(data):
            text = rec.Result()[14:-3]
            Checking(text)
        else:
            rec.PartialResult()


def callback(indata, frames, time, status):
    q.put(bytes(indata))

Recognizer_thread = threading.Thread(target=Recognizer, daemon=True)
Recognizer_thread.start()

with sd.RawInputStream(samplerate=samplerate, blocksize=12000, device=device[0], dtype='int16',
                        channels=1, callback=callback):
    Recognizer_thread.join()