#include <cstdlib>
#include <iostream>
#include <string>

int main() 
{   
    std::cout << "Сборка зависимостей и компиляция...\n";

    // Создание и настройка виртуального окружения для Python
    system("python3 -m venv ../WendyGrand/main/Python/venv/");

    std::string install_deps = "source ../WendyGrand/main/Python/venv/bin/activate && pip install -U vosk playsound3 sounddevice";
    system(("bash -c '" + install_deps + "'").c_str());

    // Компиляция всего Java-проекта
    system(("javac ../WendyGrand/main/Resources/UI/*.java"));
    system(("javac ../WendyGrand/main/Resources/UI/Panels/*.java"));
    system(("javac ../WendyGrand/main/Java/Handlers/WordHandler.java"));

    // Запуск
    system("java ../WendyGrand/main/Java/Main.java");
    
    return 0;
}