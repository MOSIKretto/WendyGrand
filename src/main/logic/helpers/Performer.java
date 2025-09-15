package src.main.logic.helpers;

import java.io.IOException;


public class Performer 
{
    public static void execute(String... command) throws 
    IOException 
    {
        new ProcessBuilder(command).inheritIO().start();
    }
}