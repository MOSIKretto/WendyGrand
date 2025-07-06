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
    // чтение конфигов
    public static List<String> readConfig(String path, String key) throws 
    IOException
    {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) 
        {
            return br.lines()
            .map(line -> line.split("#")[0].trim()) // удаление комментариев
            .filter(line -> !line.isEmpty())
            .flatMap(line -> {
                String[] parts = line.split("=", 2); // разделение на ключ и значение
                if (parts.length != 2) return Stream.empty();
                
                String k = parts[0].trim();
                String v = parts[1].trim();
                
                return key.equals(k) ? Arrays.stream(v.split(",\\s*")) : Stream.empty();
            })
            .collect(Collectors.toList());
        }
    }

    // очищение текста
    public static String cleanInput(String input, Collection<String> words) 
    {
        Set<String> toRemove = words instanceof Set ? (Set<String>)words : new HashSet<>(words);
        return Arrays.stream(input.split("\\s+"))
        .filter(word -> !toRemove.contains(word))
        .collect(Collectors.joining(" "));
    }
    
    // озвучка
    public static void Voiceover(String FunctionVoice) throws 
    IOException 
    {
        GeneralHelper.Performer("python3", "../WendyGrand/main/Python/Voiceover.py", FunctionVoice);
    }

    // запуск процессов
    public static void Performer(String... command) throws 
    IOException 
    {
        new ProcessBuilder(command)
        .inheritIO()
        .start();
    }
}