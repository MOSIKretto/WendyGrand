package main.Resources;

import java.util.stream.Collectors;
import java.util.stream.Stream;
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
                .map(line -> line.split("#")[0].trim()) // Удаляем комментарии
                .filter(line -> !line.isEmpty())
                .flatMap(line -> {
                    String[] parts = line.split("=", 2); // Разделяем на ключ и значение
                    if (parts.length != 2) return Stream.empty();
                    
                    String k = parts[0].trim();
                    String v = parts[1].trim();
                    
                    return key.equals(k) 
                        ? Arrays.stream(v.split(",\\s*")) 
                        : Stream.empty();
                })
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
    public static void message(String message) throws 
    InterruptedException 
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