package Actions;

import java.io.IOException;

public class SystemShutdown
{

    public static void systemShutdown(String arg, String message)
    {
        message(message);

        try 
        {
            new ProcessBuilder("shutdown", arg, "now")
            .start()
            .waitFor();
        } 
        catch (IOException | InterruptedException e){}
    }

    public static void systemSleep(String arg, String message)
    {
        message(message);

        try {
            Runtime.getRuntime().exec("systemctl suspend");} 
        catch (IOException e){}
    }


    private static void message(String message)
    {
        System.out.println("Система будет" + message + "через 5 секунд...");
        
        for (int i = 5; i >= 0; i--)
        {
            System.out.println(i);
            try{
                Thread.sleep(1000);}
            catch(InterruptedException e){}
        }
    }
}
