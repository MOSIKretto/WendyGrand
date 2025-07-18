#!/bin/bash
echo "Сборка зависимостей и компиляция..."


# Создание и настройка виртуального окружения для Python
python3 -m venv ../WendyGrand/main/Python/venv/
source ../WendyGrand/main/Python/venv/bin/activate
pip install -U vosk playsound3 sounddevice
deactivate


# Компиляция всего Java-проекта
javac ../WendyGrand/main/Resources/UI/*.java
javac ../WendyGrand/main/Resources/UI/Panels/*.java
javac ../WendyGrand/main/Java/Handlers/WordHandler.java


# Запуск
java ../WendyGrand/main/Java/Main.java