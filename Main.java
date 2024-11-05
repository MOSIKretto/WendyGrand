/* *Main*
 *
 * RU Запуск Sh скриптов необходимых для пересборки venv и запуска
 * ----------------------------------------------------------------
 * En Running Sh scripts required to rebuild venv and run
 * 
*/

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
                runScriptsAsync();
            } 
            catch (IOException | InterruptedException e){}
        }
        else{runScriptsAsync();}
    }

    private static void runScriptsAsync() 
    {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Запускаем Recognizer.sh
        executor.submit(() -> StartSh("Recognizer.sh"));

        // Запускаем Glava.sh, перенаправляя его вывод в null
        executor.submit(() -> StartShSilent("Glava.sh"));

        // Завершаем работу пула потоков
        executor.shutdown();
    }

    private static void StartSh(String scriptName) 
    {
        ProcessBuilder processBuilder = new ProcessBuilder("sh", "./Sh/Start/" + scriptName);
        processBuilder.inheritIO(); // Чтобы видеть вывод скрипта в консоли

        try 
        {
            Process process = processBuilder.start();
            process.waitFor();
        } 
        catch (IOException | InterruptedException e){}
    }

    private static void StartShSilent(String scriptName) 
    {
        ProcessBuilder processBuilder = new ProcessBuilder("sh", "./Sh/Start/" + scriptName);
        
        processBuilder.redirectOutput(ProcessBuilder.Redirect.to(new File("/dev/null")));
        processBuilder.redirectError(ProcessBuilder.Redirect.to(new File("/dev/null")));

        try 
        {
            Process process = processBuilder.start();
            process.waitFor();
        } 
        catch (IOException | InterruptedException e){}
    }
}
