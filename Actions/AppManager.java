package Actions;

import java.io.IOException;

public class AppManager
{
    public static void startApp(String App)
    {
        String os = System.getProperty("os.name").toLowerCase();
        Runtime runtime = Runtime.getRuntime();

        if (os.contains("nux")) 
        {
            try
            {
                runtime.exec(App);
            } 
            catch (IOException ignored) 
            {
            }
        }
    }
}
