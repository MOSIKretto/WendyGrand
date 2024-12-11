import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;

public class Main 
{
    public static void main(String[] args) throws IOException, InterruptedException 
    {
        //проверка на изменение дирректории
        String nowPath = new File("").getAbsolutePath();
        String lastPath = readPathFromFile();

        if (!nowPath.equals(lastPath)) 
        {
            System.out.println("Изменение директории");
            executeRebuildScript();
            writePathToFile(nowPath);
            Start();
        } 
        else{
            Start();}
    }

    //чтение старого пути
    private static String readPathFromFile() throws IOException 
    {
        if (Files.exists(Paths.get("path.conf"))){
            return new String(Files.readAllBytes(Paths.get("path.conf")));} 
        else{
            return "";}
    }

    //запись нового пути
    private static void writePathToFile(String path) throws IOException{
        Files.write(Paths.get("path.conf"), path.getBytes());}

    //пересобираем либы
    private static void executeRebuildScript() throws IOException, InterruptedException 
    {
        new ProcessBuilder("./Rebuild_Libs.sh")
        .inheritIO()
        .start()
        .waitFor();
    }

    //запуск Wendy
    private static void Start() throws IOException, InterruptedException 
    {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        Process builderGui = new ProcessBuilder("bash", "-c", "source venv/bin/activate; python3 ../WendyGrand/GUI/MW_Window.py").start();
        Process builderRecognizer = new ProcessBuilder("bash", "-c", "source venv/bin/activate; python3 Recognizer.py").inheritIO().start();
        Process builderGlava = new ProcessBuilder("bash", "-c", "glava --desktop --force-mod=bars").start();

        executor.submit(() -> waitForKill(builderRecognizer, builderGui, builderGlava));
        executor.submit(() -> waitForKill(builderGui, builderRecognizer, builderGlava));

        executor.shutdown();
    }

    //закрытие Wendy
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
        catch (InterruptedException e){
            Thread.currentThread().interrupt();}
    }
}