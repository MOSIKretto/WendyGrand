
//ГОТОВО!!!

import java.io.IOException;

public class Translator 
{
    public static void main(String[] args)
    {
        for (String arg : args)
        {
            ProcessBuilder processBuilderShHelper = new ProcessBuilder("python3", arg);
            try
            {
                processBuilderShHelper.start();
            } 
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
