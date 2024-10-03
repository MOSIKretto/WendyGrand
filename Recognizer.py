'''
* *Recognizer*
*
*RU Слушает и передает значение в Java_Dictionary
*-------------------------------------------------------------
*En Listens and passes the value to Java_Dictionary
*
'''


from Voiceover import ActionsVoiceover
import vosk 
import sounddevice as sd
import queue
import subprocess
import sys
import os


ActionsVoiceover.HelloVoiceover()


q = queue.Queue()
model = vosk.Model('model_small')
device = sd.default.device
samplerate = int(sd.query_devices(device[0], 'input')['default_samplerate'])


def callback(indata, frames, time, status):
    q.put(bytes(indata))


with sd.RawInputStream(samplerate=samplerate, blocksize = 16000, device=device[0], dtype='int16', 
                       channels=1, callback=callback):
        rec = vosk.KaldiRecognizer(model, samplerate)

        while True:
            data = q.get()
            if rec.AcceptWaveform(data):
                text = rec.Result()[14:-3]
                #text = str(input()) #Тесты с клавиатуры
                if (text.startswith("венди пока")) or (text.startswith("среда пока")) or (text.startswith("вэнди пока")):
                    ActionsVoiceover.ByeVoiceover()
                    subprocess.run(["pkill", "glava"])
                    sys.exit(0)
                elif (text.startswith("венди")) or (text.startswith("среда")) or (text.startswith("вэнди")):
                    print("Recognizer:", text)
                    text = text.replace("венди", "")
                    text = text.replace("среда", "")
                    text = text.replace("вэнди", "")
                    subprocess.run(["java", "Java_Dictionary.java", text.strip()], stdout=sys.stdout, 
                                stderr=sys.stdout, cwd=os.getcwd())
                else:
                    pass