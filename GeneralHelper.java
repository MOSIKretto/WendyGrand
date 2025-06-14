import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.util.Collections;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;


public class GeneralHelper
{

    public static void main(String[] args) throws 
    IOException 
    {
        System.out.println(readConfig("../WendyGrand/Configs/DictionaryModules.conf", "создатель"));
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
}