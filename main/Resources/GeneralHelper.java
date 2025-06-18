package main.Resources;

import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.util.Collection;
import java.io.IOException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


public class GeneralHelper
{
    // Чтение конфигов
    public static List<String> readConfig(String path, String key) throws 
    IOException 
    {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) 
        {
            return br.lines()
            .filter(line -> !line.trim().isEmpty() && !line.startsWith("#"))
            .filter(line -> line.startsWith(key + "="))
            .map(line -> line.substring(key.length()+1).split(",\\s*"))
            .flatMap(Arrays::stream)
            .collect(Collectors.toList());
        }
    }

    // Очищение текста
    public static String cleanInput(String input, Collection<String> words) 
    {
        Set<String> toRemove = words instanceof Set ? (Set<String>)words : new HashSet<>(words);
        return Arrays.stream(input.split("\\s+"))
        .filter(word -> !toRemove.contains(word))
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
    
    // Озвучка
    public static void Voiceover(String FunctionVoice) throws 
    IOException 
    {
        GeneralHelper.Performer("python3", "../WendyGrand/main/Python/Voiceover.py", FunctionVoice);
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