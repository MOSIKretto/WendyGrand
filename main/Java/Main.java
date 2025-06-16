package main.Java;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;


public class Main 
{

    private static final String TIME_CFG = "../WendyGrand/Configs/timepath.cfg";
    
    public static void main(String[] args) throws 
    InterruptedException,
    IOException
    {
        //проверка на изменение дирректории
        String nowPath = new File("").getAbsolutePath();

        if (!nowPath.equals(readPathFromFile())) 
        {
            System.out.println("Изменение директории");
            Performer(
                "bash", "-c", """
            javac ../WendyGrand/main/Java/Handlers/WordHandler.java && \
            python -m venv ../WendyGrand/main/Python/venv && \
            source ../WendyGrand/main/Python/venv/bin/activate && \
            pip install --upgrade pip && \
            pip install vosk playsound3 sounddevice""" //перечень библиотек (убрать playsound3 с появлением Voiceover.java)
            );
            Files.write(Paths.get(TIME_CFG), nowPath.getBytes()); //запись нового пути
            // Запуск процессов
            Performer("bash", "-c", "source ../WendyGrand/main/Python/venv/bin/activate; python3 ../WendyGrand/main/Python/Recognizer.py");
        }
        else
            Performer("bash", "-c", "source ../WendyGrand/main/Python/venv/bin/activate; python3 ../WendyGrand/main/Python/Recognizer.py");
    }

    //чтение старого пути
    private static String readPathFromFile() throws 
    IOException 
    {
        if (Files.exists(Paths.get(TIME_CFG))) return new String(Files.readAllBytes(Paths.get(TIME_CFG)));

        else return "";
    }

    private static void Performer(String... command) throws 
    InterruptedException, 
    IOException
    {
        new ProcessBuilder(command)
        .inheritIO()
        .start()
        .waitFor();
    }
}