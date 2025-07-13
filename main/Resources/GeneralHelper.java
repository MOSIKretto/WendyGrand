package main.Resources;

import java.util.stream.Collectors;
import java.io.BufferedReader;
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
    // чтение конфигов
    public static List<String> readConfig(String path, String key) throws 
    IOException 
    {
        List<String> result = new ArrayList<>();
        StringBuilder block = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) 
        {
            String line;

            while ((line = br.readLine()) != null) 
            {
                String s = line.split("#")[0].trim();

                if (s.isEmpty()) continue;

                if (s.endsWith("\\")) 
                    block.append(s, 0, s.length() - 1);
                else 
                {
                    String full = block.append(s).toString();
                    block.setLength(0);
                    String[] parts = full.split("=", 2);

                    if (parts.length == 2 && key.equals(parts[0].trim())) 
                        for (String v : parts[1].trim().split(",\\s*")) 
                            if (!v.isEmpty()) result.add(v);
                }
            }
        }

        String full = block.toString();
        String[] parts = full.split("=", 2);

        if (!full.isEmpty() && parts.length == 2 && key.equals(parts[0].trim())) 
            for (String v : parts[1].trim().split(",\\s*")) 
                if (!v.isEmpty()) 
                    result.add(v);

        return result;
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
        Performer("python3", "../WendyGrand/main/Python/Voiceover.py", FunctionVoice);
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