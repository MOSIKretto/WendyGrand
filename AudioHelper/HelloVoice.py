import random
from playsound import playsound
from config import AUDIO_DIR

def hello():
    executive_voice = random.randint(0, 1)

    if executive_voice == 0:
        playsound(AUDIO_DIR + "LaunchScriptsAndReadyToWork.mp3")
    else:
        playsound(AUDIO_DIR + "HellowStartWork.mp3")
