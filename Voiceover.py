'''
* *Voiceover*
*
*RU Активирует звуковые файлы
*-------------------------------
*En Activates audio files
*
'''

from playsound3 import playsound
import sys
from config import AUDIO_DIR
from random import *

'''
Универсальные файлы озвучкиб которые подайдут к большенсву функций:
###################################################################
Doing.mp3
Done.mp3
OneMoment.mp3
OpenItNow.mp3
Second.mp3
AlwaysAPleasure.mp3
'''

class ActionsVoiceover:

    @staticmethod
    def HelloVoiceover():

        executive_voice = randint(0, 1)

        if executive_voice == 0:
            playsound(AUDIO_DIR + 'LaunchScriptsAndReadyToWork.mp3')
        else:
            playsound(AUDIO_DIR + 'HellowStartWork.mp3')

    @staticmethod
    def ByeVoiceover():

        executive_voice = randint(0, 2)

        if executive_voice == 0:
            playsound(AUDIO_DIR + 'ByeBye.mp3')
        elif executive_voice == 1:
            playsound(AUDIO_DIR + 'GladToHelp.mp3')
        else:
            playsound(AUDIO_DIR + 'Goodbye.mp3')


    #CallFunctionName

    @staticmethod
    def CallHelloVoiceover():

        executive_voice = randint(0, 2)

        if executive_voice == 0:
            playsound(AUDIO_DIR + 'HiHowCanIHelp.mp3')
        elif executive_voice == 1:
            playsound(AUDIO_DIR + 'ImHere.mp3')
        else:
            playsound(AUDIO_DIR + 'Hello.mp3')

    @staticmethod
    def CallBrowserVoiceover():

        executive_voice = randint(0, 4)

        if executive_voice == 0:
            playsound(AUDIO_DIR + 'BrowserOpen.mp3')
        elif executive_voice == 1:
            playsound(AUDIO_DIR + 'Doing.mp3')
        elif executive_voice == 2:
            playsound(AUDIO_DIR + 'AlwaysAPleasure.mp3')
        elif executive_voice == 3:
            playsound(AUDIO_DIR + 'OpeningBrowser.mp3')
        else:
            playsound(AUDIO_DIR + 'OpenItNow.mp3')

    @staticmethod
    def CallWebSearchVoiceover():
            
            executive_voice = randint(0, 5)

            if executive_voice == 0:
                playsound(AUDIO_DIR + 'OneMoment.mp3')
            elif executive_voice == 1:
                playsound(AUDIO_DIR + 'Second.mp3')
            elif executive_voice == 2:
                playsound(AUDIO_DIR + 'Done.mp3')
            elif executive_voice == 3:
                playsound(AUDIO_DIR + 'AskingOnTheInternet.mp3')
            elif executive_voice == 4:
                playsound(AUDIO_DIR + 'WillFindIt.mp3')
            else:
                playsound(AUDIO_DIR + 'LookingForYourRequestOnTheInternet.mp3')

    @staticmethod
    def CallYouTubeSearchVoiceover():
            
            executive_voice = randint(0, 5)

            if executive_voice == 0:
                playsound(AUDIO_DIR + 'OpenItNow.mp3')
            elif executive_voice == 1:
                playsound(AUDIO_DIR + 'Second.mp3')
            elif executive_voice == 2:
                playsound(AUDIO_DIR + 'Doing.mp3')
            elif executive_voice == 3:
                playsound(AUDIO_DIR + 'LookingForYourYouTubeRequest.mp3')
            elif executive_voice == 4:
                playsound(AUDIO_DIR + 'WillFindIt.mp3')
            else:
                playsound(AUDIO_DIR + 'TryingToFindYourYouTubeRequest.mp3')

    @staticmethod
    def CallTelegramVoiceover():

        executive_voice = randint(0, 3)

        if executive_voice == 0:
            playsound(AUDIO_DIR + 'OpenTelegram.mp3')
        elif executive_voice == 1:
            playsound(AUDIO_DIR + 'OneMoment.mp3')
        elif executive_voice == 2:
            playsound(AUDIO_DIR + 'Second.mp3')
        else:
            playsound(AUDIO_DIR + 'TelegramIsOpen.mp3')

    @staticmethod
    def CallObsidianVoiceover():

        executive_voice = randint(0, 4)

        if executive_voice == 0:
            playsound(AUDIO_DIR + 'ObsidianIsOpen.mp3')
        elif executive_voice == 1:
            playsound(AUDIO_DIR + 'GoodLuckToTheJob.mp3')
        elif executive_voice == 2:
            playsound(AUDIO_DIR + 'Done.mp3')
        elif executive_voice == 3:
            playsound(AUDIO_DIR + 'OpenItNow.mp3')
        else:
            playsound(AUDIO_DIR + 'OpeningObsidian.mp3')

    @staticmethod
    def CallVScodeVoiceover():

        executive_voice = randint(0, 5)

        if executive_voice == 0:
            playsound(AUDIO_DIR + 'GoodLuckToTheJob.mp3')
        elif executive_voice == 1:
            playsound(AUDIO_DIR + 'Doing.mp3')
        elif executive_voice == 2:
            playsound(AUDIO_DIR + 'OneMoment.mp3')
        elif executive_voice == 3:
            playsound(AUDIO_DIR + 'IOpenVScode.mp3')
        elif executive_voice == 4:
            playsound(AUDIO_DIR + 'VScodeIsOpen.mp3')
        else:
            playsound(AUDIO_DIR + 'OpenItNow.mp3')

    @staticmethod
    def CallStoresVoiceover():

        executive_voice = randint(0, 5)

        if executive_voice == 0:
            playsound(AUDIO_DIR + 'OpenItNow.mp3')
        elif executive_voice == 1:
            playsound(AUDIO_DIR + 'OneMoment.mp3')
        elif executive_voice == 2:
            playsound(AUDIO_DIR + 'FindSomthing.mp3')
        elif executive_voice == 3:
            playsound(AUDIO_DIR + 'OpeningStore.mp3')
        elif executive_voice == 4:
            playsound(AUDIO_DIR + 'TheStoreIsOpen.mp3')
        else:
            playsound(AUDIO_DIR + 'Second.mp3')

    @staticmethod
    def CallRebootVoiceover():

        executive_voice = randint(0, 1)

        if executive_voice == 0 or 1:
            playsound(AUDIO_DIR + 'OneMoment.mp3')

    @staticmethod
    def CallShutdownVoiceover():

        executive_voice = randint(0, 1)

        if executive_voice == 0 or 1:
            playsound(AUDIO_DIR + 'OneMoment.mp3')
        

print("MESSAGE")
if len(sys.argv) > 1:
    print("ActionsVoiceover." + sys.argv[1] + "()")
    eval("ActionsVoiceover." + sys.argv[1] + "()")
    
