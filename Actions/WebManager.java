package Actions;

import java.io.IOException;

public class WebManager
{
    public static void startWeb(String Web)
    {
        String os = System.getProperty("os.name").toLowerCase();
        Runtime runtime = Runtime.getRuntime();

        if (os.contains("nux")) 
        {
            try 
            {
                runtime.exec(Web);
            } 
            catch (IOException ignored) 
            {
            }
        }
    }
}
