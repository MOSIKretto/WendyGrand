package Actions;

import java.io.*;

public class BrowserManager 
{
    public static void startBrowserManager()
    {
        startBrowser();
        supportBrowserVoiceover();
    }

    public static void startBrowser() 
    {
        String[] browsers = {"firefox"};
        String os = System.getProperty("os.name").toLowerCase();
        Runtime runtime = Runtime.getRuntime();

        if (os.contains("nux")) 
        {
            for (String browser : browsers) 
            {
                try 
                {
                    runtime.exec(browser + " ");
                } 
                catch (IOException ignored) 
                {
                }
            }
        }
    }

    public static void supportBrowserVoiceover()
    {
        try 
        {
            // Запускаем Python-скрипт и передаем команду "browser_voiceover"
            Process process = Runtime.getRuntime().exec("python3 voiceover.py");
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
            writer.write("browser_voiceover\n");
            writer.flush();

            // Ожидаем завершения процесса
            process.waitFor();
        } 
        catch (IOException | InterruptedException e) 
        {
            e.printStackTrace();
        }
    }
}
