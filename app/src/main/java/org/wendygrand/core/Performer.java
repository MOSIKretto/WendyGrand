package org.wendygrand.core;

import java.io.IOException;


public class Performer 
{
    public static void execute(String command) throws IOException 
    {
        switch (System.getProperty("os.name").toLowerCase()) 
        {
            case "win" -> new ProcessBuilder("cmd", "/c", command).inheritIO().start();    
            default -> new ProcessBuilder("sh", "-c", command).inheritIO().start();
        }
    }
}