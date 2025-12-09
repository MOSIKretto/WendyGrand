package org.wendygrand.communication;

import org.vosk.LibVosk;
import org.vosk.Model;
import org.vosk.Recognizer;
import org.wendygrand.core.Distributor;

import javax.sound.sampled.*;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;


public class Ears
{
    private static final List<String> NAME = Arrays.asList("венди", "вэнди", "среда");

    private static final Logger LOGGER = Logger.getLogger(Ears.class.getName());
    private static final int SAMPLE_RATE = 16000;
    private static volatile boolean running = true;
    
    private static final ExecutorService audioProcessor = Executors.newSingleThreadExecutor();
    private static final ExecutorService commandProcessor = Executors.newFixedThreadPool(2);
    private static final LinkedBlockingQueue<String> commandQueue = new LinkedBlockingQueue<>(10);

    static 
    {
        commandProcessor.submit(() -> {
            while (running) 
            {
                try 
                {
                    String command = commandQueue.take();
                    if (!command.isEmpty()) 
                        Distributor.search(command);
                } 
                catch (Exception e) { break; } 
            }
        });
    }

    public static void startHearing() 
    {
        LibVosk.setLogLevel(org.vosk.LogLevel.WARNINGS);
        
        AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, SAMPLE_RATE, 16, 1, 2, SAMPLE_RATE, false);
        
        audioProcessor.submit(() -> {
            try (Model model = loadModelFromResources(); Recognizer recognizer = new Recognizer(model, SAMPLE_RATE)) 
            {
                recordAndRecognizeSpeech(format, recognizer);
            } 
            catch (Exception e) { LOGGER.severe("Ошибка инициализации Vosk: " + e.getMessage()); }
        });
    }

    private static Model loadModelFromResources() throws Exception 
    {
        URL modelUrl = Ears.class.getClassLoader().getResource("model_small");
        if (modelUrl == null) 
            throw new IllegalStateException("Модель не найдена");
        return new Model(new File(modelUrl.toURI()).getAbsolutePath());
    }

    private static void recordAndRecognizeSpeech(AudioFormat format, Recognizer recognizer) 
    {
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        if (!AudioSystem.isLineSupported(info)) 
        {
            LOGGER.severe("Микрофон не поддерживается");
            return;
        }

        try (TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(info)) 
        {
            microphone.open(format);
            microphone.start();

            byte[] buffer = new byte[2048];
            while (running) 
            {
                int bytesRead = microphone.read(buffer, 0, 2048);
                if (recognizer.acceptWaveForm(buffer, bytesRead)) 
                {
                    String result = recognizer.getResult();
                    String text = extractTextFromResult(result);
                    if (!text.isEmpty()) 
                    {
                        // Проверка на все wake words
                        String foundWakeWord = findWakeWord(text);
                        if (foundWakeWord != null) 
                        {
                            LOGGER.info("Распознано: " + text);
                            String command = text.replace(foundWakeWord, "").trim();

                            if (!command.isEmpty() && !commandQueue.offer(command)) 
                                LOGGER.warning("Очередь переполнена: " + command);
                        }
                    }
                }
            }
        } 
        catch (Exception e) { LOGGER.severe("Ошибка обработки аудио: " + e.getMessage()); }
    }

    private static String findWakeWord(String text) 
    {
        for (String wakeWord : NAME)
            if (text.contains(wakeWord))
                return wakeWord;

        return null;
    }

    private static String extractTextFromResult(String result) 
    {
        int textIndex = result.indexOf("\"text\"");
        if (textIndex == -1) 
            return "";
        
        int start = result.indexOf('"', textIndex + 6) + 1;
        if (start <= 0) 
            return "";
        
        int end = result.indexOf('"', start);
        return (end > start) ? result.substring(start, end).trim().toLowerCase() : "";
    }
}