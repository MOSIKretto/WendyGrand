package Actions;

import java.io.IOException;

public class SystemSettingsWendy
{
    public static void systemWithdrawal(String arg, String message)
    {

        System.out.println("Система будет" + message + "через 5 секунд...");
        
        for (int i = 5; i >= 0; i--)
        {
            System.out.println(i);
            try
            {
                Thread.sleep(1000);
            }
            catch(InterruptedException e){}
        }

        try 
        {
            ProcessBuilder processBuilder = new ProcessBuilder("shutdown", arg, "now");
            Process process = processBuilder.start();
            process.waitFor();
        } 
        catch (IOException | InterruptedException e){}
    }
}
