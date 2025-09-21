package src.main.demonsController.main;

import java.io.IOException;

import src.main.UI.main.core.WindowMaker;


public class DemonsController
{
    public static void main(String[] args) throws 
    IOException 
    {
        // Запуск Recognizer
        new ProcessBuilder("bash", "-c", 
                            "source ../WendyGrand/src/main/recognizer/resources/venv/bin/activate && python3 ../WendyGrand/src/main/recognizer/main/Recognizer.py")
                            .inheritIO().start();
        // Запуск окна
        WindowMaker.startWindow();
    }
}