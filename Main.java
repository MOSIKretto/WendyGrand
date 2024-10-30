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
        ProcessBuilder processBuilderShHelper = new ProcessBuilder("python3", "ShHelper.py", "run_dir");
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
