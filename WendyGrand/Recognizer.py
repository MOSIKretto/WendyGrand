'''
* *Recognizer*
*
*RU Слушает и передает значение в Java_Dictionary
*-------------------------------------------------------------
*En Listens and passes the value to Java_Dictionary
*
'''

import random
import speech_recognition as sr
import sys
import subprocess
from playsound import playsound
from config import AUDIO_DIR

r = sr.Recognizer()


def hello():
    number = random.randint(0, 1)

    if number == 0:
        playsound(AUDIO_DIR + "LaunchScriptsAndReadyToWork.mp3")
    else:
        playsound(AUDIO_DIR + "HellowStartWork.mp3")


def bye():
    executive_voice = random.randint(0, 2)

    if executive_voice == 0:
        playsound(AUDIO_DIR + "ByeBye.mp3")
    elif executive_voice == 1:
        playsound(AUDIO_DIR + "GladToHelp.mp3")
    else:
        playsound(AUDIO_DIR + "Goodbye.mp3")


def listen():
    with sr.Microphone() as source:
        r.adjust_for_ambient_noise(source)
        audio = r.listen(source, phrase_time_limit=400, timeout=30)

        try:
            text = str(r.recognize_google(audio, language="ru-RU")).lower()

            if text[0:10] == "венди пока":
                bye()
                sys.exit(0)
            elif text[0:5] == "венди":
                subprocess.run(["java", "./Java_Dictionary.java", text], stdout=sys.stdout)
                print("done")
            else:
                pass

        except sr.UnknownValueError:
            pass


hello()

while True:
    listen()
