package Actions;

import java.io.IOException;
import java.text.MessageFormat;

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
                System.err.println(https);
                runtime.exec(MessageFormat.format("firefox https://duckduckgo.com/?q={0}", https));
            } 
            catch (IOException ignored) 
            {
            }
        }
    }
}
