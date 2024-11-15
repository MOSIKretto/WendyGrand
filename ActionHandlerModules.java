/* *ActionHandler*
 *
 * RU Альтернативный обработчик команд полученныйх с Java_Dictionary для модулей
 * -------------------------------------------------------------------------------
 * EN Alternative command handler received from Java_Dictionary for modules
 *
*/

import java.io.InputStreamReader;
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
            executePythonScript("../WendyGrand/Voiceover.py", "StandardModule");
            executePythonScript("../WendyGrand/runModules.py", function);
        }
        else
        {
            executePythonScript("../WendyGrand/Voiceover.py", "ErrModule");
        }
    }

    private static void executePythonScript(String scriptPath, String arg) 
    {
        ProcessBuilder builder = new ProcessBuilder("python3", scriptPath, arg);
        builder.redirectErrorStream(true);
        try 
        {
            Process process = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) 
            {
                System.out.println(line);
            }
            process.waitFor();
        } 
        catch (IOException | InterruptedException e){}
    }
}