package main.Resources.Managers;

import main.Resources.GeneralHelper;
import java.io.IOException;


public class ShutdownManager 
{
    
    public static void systemShutdown(String arg, String message) throws 
    InterruptedException,
    IOException
    {
        printMessage(message);
        GeneralHelper.Performer(new String[]{"shutdown", arg, "now"});
    }

    public static void systemSleep(String message) throws 
    InterruptedException, 
    IOException
    {
        printMessage(message);
        GeneralHelper.Performer(new String[]{"systemctl", "suspend", "-i"});
    }

    private static void printMessage(String message) throws 
    InterruptedException
    {
        System.out.println("Система будет " + message + " через 5 секунд...");
        
        for (int i = 5; i > 0; i--) 
        {
            System.out.println(i);
            Thread.sleep(1000);
        }
    }
}