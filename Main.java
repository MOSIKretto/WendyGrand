/* *Main*
 *
 * RU Запускает два файла Python - Recognizer.py и Wendy_Window.py
 * ----------------------------------------------------------------
 * En Runs two Python files - Recognizer.py and Wendy_Window.py
 * 
*/

import java.io.File;
import java.io.IOException;
import io.github.cdimascio.dotenv.Dotenv;

public class Main
{
    public static void main(String[] args)
    {
        // Запуск Recognizer.py
        ProcessBuilder processBuilder = new ProcessBuilder("python3", "Recognizer.py");
        File log = new File("Main.log");
        processBuilder.redirectOutput(ProcessBuilder.Redirect.appendTo(log));
        processBuilder.redirectError(ProcessBuilder.Redirect.appendTo(log));
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
