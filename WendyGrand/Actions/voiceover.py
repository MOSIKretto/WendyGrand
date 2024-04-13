from playsound import playsound
from config import AUDIO_DIR


class ActionsVoiceover:
    @staticmethod
    def browser_voiceover():
        playsound(AUDIO_DIR + "BrowserOpen.mp3")