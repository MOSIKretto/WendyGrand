package Actions;

import java.io.IOException;

public class VScodeManager 
{
    public static void startVScode()
    {
        String os = System.getProperty("os.name").toLowerCase();
        Runtime runtime = Runtime.getRuntime();

        if (os.contains("nux")) 
        { 
            try
            {
                runtime.exec("code");
            } 
            catch (IOException ignored) 
            {
            }
        }
    }
}
