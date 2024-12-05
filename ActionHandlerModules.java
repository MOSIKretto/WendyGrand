import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ActionHandlerModules 
{
    public static void TXTreader(String word) throws IOException, InterruptedException
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

    private static void functionStart(String function) throws IOException, InterruptedException
    {
        File Modules = new File("../WendyGrand/Modules/YourModules/");
        File[] files = Modules.listFiles();

        File ModulesCheck = new File(Modules, function);

        if (files != null && ModulesCheck.exists()) 
        {
            System.out.println("Активация модуля: " + function);
        
            VoiceoverScript("../WendyGrand/Voiceover.py", "StandardModule_StandardResponse");
            runModules.run(function);
        }
        else
        {
            VoiceoverScript("../WendyGrand/Voiceover.py", "ErrModule");
        }
    }

    private static void VoiceoverScript(String... scriptPath) throws IOException
    {
        ProcessBuilder builder = new ProcessBuilder("python3", scriptPath[0], scriptPath[1]);
        builder.start();
    }
}