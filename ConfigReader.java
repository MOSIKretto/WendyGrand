import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConfigReader 
{
    public static List<String> readConfig(String filePath, String key) throws IOException 
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
}