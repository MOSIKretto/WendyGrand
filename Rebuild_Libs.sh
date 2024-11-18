#!/bin/bash
javac Java_Dictionary.java
python -m venv venv
source venv/bin/activate
pip install --upgrade pip
pip install vosk
pip install playsound3
pip install sounddevice
pip install PyQt6
pip install g4f
