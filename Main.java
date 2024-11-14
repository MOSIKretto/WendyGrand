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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
                Start();
            } 
            catch (IOException | InterruptedException e){}
        }
        else{Start();}
    }


    private static void Start() 
    {   
        // Процесс для MW_Window
        ProcessBuilder builderGui = new ProcessBuilder("python3", "../WendyGrand/GUI/MW_Window.py");

        // Создаем для Recognizer
        ProcessBuilder builderRecognizer = new ProcessBuilder("bash", "-c", "source venv/bin/activate; python3 Recognizer.py");
        builderRecognizer.inheritIO();

        // Процесс для Glava
        ProcessBuilder builderGlava = new ProcessBuilder("bash", "-c", "glava --desktop --force-mod=bars");

        ExecutorService executor = Executors.newFixedThreadPool(3);

        try 
        {
            Process processRecognizer = builderRecognizer.start();
            Process processGui = builderGui.start();
            Process processGlava = builderGlava.start();

            executor.submit(() -> waitForKill(processRecognizer, processGui, processGlava));
            executor.submit(() -> waitForKill(processGui, processRecognizer, processGlava));

            executor.shutdown();
        } 
        catch (IOException e){}
    }

    private static void waitForKill(Process mainProcess, Process processToKill1, Process processToKill2) 
    {
        try 
        {
            int exitCode = mainProcess.waitFor();
            if (exitCode == 0) 
            {
                processToKill1.destroy();
                processToKill2.destroy();
            }
        } 
        catch (InterruptedException e){}
    }
}