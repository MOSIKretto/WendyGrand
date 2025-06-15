package main.Resources;

import java.lang.reflect.InvocationTargetException;
import java.util.stream.Collectors;
import main.Java.ActionHandler;
import java.io.BufferedReader;
import java.util.Collections;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;


public class GeneralHelper
{

    // Для громкости
    public static void handleVolumeCommand(String input, List<String> volumeCommands) throws 
    InterruptedException, 
    IOException 
    {
        
        if (volumeCommands != null && !volumeCommands.isEmpty() && volumeCommands.stream().anyMatch(input::startsWith))
        {
            String clearTextVolume = cleanInput(input, List.of("громкость", "на", "мне", "меня", "процента", "процент", "процентов"));
            
            new ProcessBuilder("python3", "../WendyGrand/main/Python/Voiceover.py", "StandardModule_StandardResponse").start();
            Thread.sleep(1500);
            
            if (clearTextVolume.matches("^(увеличь|увеличить|уменьши|уменьшить)\\b.*"))
                ActionHandler.CallVolume(clearTextVolume + " громкость");

            else if (clearTextVolume.matches("^(больше|меньше)\\b.*"))
                ActionHandler.CallVolume("громкость " + clearTextVolume);

            else
                ActionHandler.CallVolume(clearTextVolume);
        }
    }
    
    // Для поиска в интернете и на видео площадках
    public static void handleSearchCommand(String input, List<String> webSearchCommands, List<String> youtubeSearchCommands) throws 
    InvocationTargetException, 
    IllegalArgumentException, 
    IllegalAccessException, 
    NoSuchMethodException, 
    InterruptedException,
    SecurityException, 
    IOException
    {   
        if (webSearchCommands != null && !webSearchCommands.isEmpty() && webSearchCommands.stream().anyMatch(input::startsWith)) 
        {
            if (youtubeSearchCommands != null && !youtubeSearchCommands.isEmpty() && youtubeSearchCommands.stream().anyMatch(input::startsWith)) 
            {
                List<String> removeWords = List.of("найди", "найти", "на", "ищи", "ютубе", "ютюбе", "ютуб", "ютюб");
                String clearText = cleanInput(input, removeWords).replace(" ", "%20");
                ActionHandler.CallFunction("CallSearch", clearText, "videosearch");
            } 
            else 
            {
                List<String> removeWords = List.of("найди", "найти", "в", "интернете", "ищи");
                String clearText = cleanInput(input, removeWords).replace(" ", "%20");
                ActionHandler.CallFunction("CallSearch", clearText, "websearch");
            }
        }
    }

    // Чтение кофигов
    public static List<String> readConfig(String filePath, String key) throws 
    IOException 
    {
        List<String> results = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) 
        {
            String line;

            while ((line = reader.readLine()) != null) 
            {
                line = line.trim();

                if (line.isEmpty() || line.startsWith("#")) continue;
                
                if (line.startsWith(key + "=")) 
                {
                    String value = line.substring(key.length() + 1);
                    Collections.addAll(results, value.split(",\\s*"));
                }
            }
        }
        
        return results.isEmpty() ? Collections.emptyList() : results;
    }

    // Очищение текста
    public static String cleanInput(String input, List<String> wordsToRemove) 
    {
        return Arrays.stream(input.split("\\s+"))
            .filter(word -> !wordsToRemove.contains(word))
            .collect(Collectors.joining(" "));
    }

    // Отсчет выключения системы
    public static void message(String message) throws 
    InterruptedException,
    IOException 
    {
        System.out.println("Система будет " + message + " через 5 секунд...");
        
        for (int i = 5; i >= 0; i--) 
        {
            System.out.println(i);
            Thread.sleep(1000);
        }
    }

    // Запуск процессов
    public static void Performer(String... command) throws 
    InterruptedException, 
    IOException
    {
        new ProcessBuilder(command)
        .inheritIO()
        .start()
        .waitFor();
    }
}