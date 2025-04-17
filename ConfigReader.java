import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigReader 
{
    public static String[] readConfig(String filePath, String key) throws IOException 
    {
        List<String> results = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) 
        {
            String line;

            while ((line = reader.readLine()) != null) 
            {
                // Убираем лишние пробелы в начале и конце строки
                line = line.trim();

                if (line.isEmpty() || line.startsWith("#")) continue;
                
                if (line.startsWith(key + "=")) results.add(line.substring(key.length() + 1));
            }
        }
        
        // Возвращаем массив строк (или null, если ничего не найдено)
        return results.isEmpty() ? null : results.toArray(new String[0]);
    }
}