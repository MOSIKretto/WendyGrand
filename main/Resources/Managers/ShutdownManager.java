package main.Resources.Managers;

import main.Resources.GeneralHelper;
import java.io.IOException;


public class ShutdownManager 
{

    // Управление питанием системы
    public static void systemShutdown(String arg, String message) throws 
    InterruptedException,
    IOException
    {
        GeneralHelper.message(message);
        GeneralHelper.Performer(new String[]{"shutdown", arg, "now"});
    }

    public static void systemSleep(String message) throws 
    InterruptedException, 
    IOException
    {
        GeneralHelper.message(message);
        GeneralHelper.Performer(new String[]{"systemctl", "suspend", "-i"});
    }
}