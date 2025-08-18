from playsound3 import playsound
from random import choice
import sys
import os

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
    def hello():
        executive_voice = choice(['Hello.mp3', 'ImHere.mp3'])
        ActionsVoiceover.ActivateVoice(executive_voice)

#----------------------------------------------------------------------------------------------------------------------------------

    @staticmethod
    def browser():
        executive_voice = choice(['BrowserOpen.mp3', 'OpeningBrowser.mp3', 'ActionsVoiceover.StandardModule_StandardResponse()'])
        ActionsVoiceover.ActivateVoice(executive_voice)

    @staticmethod
    def conductor():
        executive_voice = choice(['GuideForSystemOpen.mp3', 'OpeningGuideForSystem.mp3', 
                                  'ActionsVoiceover.StandardModule_StandardResponse()'])
        ActionsVoiceover.ActivateVoice(executive_voice)

    @staticmethod
    def terminal():
        executive_voice = choice(['TerminalOpen.mp3', 'OpeningTerminal.mp3', 'ActionsVoiceover.StandardModule_StandardResponse()'])
        ActionsVoiceover.ActivateVoice(executive_voice)

    @staticmethod
    def store():
        executive_voice = choice(['FindSomthing.mp3', 'OpeningAppStore.mp3', 'OpeningStore.mp3', 'TheStoreIsOpen.mp3', 
                                  'ActionsVoiceover.StandardModule_StandardResponse()'])
        ActionsVoiceover.ActivateVoice(executive_voice)

    @staticmethod
    def office():
        executive_voice = choice(['OfficeOpen.mp3', 'OpeningOffice.mp3', 'ActionsVoiceover.StandardModule_StandardResponse()'])
        ActionsVoiceover.ActivateVoice(executive_voice)

    @staticmethod
    def messenger():
        executive_voice = choice(['MessengerOpen.mp3', 'OpeningMessenger.mp3', 'ActionsVoiceover.StandardModule_StandardResponse()'])
        ActionsVoiceover.ActivateVoice(executive_voice)

    @staticmethod
    def socialnetwork():
        executive_voice = choice(['SocialNetworkOpen.mp3', 'OpeningSocialNetwork.mp3', 
                                  'ActionsVoiceover.StandardModule_StandardResponse()'])
        ActionsVoiceover.ActivateVoice(executive_voice)

    @staticmethod
    def notes():
        executive_voice = choice(['NotesOpen.mp3', 'GoodLuckToTheJob.mp3', 'OpeningNotes.mp3', 
                                  'ActionsVoiceover.StandardModule_StandardResponse()'])
        ActionsVoiceover.ActivateVoice(executive_voice)

    @staticmethod
    def codeeditor():
        executive_voice = choice(['GoodLuckToTheJob.mp3', 'OpeningCodeEditor.mp3', 'CodeEditorOpen.mp3', 
                                  'ActionsVoiceover.StandardModule_StandardResponse()'])
        ActionsVoiceover.ActivateVoice(executive_voice)

#----------------------------------------------------------------------------------------------------------------------------------

    @staticmethod
    def reboot():
        executive_voice = choice(['Rebooting.mp3', 'ActionsVoiceover.StandardModule_StandardResponse()'])
        ActionsVoiceover.ActivateVoice(executive_voice)

    @staticmethod
    def shutdown():
        executive_voice = choice(['ShuttingDown.mp3', 'ActionsVoiceover.StandardModule_StandardResponse()'])
        ActionsVoiceover.ActivateVoice(executive_voice)

    @staticmethod
    def sleep():
        executive_voice = choice(['Sleeping.mp3', 'ActionsVoiceover.StandardModule_StandardResponse()'])
        ActionsVoiceover.ActivateVoice(executive_voice)

#----------------------------------------------------------------------------------------------------------------------------------

    @staticmethod
    def websearch():
        executive_voice = choice(['LookingForYourRequestOnTheInternet.mp3', 'AskingOnTheInternet.mp3',
                                  'WillFindIt.mp3', 'ActionsVoiceover.StandardModule_StandardResponse()'])
        ActionsVoiceover.ActivateVoice(executive_voice)

    @staticmethod
    def videosearch():
        executive_voice = choice(['TryingToFindYourYouTubeRequest.mp3', 'LookingForYourYouTubeRequest.mp3',
                                  'WillFindIt.mp3', 'ActionsVoiceover.StandardModule_StandardResponse()'])
        ActionsVoiceover.ActivateVoice(executive_voice)

#----------------------------------------------------------------------------------------------------------------------------------

    @staticmethod
    def volume():
        executive_voice = choice(['ISetTheVolume.mp3', 'TheVolumeIsSet.mp3', 'ActionsVoiceover.StandardModule_StandardResponse()'])
        ActionsVoiceover.ActivateVoice(executive_voice)

    @staticmethod
    def volumeErr():
        executive_voice = choice(['SorryButIDontKnowSuchAVolumeCommand.mp3', 'SorryButThereIsNoSuchVolumeCommand.mp3', 
                                  'VolumeChangeError.mp3', 'AnErrorOccurredWhileChangingTheVolume.mp3', 
                                  'ThereWasAProblemChangingTheVolume.mp3'])
        ActionsVoiceover.ActivateVoice(executive_voice)

#----------------------------------------------------------------------------------------------------------------------------------

    @staticmethod
    def errUpgradePip():
        executive_voice = choice(['FailedToUpdatePipToLatestVersion.mp3', 'FailedToUpdatePip.mp3'])
        ActionsVoiceover.ActivateVoice(executive_voice)

    @staticmethod
    def errVenvCreate():
        executive_voice = choice(['FailedToCreateVirtualEnvironment.mp3', 'ErrorCreatingVirtualEnvironment.mp3'])
        ActionsVoiceover.ActivateVoice(executive_voice)

    @staticmethod
    def errorInstallLibs():
        executive_voice = choice(["ErrorInstallingLibrary.mp3", "FailedToFindOrInstallLibrary.mp3"])
        ActionsVoiceover.ActivateVoice(executive_voice)

    @staticmethod
    def dependenciesFound():
        executive_voice = choice(['DependenciesFoundStartingBuild.mp3', 'DependenciesFoundStartBuilding.mp3', 
                                  'StartingBuildOfRequiredDependencies.mp3'])
        ActionsVoiceover.ActivateVoice(executive_voice)

#----------------------------------------------------------------------------------------------------------------------------------

    @staticmethod
    def StandardModule_StandardResponse():
        executive_voice = choice(['OneMoment.mp3', 'Doing.mp3', 'Done.mp3', 'AlwaysAPleasure.mp3', 'OneSecond.mp3', 'Second.mp3'])
        playsound("../WendyGrand/src/main/voiceover/resources/audio/" + executive_voice)

    @staticmethod
    def ErrModule():
        executive_voice = choice(['CouldntFindYouModuleMaybeYouDidntAddItToYourModues.mp3', 'SorryICouldntFindYourModule.mp3'])
        ActionsVoiceover.ActivateVoice(executive_voice)

#----------------------------------------------------------------------------------------------------------------------------------

    @staticmethod
    def ActivateVoice(executive_voice):
        if executive_voice != 'ActionsVoiceover.StandardModule_StandardResponse()':
            playsound("../WendyGrand/src/main/voiceover/resources/audio/" + executive_voice)
        else:
            eval(executive_voice)

if len(sys.argv) > 1:
    eval("ActionsVoiceover." + sys.argv[1] + "()")