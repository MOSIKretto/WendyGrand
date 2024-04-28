'''
* *voiceover*
*
*RU Активирует звуковые файлы
*-------------------------------
*En Activates audio files
'''
import sys

from playsound import playsound
import sys
import os
sys.path.append(os.getcwd()+"/WendyGrand/")
from config import AUDIO_DIR
from random import *


class ActionsVoiceover:

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


print("MESSAGE")
eval("ActionsVoiceover."+sys.argv[1]+"()")
