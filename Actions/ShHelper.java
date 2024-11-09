package Actions;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

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
        catch (IOException e){} 
        catch (InterruptedException e){}
    }
}
