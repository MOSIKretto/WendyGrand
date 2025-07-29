#include <iostream>

int main() 
{   
    std::cout << "Сборка зависимостей и компиляция...\n";

    // Создание и настройка виртуального окружения для Python
    system("python3 -m venv ../WendyGrand/src/main/resources/python/venv/");
    system(("bash -c 'source ../WendyGrand/src/main/resources/python/venv/bin/activate && pip install -U vosk playsound3 sounddevice'"));

    // Компиляция всего Java-проекта
    system(("javac ../WendyGrand/src/main/java/core/WordHandler.java"));

    system(("javac ../WendyGrand/src/main/ui/java/*.java"));
    system(("javac ../WendyGrand/src/main/ui/java/components/*.java"));
    system(("javac ../WendyGrand/src/main/ui/java/panels/basePanel/*.java"));
    system(("javac ../WendyGrand/src/main/ui/java/panels/pagesPanel/*.java"));
    system(("javac ../WendyGrand/src/main/ui/java/panels/tabPanel/*.java"));
    system(("javac ../WendyGrand/src/main/ui/java/panels/tabPanel/dictionary/*.java"));

    // Запуск
    system("java ../WendyGrand/src/main/java/Main.java");
    
    return 0;
}