package Actions;

import java.io.IOException;

public class HttpsManager
{
    public static void startHttps(String https)
    {
        String os = System.getProperty("os.name").toLowerCase();
        Runtime runtime = Runtime.getRuntime();

        if (os.contains("nux")) 
        {
            try 
            {
                runtime.exec("firefox https://duckduckgo.com/?q=" + https);
            } 
            catch (IOException ignored) 
            {
            }
        }
    }
}
