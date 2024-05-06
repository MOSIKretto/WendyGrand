'''
* *Voiceover*
*
*RU Активирует звуковые файлы
*-------------------------------
*En Activates audio files
*
'''

from playsound import playsound
import sys
from config import AUDIO_DIR
from random import *


class ActionsVoiceover:

    @staticmethod
    def HelloVoiceover():

        executive_voice = randint(0, 1)

        if executive_voice == 0:
            playsound(AUDIO_DIR + "LaunchScriptsAndReadyToWork.mp3")
        else:
            playsound(AUDIO_DIR + "HellowStartWork.mp3")

    @staticmethod
    def ByeVoiceover():

        executive_voice = randint(0, 2)

        if executive_voice == 0:
            playsound(AUDIO_DIR + "ByeBye.mp3")
        elif executive_voice == 1:
            playsound(AUDIO_DIR + "GladToHelp.mp3")
        else:
            playsound(AUDIO_DIR + "Goodbye.mp3")

    @staticmethod
    def CallBrowserVoiceover():

        executive_voice = randint(0, 5)

        if executive_voice == 0:
            playsound(AUDIO_DIR + "BrowserOpen.mp3")
        elif executive_voice == 1:
            playsound(AUDIO_DIR + 'Doing.mp3')
        elif executive_voice == 2:
            playsound(AUDIO_DIR + 'OneMoment.mp3')
        elif executive_voice == 3:
            playsound(AUDIO_DIR + 'Second.mp3')
        elif executive_voice == 4:
            playsound(AUDIO_DIR + 'OpeningBrowser.mp3')
        else:
            playsound(AUDIO_DIR + "OpenItNow.mp3")

    @staticmethod
    def CallTelegramVoiceover():

        executive_voice = randint(0, 5)

        if executive_voice == 0:
            playsound(AUDIO_DIR + "OpenTelegram.mp3")
        elif executive_voice == 1:
            playsound(AUDIO_DIR + 'Doing.mp3')
        elif executive_voice == 2:
            playsound(AUDIO_DIR + 'OneMoment.mp3')
        elif executive_voice == 3:
            playsound(AUDIO_DIR + 'Second.mp3')
        elif executive_voice == 4:
            playsound(AUDIO_DIR + 'TelegramIsOpen.mp3')
        else:
            playsound(AUDIO_DIR + "OpenItNow.mp3")

    @staticmethod
    def CallVScodeVoiceover():

        executive_voice = randint(0, 6)

        if executive_voice == 0:
            playsound(AUDIO_DIR + "GoodLuckToTheJob.mp3")
        elif executive_voice == 1:
            playsound(AUDIO_DIR + 'Doing.mp3')
        elif executive_voice == 2:
            playsound(AUDIO_DIR + 'OneMoment.mp3')
        elif executive_voice == 3:
            playsound(AUDIO_DIR + 'Second.mp3')
        elif executive_voice == 4:
            playsound(AUDIO_DIR + 'IOpenVScode.mp3')
        elif executive_voice == 5:
            playsound(AUDIO_DIR + 'VScodeIsOpen.mp3')
        else:
            playsound(AUDIO_DIR + "OpenItNow.mp3")

    @staticmethod
    def CallStoresVoiceover():

        executive_voice = randint(0, 4)

        if executive_voice == 0:
            playsound(AUDIO_DIR + 'Doing.mp3')
        elif executive_voice == 1:
            playsound(AUDIO_DIR + "OpenItNow.mp3")
        elif executive_voice == 2:
            playsound(AUDIO_DIR + 'OneMoment.mp3')
        else:
            playsound(AUDIO_DIR + 'Second.mp3')
        

print("MESSAGE")
if len(sys.argv) > 1:
    print("ActionsVoiceover." + sys.argv[1] + "()")
    eval("ActionsVoiceover." + sys.argv[1] + "()")
    