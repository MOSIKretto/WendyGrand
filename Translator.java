import java.io.IOException;

public class Translator 
{
    public static void main(String[] args)
    {
        for (String arg : args)
        {
            ProcessBuilder processBuilder = new ProcessBuilder("python3", arg);
            try
            {
                processBuilder.start();
            } 
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
