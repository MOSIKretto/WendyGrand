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

        if (!nowPath.equals(readPathFromFile())) 
        {
            System.out.println("Изменение директории");
            executeRebuildScript();
            writePathToFile(nowPath);
            Start();
        }
        else Start();
    }

    //чтение старого пути
    private static String readPathFromFile() throws IOException 
    {
        if (Files.exists(Paths.get("path.cfg"))) return new String(Files.readAllBytes(Paths.get("path.cfg")));

        else return "";
    }

    //запись нового пути
    private static void writePathToFile(String path) throws IOException
    {
        Files.write(Paths.get("path.cfg"), path.getBytes());
    }

    //пересобираем либы
    private static void executeRebuildScript() throws IOException, InterruptedException 
    {
        String[] commands = {
            "bash", "-c", """
            javac WordHandler.java && \
            python -m venv venv && \
            source venv/bin/activate && \
            pip install --upgrade pip && \
            pip install vosk playsound3 sounddevice""" //перечень библиотек (убрать playsound3 с появлением Voiceover.java)
        };

        new ProcessBuilder(commands)
        .inheritIO()
        .start()
        .waitFor();
    }

    //запуск Wendy
    private static void Start() throws IOException, InterruptedException 
    {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Process builderGui = new ProcessBuilder("bash", "-c", "source venv/bin/activate; python3 ../WendyGrand/GUI/MW_Window.py").start();
        Process builderRecognizer = new ProcessBuilder("bash", "-c", "source venv/bin/activate; python3 Recognizer.py")
        .inheritIO()
        .start();

        executor.submit(() -> waitForKill(builderRecognizer, builderGui));
        executor.submit(() -> waitForKill(builderGui, builderRecognizer));

        executor.shutdown();
    }

    //закрытие Wendy
    private static void waitForKill(Process mainProcess, Process processToKill) 
    {
        try{
            if (mainProcess.waitFor() == 0) processToKill.destroy();} 
        catch (InterruptedException e){
            Thread.currentThread().interrupt();}
    }
}