#!/bin/bash
python -m venv venv
source venv/bin/activate
pip install --upgrade pip
pip install vosk
pip install playsound3
pip install sounddevice
