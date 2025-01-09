package Actions;

import java.io.IOException;

public class SearchManager
{
    public static void startSearch(String browser, String https, String search) throws IOException
    {
        String os = System.getProperty("os.name").toLowerCase();
        Runtime runtime = Runtime.getRuntime();

        if (os.contains("nux") || os.contains("nix")) 
        runtime.exec(browser + " " + https + search);
    }
}
