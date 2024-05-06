package Actions;

import java.io.IOException;

public class StoresManager
{
    public static void startStores()
    {
        String os = System.getProperty("os.name").toLowerCase();
        Runtime runtime = Runtime.getRuntime();

        if (os.contains("nux")) 
        {
            try
            {
                runtime.exec("snap-store" /*,"gnome-software"*/);
            } 
            catch (IOException ignored) 
            {
            }
        }
    }
}
