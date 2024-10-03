/* *Main*
 *
 * RU Запускает два файла Python - Recognizer.py и Wendy_Window.py
 * ----------------------------------------------------------------
 * En Runs two Python files - Recognizer.py and Wendy_Window.py
 * 
*/

import java.io.IOException;

public class Main
{
    public static void main(String[] args)
    {
        // Запуск Recognizer.py
        ProcessBuilder processBuilderRecognizer = new ProcessBuilder("python3", "Recognizer.py");
        try
        {
            processBuilderRecognizer.start();
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }

        ProcessBuilder processBuilderShHelper = new ProcessBuilder("python3", "ShHelper.py", "Sh" + "Start");
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
