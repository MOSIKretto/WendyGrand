'''
* *config*
*
*RU Настройщик путей. Пути храняться в .env 
*----------------------------------------------
*En Path customizer. Paths are stored in .env
'''

import os
from os.path import join, dirname, exists
from dotenv import load_dotenv

dotenv_path = join(dirname(__file__), ".env")
if exists(dotenv_path):
    load_dotenv(dotenv_path)
    AUDIO_DIR = os.environ.get("AUDIO_DIR")
    SOURCE_DIR = os.getenv("SOURCE_DIR")
