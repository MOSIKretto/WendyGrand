package Actions;

import java.io.IOException;

public class SystemSettingsWendy
{
    public static void restart_shutdown_System(String arg)
    {

        System.out.println("Система будет перезагружена через 10секунд...");
        
        for (int i = 10; i >= 0; i--)
        {
            System.out.println(i);
            try
            {
                Thread.sleep(1000);
            }
            catch(InterruptedException e){e.printStackTrace();}
        }

        try 
        {
            // Команда для перезагрузки системы
            ProcessBuilder processBuilder = new ProcessBuilder("shutdown", arg, "now");
            Process process = processBuilder.start();
            process.waitFor(); // Ожидание завершения процесса
        } 
        catch (IOException | InterruptedException e) 
        {
            e.printStackTrace();
        }
    }
}
