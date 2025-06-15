from playsound3 import playsound
from random import choice
import sys

'''
Универсальные файлы озвучки которые подайдут к большенсву функций:
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
        executive_voice = choice(['LaunchScriptsAndReadyToWork.mp3', 'HellowStartWork.mp3'])
        ActionsVoiceover.ActivateVoice(executive_voice)

    @staticmethod
    def ByeVoiceover():
        executive_voice = choice(['ByeBye.mp3', 'GladToHelp.mp3', 'Goodbye.mp3'])
        ActionsVoiceover.ActivateVoice(executive_voice)

#----------------------------------------------------------------------------------------------------------------------------------

    @staticmethod
    def CallHelloVoiceover():
        executive_voice = choice(['Hello.mp3', 'ImHere.mp3'])
        ActionsVoiceover.ActivateVoice(executive_voice)

#----------------------------------------------------------------------------------------------------------------------------------

    @staticmethod
    def CallBrowserVoiceover():
        executive_voice = choice(['BrowserOpen.mp3', 'OpeningBrowser.mp3', 'ActionsVoiceover.StandardModule_StandardResponse()'])
        ActionsVoiceover.ActivateVoice(executive_voice)

    @staticmethod
    def CallConductorVoiceover():
        executive_voice = choice(['GuideForSystemOpen.mp3', 'OpeningGuideForSystem.mp3', 'ActionsVoiceover.StandardModule_StandardResponse()'])
        ActionsVoiceover.ActivateVoice(executive_voice)

    @staticmethod
    def CallTerminalVoiceover():
        executive_voice = choice(['TerminalOpen.mp3', 'OpeningTerminal.mp3', 'ActionsVoiceover.StandardModule_StandardResponse()'])
        ActionsVoiceover.ActivateVoice(executive_voice)

    @staticmethod
    def CallStoreVoiceover():
        executive_voice = choice(['FindSomthing.mp3', 'OpeningStore.mp3', 'TheStoreIsOpen.mp3', 'ActionsVoiceover.StandardModule_StandardResponse()'])
        ActionsVoiceover.ActivateVoice(executive_voice)

    @staticmethod
    def CallOfficeVoiceover():
        executive_voice = choice(['OfficeOpen.mp3', 'OpeningOffice.mp3', 'ActionsVoiceover.StandardModule_StandardResponse()'])
        ActionsVoiceover.ActivateVoice(executive_voice)

#----------------------------------------------------------------------------------------------------------------------------------

    @staticmethod
    def CallMessengerVoiceover():
        executive_voice = choice(['MessengerOpen.mp3', 'OpeningMessenger.mp3', 'ActionsVoiceover.StandardModule_StandardResponse()'])
        ActionsVoiceover.ActivateVoice(executive_voice)

    @staticmethod
    def CallSocialNetworkVoiceover():
        executive_voice = choice(['SocialNetworkOpen.mp3', 'OpeningSocialNetwork.mp3', 'ActionsVoiceover.StandardModule_StandardResponse()'])
        ActionsVoiceover.ActivateVoice(executive_voice)

#----------------------------------------------------------------------------------------------------------------------------------

    @staticmethod
    def CallNotesVoiceover():
        executive_voice = choice(['NotesOpen.mp3', 'GoodLuckToTheJob.mp3', 'OpeningNotes.mp3', 'ActionsVoiceover.StandardModule_StandardResponse()'])
        ActionsVoiceover.ActivateVoice(executive_voice)

    @staticmethod
    def CallCodeEditorVoiceover():
        executive_voice = choice(['GoodLuckToTheJob.mp3', 'OpeningCodeEditor.mp3', 'CodeEditorOpen.mp3', 'ActionsVoiceover.StandardModule_StandardResponse()'])
        ActionsVoiceover.ActivateVoice(executive_voice)

#----------------------------------------------------------------------------------------------------------------------------------

    @staticmethod
    def CallRebootVoiceover():
        executive_voice = choice(['Rebooting.mp3', 'ActionsVoiceover.StandardModule_StandardResponse()'])
        ActionsVoiceover.ActivateVoice(executive_voice)

    @staticmethod
    def CallShutdownVoiceover():
        executive_voice = choice(['ShuttingDown.mp3'])
        ActionsVoiceover.ActivateVoice(executive_voice)

    @staticmethod
    def CallSleepVoiceover():
        executive_voice = choice(['Sleeping.mp3', 'ActionsVoiceover.StandardModule_StandardResponse()'])
        ActionsVoiceover.ActivateVoice(executive_voice)

#----------------------------------------------------------------------------------------------------------------------------------

    @staticmethod
    def CallSearchVoiceover():
        executive_voice = choice(['LookingForYourRequestOnTheInternet.mp3', 'AskingOnTheInternet.mp3',
                                  'WillFindIt.mp3', 'ActionsVoiceover.StandardModule_StandardResponse()'])
        ActionsVoiceover.ActivateVoice(executive_voice)

#----------------------------------------------------------------------------------------------------------------------------------

    @staticmethod
    def StandardModule_StandardResponse():
        executive_voice = choice(['OneMoment.mp3', 'Doing.mp3', 'Done.mp3', 'AlwaysAPleasure.mp3', 'OneSecond.mp3', 'Second.mp3'])
        playsound("../WendyGrand/main/Resources/Audio/" + executive_voice)

    @staticmethod
    def ErrModule():
        executive_voice = choice(['CouldntFindYouModuleMaybeYouDidntAddItToYourModues.mp3', 'SorryICouldntFindYourModule.mp3'])
        ActionsVoiceover.ActivateVoice(executive_voice)

#----------------------------------------------------------------------------------------------------------------------------------

    @staticmethod
    def ActivateVoice(executive_voice):
        if executive_voice != 'ActionsVoiceover.StandardModule_StandardResponse()':
            playsound("../WendyGrand/main/Resources/Audio/" + executive_voice)
        else:
            ActionsVoiceover.StandardModule_StandardResponse()

if len(sys.argv) > 1:
    eval("ActionsVoiceover." + sys.argv[1] + "()")
