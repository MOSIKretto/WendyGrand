import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main 
{
    public static void main(String[] args) throws IOException, InterruptedException 
    {
        String nowPath = new File("").getAbsolutePath();
        String lastPath = readPathFromFile();

        if (!nowPath.equals(lastPath)) 
        {
            System.out.println("Изменение директории");
            executeRebuildScript();
            writePathToFile(nowPath);
            Start();
        } 
        else {Start();}
    }

    private static String readPathFromFile() throws IOException 
    {
        if (Files.exists(Paths.get("path.conf"))) 
        {
            return new String(Files.readAllBytes(Paths.get("path.conf")));
        } 
        else {return "";}
    }

    private static void writePathToFile(String path) throws IOException 
    {
        Files.write(Paths.get("path.conf"), path.getBytes());
    }

    private static void executeRebuildScript() throws IOException, InterruptedException 
    {
        ProcessBuilder rebuildProcess = new ProcessBuilder("./Rebuild_Libs.sh");
        rebuildProcess.inheritIO();
        rebuildProcess.start().waitFor();
    }

    private static void Start() throws IOException, InterruptedException 
    {
        ProcessBuilder builderGui = new ProcessBuilder("bash", "-c", "source venv/bin/activate; python3 ../WendyGrand/GUI/MW_Window.py");
        ProcessBuilder builderRecognizer = new ProcessBuilder("bash", "-c", "source venv/bin/activate; python3 Recognizer.py");
        builderRecognizer.inheritIO();
        ProcessBuilder builderGlava = new ProcessBuilder("bash", "-c", "glava --desktop --force-mod=bars");

        ExecutorService executor = Executors.newFixedThreadPool(3);

        Process processRecognizer = builderRecognizer.start();
        Process processGui = builderGui.start();
        Process processGlava = builderGlava.start();

        executor.submit(() -> waitForKill(processRecognizer, processGui, processGlava));
        executor.submit(() -> waitForKill(processGui, processRecognizer, processGlava));

        executor.shutdown();
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
        catch (InterruptedException e) 
        {
            Thread.currentThread().interrupt();
        }
    }
}