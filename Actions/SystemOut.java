package Actions;

import java.io.IOException;

public class SystemOut 
{
    public static void restartSystem()
    {

        System.out.println("Система будет перезагружена через 15секунд...");
        
        for (int i = 15; i >= 0; i--)
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
            ProcessBuilder processBuilder = new ProcessBuilder("shutdown", "-r", "now");
            Process process = processBuilder.start();
            process.waitFor(); // Ожидание завершения процесса
        } 
        catch (IOException | InterruptedException e) 
        {
            e.printStackTrace();
        }
    }
}
