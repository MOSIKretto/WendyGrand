package Actions;

import java.io.IOException;
import java.text.MessageFormat;

public class SearchManager
{
    public static void startSearch(String https, String search)
    {
        String os = System.getProperty("os.name").toLowerCase();
        Runtime runtime = Runtime.getRuntime();

        if (os.contains("nux")) 
        {
            try 
            {
                runtime.exec(MessageFormat.format("firefox {0}{1}", https, search));
            } 
            catch (IOException ignored) 
            {
            }
        }
    }
}
