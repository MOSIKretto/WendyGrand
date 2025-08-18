package src.main.logic.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileReader;
import java.util.List;


public class ConfigReader
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
}