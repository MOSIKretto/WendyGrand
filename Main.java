/* *Main*
 *
 * RU Запуск Sh скриптов необходимых для пересборки venv и запуска
 * ----------------------------------------------------------------
 * En Running Sh scripts required to rebuild venv and run
 * 
*/

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;

public class Main 
{
    public static void main(String[] args) 
    {
        String nowPath = new File("").getAbsolutePath();
        String lastPath = "";

        try 
        {
            lastPath = new String(Files.readAllBytes(Paths.get("path.conf")));
        } 
        catch (Exception e) 
        {
            try 
            {
                Files.write(Paths.get("path.conf"), nowPath.getBytes());
            } 
            catch (Exception writeException){}
        }

        if (!nowPath.equals(lastPath))
        {
            System.out.println("Изменение директории");
            try
            {
                // Выполняем скрипт Rebuild_Libs.sh
                ProcessBuilder rebuildProcess = new ProcessBuilder("./Rebuild_Libs.sh");
                rebuildProcess.inheritIO();
                rebuildProcess.start().waitFor(); // Ждем завершения процесса
                try 
                {
                    Files.write(Paths.get("path.conf"), nowPath.getBytes());
                } 
                catch (Exception writeException){}
                StartSh("Recognizer.sh", "Glava.sh");
            } 
            catch (IOException | InterruptedException e){}
        }
        else{StartSh("Recognizer.sh", "Glava.sh");}
    }

    private static void StartSh(String scriptRecognizer, String scriptGlava) 
    {
        //Процесс для Recognizer
        ProcessBuilder builderRecognizer = new ProcessBuilder("bash", "./Sh/Start/" + scriptRecognizer);
        builderRecognizer.inheritIO(); // Чтобы видеть вывод скрипта в консоли

        //Процесс для MW_Window
        ProcessBuilder builderGui = new ProcessBuilder("python3", "MW_Window.py");

        //Процесс для Glava
        ProcessBuilder builderGlava = new ProcessBuilder("bash", "./Sh/Start/" + scriptGlava);

        try 
        {
            Process processRec = builderRecognizer.start();
            Process processGui = builderGui.start();
            builderGlava.start();

            int Rec = processRec.waitFor();

            if (Rec == 0) 
            {
                processGui.destroy();
            }
        } 
        catch (IOException | InterruptedException e){}
    }
}