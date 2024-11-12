/* *ActionHandler*
 *
 * RU Альтернативный обработчик команд полученныйх с Java_Dictionary для модулей
 * -------------------------------------------------------------------------------
 * EN Alternative command handler received from Java_Dictionary for modules
 *
*/

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;

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
                    
                    ExecutorService executor = Executors.newFixedThreadPool(2);
                    executor.submit(() -> functionStart(function));
                    executor.submit(() -> voiceoverStart());
                    executor.shutdown();
                }
            }

            reader.close();
        }
        catch (IOException e){}
    }


    private static void functionStart(String function)
    {
        System.out.println("Активация модуля: " + function);
        ProcessBuilder builderFunction = new ProcessBuilder("python3", "../WendyGrand/runModules.py", function);
        try
        {
            builderFunction.start().waitFor();
        }
        catch (IOException | InterruptedException e){}
    }

    private static void voiceoverStart()
    {
        ProcessBuilder builderVoiceover = new ProcessBuilder("python3", "../WendyGrand/Voiceover.py", "Standard");
        try
        {
            builderVoiceover.start().waitFor();
        }
        catch (IOException | InterruptedException e){}
    }
}
