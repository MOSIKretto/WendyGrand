#include <iostream>

int main() 
{
    // Создание и настройка виртуального окружения для Python

    // Для Recognizer
    system("python3 -m venv ../WendyGrand/src/main/recognizer/resources/venv/ &&" 
           "bash -c 'source ../WendyGrand/src/main/recognizer/resources/venv/bin/activate &&"
           "pip install -U vosk sounddevice'");


    // Компиляция всего Java-проекта
    system("javac ../WendyGrand/src/main/logic/main/DictionaryHandler.java && javac -cp . src/main/UI/main/StartWindow.java");


    // Запуск
    system("java ../WendyGrand/src/main/demonsController/main/DemonsController.java");
    
    return 0;
}