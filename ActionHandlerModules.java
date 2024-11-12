/* *ActionHandler*
 *
 * RU Альтернативный обработчик команд полученныйх с Java_Dictionary для модулей
 * -------------------------------------------------------------------------------
 * EN Alternative command handler received from Java_Dictionary for modules
 *
*/

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
                    functionStart(function);
                }
            }

            reader.close();
        }
        catch (IOException e){}
    }


    private static void functionStart(String function)
    {
        System.out.println("Активация модуля: " + function);
        ProcessBuilder builderVoiceover = new ProcessBuilder("python3", "../WendyGrand/Voiceover.py", "Standard");
        ProcessBuilder builderFunction = new ProcessBuilder("python3", "../WendyGrand/runModules.py", function);
        try
        {
            builderVoiceover.start().waitFor();
            builderFunction.start().waitFor();
        }
        catch (IOException | InterruptedException e){}
    }
}
