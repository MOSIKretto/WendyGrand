'''
• *Recognizer*
*
*RU Слушает и передает значение в Java_Dictionary
*-------------------------------------------------------------
*En Listens and passes the value to Java_Dictionary
*
'''

from Voiceover import ActionsVoiceover
import sounddevice as sd
import subprocess
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

def callback(indata, frames, time, status):
    q.put(bytes(indata))

with sd.RawInputStream(samplerate=samplerate, blocksize=16000, device=device[0], dtype='int16', 
                       channels=1, callback=callback):
    rec = vosk.KaldiRecognizer(model, samplerate)

    while True:
        data = q.get()
        if rec.AcceptWaveform(data):
            text = rec.Result()[14:-3]
            
            '''ДОЛЖНА БЫТЬ ПЕРЕСТРОЕНА ЛОГИКА'''

            if text.startswith(("венди пока", "среда пока", "вэнди пока")):
                #print("Recognizer:", text)
                ActionsVoiceover.ByeVoiceover()
                subprocess.run(["pkill", "glava"])
                sys.exit(0)
            elif text.startswith(("венди", "среда", "вэнди")):
                if len(text) != 5:
                    #print("Recognizer:", text)
                    text = re.sub(r"венди|среда|вэнди", "", text)
                    subprocess.run(["java", "Java_Dictionary.java", text.strip()])
                else:
                    last_command = text
                    command_timer = time.time()
                    #print("Recognizer:", text)
                    ActionsVoiceover.CallHelloVoiceover()
            elif last_command and text != "" and time.time() - command_timer <= 10:
                text = re.sub(r"привет чем могу помочь|чем могу помочь|я могу помочь|могу помочь|здравствуйте|я здесь|привет|здесь", "", text)
                if text != "":
                    #print("Recognizer:", text)
                    subprocess.run(["java", "Java_Dictionary.java", text.strip()])
                    last_command = ""
