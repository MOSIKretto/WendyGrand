#!/bin/bash

git clone https://github.com/MOSIKretto/WendyGrand.git Wendy
# Переходим в директорию репозитория
cd Wendy
# Создаем виртуальное окружение
python3 -m venv venv
# Активируем виртуальное окружение
source venv/bin/activate
# Обновляем pip
pip install --upgrade pip
# Устанавливаем зависимости
pip install python-dotenv vosk playsound3 sounddevice
# Выходим из виртуального окружения
deactivate


