from playsound3 import playsound
from random import randint
import sys

AUDIO_DIR = "../WendyGrand/Audio/"

'''
Универсальные файлы озвучкиб которые подайдут к большенсву функций:
###################################################################
Doing.mp3
Done.mp3
OneMoment.mp3
Second.mp3
AlwaysAPleasure.mp3 
OneSecond.mp3
'''

class ActionsVoiceover:

#----------------------------------------------------------------------------------------------------------------------------------

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

#----------------------------------------------------------------------------------------------------------------------------------

    @staticmethod
    def CallHelloVoiceover():
        executive_voice = randint(0, 1)
        if executive_voice == 0:
            playsound(AUDIO_DIR + 'Hello.mp3')
        else:
            playsound(AUDIO_DIR + 'ImHere.mp3')

#----------------------------------------------------------------------------------------------------------------------------------

    @staticmethod
    def CallBrowserVoiceover():
        executive_voice = randint(0, 2)
        if executive_voice == 0:
            playsound(AUDIO_DIR + 'BrowserOpen.mp3')
        elif executive_voice == 1:
            playsound(AUDIO_DIR + 'OpeningBrowser.mp3')
        else:
            ActionsVoiceover.StandardModule_StandardResponse()

    @staticmethod
    def CallConductorVoiceover():
        executive_voice = randint(0, 2)
        if executive_voice == 0:
            playsound(AUDIO_DIR + 'GuideForSystemOpen.mp3')
        elif executive_voice == 1:
            playsound(AUDIO_DIR + 'OpeningGuideForSystem.mp3')
        else:
            ActionsVoiceover.StandardModule_StandardResponse()

    @staticmethod
    def CallTerminalVoiceover():
        executive_voice = randint(0, 2)
        if executive_voice == 0:
            playsound(AUDIO_DIR + 'TerminalOpen.mp3')
        elif executive_voice == 1:
            playsound(AUDIO_DIR + 'OpeningTerminal.mp3')
        else:
            ActionsVoiceover.StandardModule_StandardResponse()

    @staticmethod
    def CallStoresVoiceover():
        executive_voice = randint(0, 3)
        if executive_voice == 0:
            playsound(AUDIO_DIR + 'FindSomthing.mp3')
        elif executive_voice == 1:
            playsound(AUDIO_DIR + 'OpeningStore.mp3')
        elif executive_voice == 2:
            playsound(AUDIO_DIR + 'TheStoreIsOpen.mp3')
        else:
            ActionsVoiceover.StandardModule_StandardResponse()

    @staticmethod
    def CallOfficeVoiceover():
        executive_voice = randint(0, 2)
        if executive_voice == 0:
            playsound(AUDIO_DIR + 'OfficeOpen.mp3')
        elif executive_voice == 1:
            playsound(AUDIO_DIR + 'OpeningOffice.mp3')
        else:
            ActionsVoiceover.StandardModule_StandardResponse()

#----------------------------------------------------------------------------------------------------------------------------------

    @staticmethod
    def CallMessengerVoiceover():
        executive_voice = randint(0, 2)
        if executive_voice == 0:
            playsound(AUDIO_DIR + 'MessengerOpen.mp3')
        elif executive_voice == 1:
            playsound(AUDIO_DIR + 'OpeningMessenger.mp3')
        else:
            ActionsVoiceover.StandardModule_StandardResponse()

    @staticmethod
    def CallSocialNetworkVoiceover():
        executive_voice = randint(0, 2)
        if executive_voice == 0:
            playsound(AUDIO_DIR + 'SocialNetworkOpen.mp3')
        elif executive_voice == 1:
            playsound(AUDIO_DIR + 'OpeningSocialNetwork.mp3')
        else:
            ActionsVoiceover.StandardModule_StandardResponse()

#----------------------------------------------------------------------------------------------------------------------------------

    @staticmethod
    def CallNotesVoiceover():
        executive_voice = randint(0, 3)
        if executive_voice == 0:
            playsound(AUDIO_DIR + 'NotesOpen.mp3')
        elif executive_voice == 1:
            playsound(AUDIO_DIR + 'GoodLuckToTheJob.mp3')
        elif executive_voice == 2:
            playsound(AUDIO_DIR + 'OpeningNotes.mp3')
        else:
            ActionsVoiceover.StandardModule_StandardResponse()

    @staticmethod
    def CallCodeEditorVoiceover():
        executive_voice = randint(0, 3)
        if executive_voice == 0:
            playsound(AUDIO_DIR + 'GoodLuckToTheJob.mp3')
        elif executive_voice == 1:
            playsound(AUDIO_DIR + 'OpeningCodeEditor.mp3')
        elif executive_voice == 2:
            playsound(AUDIO_DIR + 'CodeEditorOpen.mp3')
        else:
            ActionsVoiceover.StandardModule_StandardResponse()

#----------------------------------------------------------------------------------------------------------------------------------

    @staticmethod
    def CallRebootVoiceover():
        executive_voice = randint(0, 1)
        if executive_voice == 0:
            playsound(AUDIO_DIR + 'Rebooting.mp3')
        else:
            ActionsVoiceover.StandardModule_StandardResponse()

    @staticmethod
    def CallShutdownVoiceover():
        executive_voice = randint(0, 1)
        if executive_voice == 0:
            playsound(AUDIO_DIR + 'ShuttingDown.mp3')
        else:
            ActionsVoiceover.StandardModule_StandardResponse()

    @staticmethod
    def CallSleepVoiceover():
        executive_voice = randint(0, 1)
        if executive_voice == 0:
            playsound(AUDIO_DIR + 'Sleeping.mp3')
        else:
            ActionsVoiceover.StandardModule_StandardResponse()

#----------------------------------------------------------------------------------------------------------------------------------

    @staticmethod
    def CallWebSearchVoiceover():
        executive_voice = randint(0, 3)
        if executive_voice == 0:
            playsound(AUDIO_DIR + 'LookingForYourRequestOnTheInternet.mp3')
        elif executive_voice == 1:
            playsound(AUDIO_DIR + 'AskingOnTheInternet.mp3')
        elif executive_voice == 2:
            playsound(AUDIO_DIR + 'WillFindIt.mp3')
        else:
            ActionsVoiceover.StandardModule_StandardResponse()

    @staticmethod
    def CallYouTubeSearchVoiceover():
        executive_voice = randint(0, 3)
        if executive_voice == 0:
            playsound(AUDIO_DIR + 'TryingToFindYourYouTubeRequest.mp3')
        elif executive_voice == 1:
            playsound(AUDIO_DIR + 'LookingForYourYouTubeRequest.mp3')
        elif executive_voice == 2:
            playsound(AUDIO_DIR + 'WillFindIt.mp3')
        else:
            ActionsVoiceover.StandardModule_StandardResponse()

#----------------------------------------------------------------------------------------------------------------------------------

    @staticmethod
    def StandardModule_StandardResponse():
        executive_voice = randint(0, 5)
        if executive_voice == 0:
            playsound(AUDIO_DIR + 'OneMoment.mp3')
        elif executive_voice == 1:
            playsound(AUDIO_DIR + 'Doing.mp3')
        elif executive_voice == 2:
            playsound(AUDIO_DIR + 'Done.mp3')
        elif executive_voice == 3:
            playsound(AUDIO_DIR + 'AlwaysAPleasure.mp3')
        elif executive_voice == 4:
            playsound(AUDIO_DIR + 'OneSecond.mp3')
        else:
            playsound(AUDIO_DIR + 'Second.mp3')

    @staticmethod
    def ErrModule():
        executive_voice = randint(0, 1)
        if executive_voice == 0:
            playsound(AUDIO_DIR + 'CouldntFindYouModuleMaybeYouDidntAddItToYourModues.mp3')
        else:
            playsound(AUDIO_DIR + 'SorryICouldntFindYourModule.mp3')

#----------------------------------------------------------------------------------------------------------------------------------

if len(sys.argv) > 1:
    eval("ActionsVoiceover." + sys.argv[1] + "()")
