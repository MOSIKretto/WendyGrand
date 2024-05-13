'''
* *Recognizer*
*
*RU Слушает и передает значение в Java_Dictionary
*-------------------------------------------------------------
*En Listens and passes the value to Java_Dictionary
*
'''

from Voiceover import ActionsVoiceover
import speech_recognition as sr
import subprocess
import sys
import os

r = sr.Recognizer()

def listen():
    with sr.Microphone() as source:
        r.adjust_for_ambient_noise(source)
        audio = r.listen(source, phrase_time_limit=400, timeout=30)

        try:
            text = str(r.recognize_google(audio, language="ru-RU")).lower()

            if (text[0:10] == "венди пока") or (text[0:10] == "среда пока"):
                ActionsVoiceover.ByeVoiceover()
                sys.exit(0)
            elif (text[0:5] == "венди") or (text[0:5] == "среда"):
                print("Recognizer:", text)
                subprocess.run(["java", "Java_Dictionary.java", text[6:]], stdout=sys.stdout, 
                               stderr=sys.stdout, cwd=os.getcwd())
            else:
                pass

        except sr.UnknownValueError:
            pass

ActionsVoiceover.HelloVoiceover()

while True:
    listen()
