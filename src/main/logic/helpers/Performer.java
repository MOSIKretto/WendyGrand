package src.main.logic.helpers;

import java.io.IOException;


public class Performer
{
    // запуск процессов
    public static void execute(String... command) throws 
    IOException 
    {
        new ProcessBuilder(command)
        .inheritIO()
        .start();
    }
}