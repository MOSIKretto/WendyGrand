package main.Resources;

import java.util.stream.Collectors;
import main.Java.ActionHandler;
import java.io.BufferedReader;
import java.util.Collections;
import java.util.Collection;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


public class GeneralHelper
{

    // Для громкости
    public static void handleVolumeCommand(String input, List<String> volumeCommands) throws 
    InterruptedException, 
    IOException 
    {
    
        if (volumeCommands == null || volumeCommands.isEmpty() || volumeCommands.stream().noneMatch(input::startsWith))
            return;
        
        String clearTextVolume = cleanInput(input, List.of("громкость", "на", "мне", "меня", "процента", "процент", "процентов"));
        
        new ProcessBuilder("python3", "../WendyGrand/main/Python/Voiceover.py", "StandardModule_StandardResponse").start();
        Thread.sleep(1000);
        
        ActionHandler.CallVolume(clearTextVolume.matches("^(увеличь|увеличить|уменьши|уменьшить)\\b.*") 
                                    ? clearTextVolume + " громкость" 
                                    : clearTextVolume.matches("^(больше|меньше)\\b.*") 
                                    ? "громкость " + clearTextVolume 
                                    : clearTextVolume);
    }
    
    // Для поиска в интернете и на видео площадках
    public static void handleSearchCommand(String input, List<String> webSearchCommands, List<String> youtubeSearchCommands) throws 
    InterruptedException, 
    IOException 
    {   
        if (webSearchCommands == null || webSearchCommands.isEmpty()) return;
        
        boolean hasWebSearchCommand = false;
        for (String cmd : webSearchCommands) 
        {
            if (input.startsWith(cmd)) 
            {
                hasWebSearchCommand = true;
                break;
            }
        }
        if (!hasWebSearchCommand) return;
        
        if (youtubeSearchCommands != null && !youtubeSearchCommands.isEmpty()) 
        {
            boolean hasYoutubeCommand = false;
            for (String cmd : youtubeSearchCommands) 
            {
                if (input.startsWith(cmd)) 
                    hasYoutubeCommand = true; break;
            }
            
            if (hasYoutubeCommand) 
            {
                String clearText = cleanInput(input, Set.of("найди", "найти", "на", "ищи", "ютубе", "ютюбе", "ютуб", "ютюб")).replace(" ", "%20");
                ActionHandler.CallFunction("CallSearch", clearText, "videosearch");
                return;
            }
        }
        
        String clearText = cleanInput(input, Set.of("найди", "найти", "в", "интернете", "ищи")).replace(" ", "%20");
        ActionHandler.CallFunction("CallSearch", clearText, "websearch");
    }

    // Чтение кофигов
    public static List<String> readConfig(String filePath, String key) throws 
    IOException 
    {
        List<String> results = new ArrayList<>(4);
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) 
        {
            String line;
            int keyLen = key.length() + 1;
            
            while ((line = reader.readLine()) != null) 
            {
                line = line.trim();
                
                if (line.isEmpty() || line.charAt(0) == '#') continue;
                
                if (line.startsWith(key) && line.length() > keyLen && line.charAt(key.length()) == '=') 
                {
                    String value = line.substring(keyLen);
                    Collections.addAll(results, value.split(",\\s*"));
                    break;
                }
            }
        }
        
        return results.isEmpty() ? Collections.emptyList() : results;
    }

    // Очищение текста
    public static String cleanInput(String input, Collection<String> wordsToRemove) 
    {
        Set<String> removeSet = wordsToRemove instanceof Set ? (Set<String>) wordsToRemove : new HashSet<>(wordsToRemove);
            
        return Arrays.stream(input.split("\\s+"))
        .filter(word -> !removeSet.contains(word))
        .collect(Collectors.joining(" "));
    }

    // Отсчет выключения системы
    public static void message(String message) throws InterruptedException 
    {
        System.out.println("Система будет " + message + " через 5 секунд...");
        
        for (int i = 5; i > 0; i--) 
        {
            System.out.println(i);
            Thread.sleep(1000);
        }
    }

    // Запуск процессов
    public static void Performer(String... command) throws 
    IOException 
    {
        new ProcessBuilder(command)
        .inheritIO()
        .start();
    }
}