import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.File;

public class ActionHandlerModules 
{
    public static void TXTreader(String word)
    {
        try 
        {
            BufferedReader reader = new BufferedReader(new FileReader("../WendyGrand/Modules/Dictionary.txt"));
            String line;

            while ((line = reader.readLine()) != null) 
            {
                if (line.startsWith(word)) 
                {
                    String function = line.split("=")[1];
                    functionStart(function);
                }
            }

            reader.close();
        }
        catch (IOException e){}
    }

    private static void functionStart(String function)
    {
        File Modules = new File("../WendyGrand/Modules/YourModules/");
        File[] files = Modules.listFiles();

        File ModulesCheck = new File(Modules, function);

        if (files != null && ModulesCheck.exists()) 
        {
            System.out.println("Активация модуля: " + function);
            VoiceoverScript("../WendyGrand/Voiceover.py", "StandardModule");
            runModules.run(function);
        }
        else
        {
            VoiceoverScript("../WendyGrand/Voiceover.py", "ErrModule");
        }
    }

    private static void VoiceoverScript(String scriptPath, String arg) 
    {
        ProcessBuilder builder = new ProcessBuilder("python3", scriptPath, arg);
        try 
        {
            builder.start();
        } 
        catch (IOException e){}
    }
}