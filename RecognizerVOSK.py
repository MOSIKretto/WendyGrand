'''
* *Recognizer*
*
*RU Слушает и передает значение в Java_Dictionary
*-------------------------------------------------------------
*En Listens and passes the value to Java_Dictionary
*
'''

# Это второй распознователь, работает оффлайн и к основному коду подключен лишь бы поиграться (работает через пень-колоду). 
# Папка model_small для этого распознователя.

import sounddevice as sd    
import vosk               
import queue
import subprocess
import sys
import os
from Voiceover import ActionsVoiceover

ActionsVoiceover.HelloVoiceover()

q = queue.Queue()

model = vosk.Model('model_small')
device = sd.default.device 
samplerate = int(sd.query_devices(device[0], 'input')['default_samplerate'])

def callback(indata, frames, time, status):
    q.put(bytes(indata))

#постоянная прослушка микрофона
with sd.RawInputStream(samplerate=samplerate, blocksize = 16000, device=device[0], dtype='int16', 
                       channels=1, callback=callback):

    rec = vosk.KaldiRecognizer(model, samplerate)

    while True:

        data = q.get()
        
        if rec.AcceptWaveform(data):

            data = (rec.Result())[14:-3]

            if (data[0:10] == "венди пока") or (data[0:10] == "среда пока") or (data[0:10] == "вэнди пока"):
                ActionsVoiceover.ByeVoiceover()
                sys.exit(0)
            elif (data[0:5] == "венди") or (data[0:5] == "среда") or (data[0:5] == "вэнди"):
                print("RecognizerVOSK:", data)
                subprocess.run(["java", "Java_Dictionary.java", data[6:]], stdout=sys.stdout,
                               stderr=sys.stdout, cwd=os.getcwd())
            else:
                pass
