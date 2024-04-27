import random
from playsound import playsound
from config import AUDIO_DIR

def bye():
    executive_voice = random.randint(0, 2)

    if executive_voice == 0:
        playsound(AUDIO_DIR + "ByeBye.mp3")
    elif executive_voice == 1:
        playsound(AUDIO_DIR + "GladToHelp.mp3")
    else:
        playsound(AUDIO_DIR + "Goodbye.mp3")