package src.main.logic.helpers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class ConfigReader {
    private static final Map<String, Map<String, List<String>>> CACHE = new ConcurrentHashMap<>();
    private static final Pattern COMMA_SPLIT = Pattern.compile("\\s*,\\s*");
    
    public static List<String> readConfig(String path, String key) throws IOException {
        Map<String, List<String>> config = CACHE.get(path);
        if (config != null) {
            List<String> result = config.get(key);
            return result != null ? result : Collections.emptyList();
        }
        
        config = new ConcurrentHashMap<>();
        String content = Files.readString(Path.of(path));
        final int length = content.length();
        boolean inComment = false;
        boolean inContinuation = false;
        StringBuilder currentLine = new StringBuilder(128);
        
        for (int i = 0; i < length; i++) {
            char c = content.charAt(i);
            
            // Обработка комментариев
            if (c == '#') {
                inComment = true;
                continue;
            }
            
            if (c == '\n' || c == '\r') {
                if (c == '\r' && i + 1 < length && content.charAt(i + 1) == '\n') {
                    i++;
                }
                
                if (!inContinuation && !inComment && currentLine.length() > 0) {
                    processLineFast(config, currentLine.toString());
                    currentLine.setLength(0);
                }
                
                inComment = false;
                inContinuation = false;
                continue;
            }
            
            if (inComment) continue;
            
            // Обработка продолжения строки
            if (c == '\\' && i + 1 < length) {
                char next = content.charAt(i + 1);
                if (next == '\n' || next == '\r') {
                    inContinuation = true;
                    i++;
                    continue;
                }
            }
            
            if (!inContinuation) {
                currentLine.append(c);
            }
        }
        
        if (!inComment && currentLine.length() > 0) {
            processLineFast(config, currentLine.toString());
        }
        
        CACHE.put(path, config);
        List<String> result = config.get(key);
        return result != null ? result : Collections.emptyList();
    }
    
    private static void processLineFast(Map<String, List<String>> config, String line) {
        final int len = line.length();
        int eqIndex = -1;
        
        // Быстрый поиск позиции '='
        for (int i = 0; i < len; i++) {
            char c = line.charAt(i);
            if (c == '=') {
                eqIndex = i;
                break;
            }
            if (Character.isWhitespace(c)) continue;
        }
        
        if (eqIndex <= 0) return;
        
        // Извлечение ключа
        String key = line.substring(0, eqIndex).trim();
        if (key.isEmpty()) return;
        
        // Извлечение значений
        String valuesStr = line.substring(eqIndex + 1).trim();
        if (valuesStr.isEmpty()) {
            config.put(key, Collections.emptyList());
            return;
        }
        
        List<String> values;
        if (!valuesStr.contains("\"")) {
            values = Arrays.asList(COMMA_SPLIT.split(valuesStr));
        } else {
            values = new ArrayList<>();
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
            values.add(valuesStr.substring(start).trim());
        }
        
        config.put(key, Collections.unmodifiableList(values));
    }
    
    public static void clearCache() {
        CACHE.clear();
    }
    
    public static void clearCache(String path) {
        CACHE.remove(path);
    }
}