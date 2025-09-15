package src.main.demonsController.main;

import java.io.IOException;


public class DemonsController
{
    public static void main(String[] args) throws 
    IOException 
    {
        new ProcessBuilder("bash", "-c", 
                            "source ../WendyGrand/src/main/recognizer/resources/venv/bin/activate && python3 ../WendyGrand/src/main/recognizer/main/Recognizer.py")
                            .inheritIO().start();
    }
}