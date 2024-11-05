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
        ProcessBuilder processBuilderShStarter = new ProcessBuilder("python3", "ShStarter.py", "run_dir");
        try
        {
            processBuilderShStarter.start();
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
