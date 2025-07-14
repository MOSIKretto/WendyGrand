package main.Java;

import main.Resources.UI.WendyW;

import java.nio.file.*;
import java.awt.Frame;
import javax.swing.*;


public class Main
{
    
    private static final String TIME_CFG = "../WendyGrand/Configs/timepath.cfg";
    private static final String VENV = "../WendyGrand/main/Python/venv/bin/activate";
    private static Process recognitionProcess;

    public static void main(String[] args) throws 
    Exception
    {
        Path config = Paths.get(TIME_CFG);
        String currentDir = Paths.get("").toAbsolutePath().toString();
        
        if (!Files.exists(config) || !currentDir.equals(Files.readString(config))) 
        {
            Files.writeString(config, currentDir);
            setupEnvironment();
        }
        startApplication();
    }

    private static void startApplication() throws 
    Exception
    {
        recognitionProcess = new ProcessBuilder("bash", "-c", 
                            "source " + VENV + " && python3 ../WendyGrand/main/Python/Recognizer.py")
                            .inheritIO().start();
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> recognitionProcess.destroy()));
        
        new Thread(() -> {
            try 
            { 
                recognitionProcess.waitFor(); 
                System.exit(0); 
            } 
            catch (InterruptedException ignored) { }
        }).start();

        SwingUtilities.invokeLater(() -> {
            WendyW.startWindow();
            for (Frame f : Frame.getFrames()) 
                if (f.isVisible()) 
                    ((JFrame)f).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }

    private static void setupEnvironment() throws 
    Exception
    {
        new ProcessBuilder("bash", "-c", 
        "javac ../WendyGrand/main/Java/Handlers/WordHandler.java && " +
        "python -m venv " + VENV.replace("/bin/activate", "") + " && " +
        "source " + VENV + " && pip install -U vosk playsound3 sounddevice")
        .inheritIO()
        .start()
        .waitFor();
    }
}