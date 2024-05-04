'''
* *Recognizer*
*
*RU Слушает и передает значение в Java_Dictionary
*-------------------------------------------------------------
*En Listens and passes the value to Java_Dictionary
*
'''

import speech_recognition as sr
import sys
import subprocess
from AudioHelper.HelloVoice import hello
from AudioHelper.ByeVoice import bye
from config import SOURCE_DIR
import os

r = sr.Recognizer()


def listen():
    with sr.Microphone() as source:
        r.adjust_for_ambient_noise(source)
        audio = r.listen(source, phrase_time_limit=400, timeout=30)

        try:
            text = str(r.recognize_google(audio, language="ru-RU")).lower()

            if (text[0:10] == "венди пока") or (text[0:10] == "среда пока"):
                bye()
                sys.exit(0)
            elif (text[0:5] == "венди") or (text[0:5] == "среда"):
                complite = subprocess.run(["java", SOURCE_DIR[2:-1] + ".Java_Dictionary", text[6:]], stdout=sys.stdout,
                                           cwd=os.getcwd())
                print(complite)
                print(text)
            else:
                pass

        except sr.UnknownValueError:
            pass


hello()

while True:
    listen()
