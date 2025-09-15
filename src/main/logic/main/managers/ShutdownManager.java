package src.main.logic.main.managers;

import src.main.logic.helpers.Performer;


public class ShutdownManager 
{
    public static void systemShutdown(String arg, String message) throws 
    Exception 
    {
        printMessage(message);
        Performer.execute("shutdown", arg, "now");
    }
    
    public static void systemSleep(String message) throws 
    Exception 
    {
        printMessage(message);
        Performer.execute("systemctl", "suspend", "-i");
    }
    
    private static void printMessage(String message) throws 
    InterruptedException 
    {
        System.out.println("Система будет " + message + " через 5 секунд...");
        
        for (int i = 5; i > 0; i--) {
            System.out.println(i);
            Thread.sleep(1000);
        }
    }
}