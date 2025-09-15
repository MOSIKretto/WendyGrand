package src.main.logic.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class ConfigReaderLogic
{
    private static final ConcurrentMap<String, CachedConfig> configCache = new ConcurrentHashMap<>();
    
    private static class CachedConfig
    {
        final long lastModified;
        final List<String> values;
        
        CachedConfig(long lastModified, List<String> values) 
        {
            this.lastModified = lastModified;
            this.values = values;
        }
    }

    public static List<String> readConfig(String path, String key) throws 
    IOException 
    {
        Path filePath = Paths.get(path);
        long lastModified = Files.getLastModifiedTime(filePath).toMillis();
        String cacheKey = path + ":" + key;
        
        CachedConfig cached = configCache.get(cacheKey);
        if (cached != null && cached.lastModified == lastModified) return new ArrayList<>(cached.values);
        
        List<String> result = new ArrayList<>(10);
        try (BufferedReader br = Files.newBufferedReader(filePath)) 
        {
            StringBuilder currentBlock = new StringBuilder(128);
            String line;
            
            while ((line = br.readLine()) != null) 
            {
                line = removeComment(line).trim();
                if (line.isEmpty()) continue;
                
                if (line.endsWith("\\")) 
                {
                    currentBlock.append(line, 0, line.length()-1).append(" ");
                    continue;
                }
                
                if (currentBlock.length() > 0) 
                {
                    currentBlock.append(line);
                    line = currentBlock.toString();
                    currentBlock.setLength(0);
                }
                
                if (isTargetLine(line, key)) 
                {
                    extractValues(line.substring(key.length()), result);
                    break;
                }
            }
        }
        
        configCache.put(cacheKey, new CachedConfig(lastModified, new ArrayList<>(result)));
        return result;
    }

    private static String removeComment(String line) 
    {
        int idx = line.indexOf('#');
        return idx >= 0 ? line.substring(0, idx) : line;
    }

    private static boolean isTargetLine(String line, String key) 
    {
        if (!line.startsWith(key)) return false;
        
        int i = key.length();
        while (i < line.length() && Character.isWhitespace(line.charAt(i))) i++;
        
        return i < line.length() && line.charAt(i) == '=';
    }

    private static void extractValues(String data, List<String> result) 
    {
        int start = data.indexOf('=') + 1;
        int length = data.length();
        
        while (start < length) 
        {
            while (start < length && Character.isWhitespace(data.charAt(start))) start++;
            if (start >= length) break;

            int end = findValueEnd(data, start);
            String value = data.substring(start, end).trim();
            if (!value.isEmpty()) result.add(value);
            
            start = end + 1;
        }
    }

    private static int findValueEnd(String str, int start) 
    {
        for (int i = start; i < str.length(); i++)
            if (str.charAt(i) == ',') 
                return i;
        return str.length();
    }
    
    public static void clearCache() { configCache.clear(); }
    public static void removeFromCache(String path, String key) { configCache.remove(path + ":" + key); }
}