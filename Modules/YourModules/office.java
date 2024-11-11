package YourModules;

import java.io.IOException;

public class office
{
    public static void main(String[] args)
    {
        String os = System.getProperty("os.name").toLowerCase();
        Runtime runtime = Runtime.getRuntime();

        if (os.contains("nux")) 
        {
            try{runtime.exec("libreoffice");} 
            catch (IOException ignored){}
        }
    }
}
