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

r = sr.Recognizer()

def Hello():
    nomber = random.randint(0, 1)

    if nomber == 0:
        playsound("ЗапускаюСкриптыИГотоваРаботать.mp3")
    else:
        playsound("ПриветНачиннаюРаботу.mp3")

def Bye():

    Executive_voice = random.randint(0, 2)

    if Executive_voice == 0:
        playsound('ПокаПока.mp3')
    elif Executive_voice == 1:
        playsound('РадаБылаПомочь.mp3')
    else:
        playsound('ДоСвидания.mp3')

def listen():
    
    with sr.Microphone() as source:
        r.adjust_for_ambient_noise(source)
        audio = r.listen(source, phrase_time_limit=400, timeout=30)

        try:
            text = str(r.recognize_google(audio, language="ru-RU")).lower()

            if text[0:10] == "венди пока":
                Bye()
                sys.exit(0)
            elif text[0:5] == "венди":
                subprocess.run(["java", "Java_Dictionary.java", text])
            else:
                pass

        except sr.UnknownValueError:
            pass

Hello()

while True:
    listen()
