
//ГОТОВО!!!

import java.io.IOException;

public class Main
{
    public static void main(String[] args)
    {
        ProcessBuilder processBuilderShHelper = new ProcessBuilder("python3", "Installer.py");
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
