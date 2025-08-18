package src.main.logic.helpers;

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
    public static List<String> readConfig(String... args) throws 
    IOException
    {
        String path = args[0], key = args[1];
        List<String> result = new ArrayList<>();
        StringBuilder block = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) 
        {
            String line;

            while ((line = br.readLine()) != null) 
            {
                String trimmed = line.split("#", 2)[0].trim();

                if (trimmed.isEmpty()) 
                    continue;
                
                if (trimmed.endsWith("\\"))
                {
                    block.append(trimmed, 0, trimmed.length() - 1);
                    continue;
                }
                
                processConfigLine(block.append(trimmed).toString(), key, result);
                block.setLength(0);
            }
        }
        
        if (block.length() > 0)
            processConfigLine(block.toString(), key, result);
        
        return result;
    }

    private static void processConfigLine(String line, String key, List<String> result) 
    {
        String[] parts = line.split("=", 2);
        if (parts.length == 2 && key.equals(parts[0].trim())) 
            for (String value : parts[1].trim().split(",\\s*"))
                if (!value.isEmpty())
                    result.add(value);
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
        Performer("../WendyGrand/src/main/voiceover/resources/venv/bin/python", 
                            "../WendyGrand/src/main/voiceover/main/Voiceover.py", 
                            FunctionVoice);
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