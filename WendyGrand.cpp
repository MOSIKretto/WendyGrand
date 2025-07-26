#include <iostream>

int main() 
{   
    std::cout << "Сборка зависимостей и компиляция...\n";

    // Создание и настройка виртуального окружения для Python
    system("python3 -m venv ../WendyGrand/main/Python/venv/");
    system(("bash -c 'source ../WendyGrand/main/Python/venv/bin/activate && pip install -U vosk playsound3 sounddevice'"));

    // Компиляция всего Java-проекта
    system(("javac ../WendyGrand/main/Resources/UI/*.java"));
    system(("javac ../WendyGrand/main/Resources/UI/Panels/*.java"));
    system(("javac ../WendyGrand/main/Resources/UI/Components/*.java"));
    system(("javac ../WendyGrand/main/Java/Handlers/WordHandler.java"));

    // Запуск
    system("java ../WendyGrand/main/Java/Main.java");
    
    return 0;
}