package main.Resources.Managers;

import java.io.IOException;


public class AppManager
{
    public static void execute(String... params) throws IOException
    {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("nux") || os.contains("nix")) 
            new ProcessBuilder(params).start();
    }
}