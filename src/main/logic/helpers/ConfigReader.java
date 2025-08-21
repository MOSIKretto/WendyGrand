package src.main.logic.helpers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigReader {
    private static final Map<String, Map<String, List<String>>> CACHE = new ConcurrentHashMap<>();
    
    public static List<String> readConfig(String path, String key) throws IOException {
        Map<String, List<String>> config = CACHE.get(path);
        if (config != null) {
            return config.getOrDefault(key, Collections.emptyList());
        }
        
        config = new ConcurrentHashMap<>();
        String content = Files.readString(Path.of(path));
        int length = content.length();
        StringBuilder currentLine = new StringBuilder();
        boolean inContinuation = false;
        
        for (int i = 0; i < length; i++) {
            char c = content.charAt(i);
            
            // Пропускаем комментарии
            if (c == '#') {
                while (i < length && content.charAt(i) != '\n' && content.charAt(i) != '\r') i++;
                continue;
            }
            
            // Обрабатываем конец строки
            if (c == '\n' || c == '\r') {
                if (c == '\r' && i + 1 < length && content.charAt(i + 1) == '\n') i++;
                
                if (!inContinuation) {
                    processLine(config, currentLine.toString());
                    currentLine.setLength(0);
                } else {
                    inContinuation = false;
                }
                continue;
            }
            
            // Проверяем на продолжение строки
            if (c == '\\' && i + 1 < length && 
                (content.charAt(i + 1) == '\n' || content.charAt(i + 1) == '\r')) {
                inContinuation = true;
                i++; // Пропускаем символ перевода строки
                continue;
            }
            
            currentLine.append(c);
        }
        
        // Обрабатываем последнюю строку
        if (currentLine.length() > 0) {
            processLine(config, currentLine.toString());
        }
        
        CACHE.put(path, config);
        return config.getOrDefault(key, Collections.emptyList());
    }
    
    private static void processLine(Map<String, List<String>> config, String line) {
        line = line.trim();
        if (line.isEmpty()) return;
        
        int eqIndex = line.indexOf('=');
        if (eqIndex <= 0) return;
        
        String key = line.substring(0, eqIndex).trim();
        String valuesStr = line.substring(eqIndex + 1).trim();
        
        if (valuesStr.isEmpty()) {
            config.put(key, Collections.emptyList());
            return;
        }
        
        // Быстрый парсинг значений
        List<String> values = new ArrayList<>();
        int start = 0;
        boolean inQuotes = false;
        
        for (int i = 0; i < valuesStr.length(); i++) {
            char c = valuesStr.charAt(i);
            
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                values.add(valuesStr.substring(start, i).trim());
                start = i + 1;
            }
        }
        
        // Добавляем последнее значение
        if (start < valuesStr.length()) {
            values.add(valuesStr.substring(start).trim());
        }
        
        config.put(key, values);
    }
    
    public static void clearCache() {
        CACHE.clear();
    }
    
    public static void clearCache(String path) {
        CACHE.remove(path);
    }
}