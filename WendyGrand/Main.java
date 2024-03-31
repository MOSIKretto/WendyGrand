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
        ProcessBuilder processBuilder1 = new ProcessBuilder("python3", "./WendyGrand/Recognizer.py");
        try
        {
            processBuilder1.start();
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
