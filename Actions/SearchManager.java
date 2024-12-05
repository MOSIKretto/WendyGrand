package Actions;

import java.text.MessageFormat;
import java.io.IOException;

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
            catch (IOException e){}
        }
    }
}
