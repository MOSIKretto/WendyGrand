package Actions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShHelper 
{

    public static void StartSh(String script) 
    {
        
        String path = "./Sh/" + script;

        ProcessBuilder ShStart = new ProcessBuilder("sh", path);

        try 
        {
            Process process = ShStart.start();

            // Читаем вывод процесса
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) 
            {
                String line;
                while ((line = reader.readLine()) != null)
                {
                    System.out.println(line);
                }
            }

            process.waitFor();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        } 
        catch (InterruptedException e) 
        {
            Thread.currentThread().interrupt();
        }
    }
}
