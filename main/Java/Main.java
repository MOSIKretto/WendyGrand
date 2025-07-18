package main.Java;

import main.Resources.UI.WindowMaker;
import java.io.IOException;
import java.awt.Frame;
import javax.swing.*;


public class Main
{

    private static Process recognitionProcess;

    public static void main(String[] args) throws 
    IOException 
    {
        recognitionProcess = new ProcessBuilder("bash", "-c", 
                            "source ../WendyGrand/main/Python/venv/bin/activate && python3 ../WendyGrand/main/Python/Recognizer.py")
                            .inheritIO().start();
        
        // Гарантированное завершение Python-процесса при закрытии Java-приложения
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (recognitionProcess.isAlive()) 
                recognitionProcess.destroy();
        }));
        
        // Мониторинг Python-процесса (автовыход при завершении)
        new Thread(() -> {
            try
            {
                recognitionProcess.waitFor();
                System.exit(0);
            }
            catch (InterruptedException ignored) { Thread.currentThread().interrupt(); }
        }).start();

        // Запуск GUI в потоке обработки событий EDT
        SwingUtilities.invokeLater(() -> {
            WindowMaker.startWindow();
            
            for (Frame frame : Frame.getFrames())
                if (frame.isVisible() && frame instanceof JFrame)
                    ((JFrame)frame).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }
}