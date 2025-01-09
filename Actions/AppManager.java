package Actions;

import java.io.IOException;

public class AppManager
{
    public static void startApp(String App) throws IOException
    {
        String os = System.getProperty("os.name").toLowerCase();
        Runtime runtime = Runtime.getRuntime();

        if (os.contains("nux") || os.contains("nix")) 
        runtime.exec(App);
    }
}
