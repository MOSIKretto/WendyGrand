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
        if (Files.exists(Paths.get("timepath.cfg"))) return new String(Files.readAllBytes(Paths.get("timepath.cfg")));

        else return "";
    }

    //запись нового пути
    private static void writePathToFile(String path) throws IOException
    {
        Files.write(Paths.get("timepath.cfg"), path.getBytes());
    }

    //пересобираем либы
    private static void executeRebuildScript() throws IOException, InterruptedException 
    {
        String[] commands = {
            "bash", "-c", """
            javac ../WendyGrand/WordHandler.java && \
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
        new ProcessBuilder("bash", "-c", "source venv/bin/activate; python3 Recognizer.py")
        .inheritIO()
        .start()
        .waitFor();
    }
}