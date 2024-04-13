import os
from os.path import join, dirname, exists
from dotenv import load_dotenv

dotenv_path = join(dirname(__file__), ".env")
if exists(dotenv_path):
    load_dotenv(dotenv_path)
    AUDIO_DIR = os.environ.get("AUDIO_DIR")
    BROWSER_DIR = os.environ.get("BROWSER_DIR")
