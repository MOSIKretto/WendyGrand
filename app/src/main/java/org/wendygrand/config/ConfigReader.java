package org.wendygrand.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class ConfigReader 
{
    private static final ConcurrentHashMap<String, CachedConfig> configCache = new ConcurrentHashMap<>();
    private static final Pattern DELAY_PATTERN = Pattern.compile("^(.*?)\\((\\d+)\\)$");
    private static final long CACHE_REFRESH_INTERVAL = 30000;
    
    public static class ApplicationWithDelay {
        private final String application;
        private final int delay;
        
        public ApplicationWithDelay(String application, int delay) {
            this.application = application;
            this.delay = Math.max(delay, 0);
        }
        
        public String getApplication() { return application; }
        public int getDelay() { return delay; }
    }
    
    private static class CommandEntry {
        final List<ApplicationWithDelay> applications;
        final List<String> activators;
        final List<String> voiceResponses;
        
        CommandEntry(List<ApplicationWithDelay> applications, List<String> activators, List<String> voiceResponses) {
            this.applications = applications != null ? new ArrayList<>(applications) : new ArrayList<>();
            this.activators = activators != null ? new ArrayList<>(activators) : new ArrayList<>();
            this.voiceResponses = voiceResponses != null ? new ArrayList<>(voiceResponses) : new ArrayList<>();
        }
    }
    
    private static class CachedConfig {
        final long lastModified;
        final long cacheTime;
        final List<CommandEntry> commands;
        final List<String> deleteWords;
        final Map<String, List<String>> voiceOnlyCommands;
        
        CachedConfig(long lastModified, List<CommandEntry> commands, 
                    List<String> deleteWords, Map<String, List<String>> voiceOnlyCommands) {
            this.lastModified = lastModified;
            this.cacheTime = System.currentTimeMillis();
            this.commands = commands != null ? new ArrayList<>(commands) : new ArrayList<>();
            this.deleteWords = deleteWords != null ? new ArrayList<>(deleteWords) : new ArrayList<>();
            this.voiceOnlyCommands = voiceOnlyCommands != null ? new HashMap<>(voiceOnlyCommands) : new HashMap<>();
        }
        
        boolean isExpired() {
            return System.currentTimeMillis() - cacheTime > CACHE_REFRESH_INTERVAL;
        }
    }

    public static class CommandResult {
        private final List<ApplicationWithDelay> applications;
        private final List<String> voiceResponses;
        
        public CommandResult(List<ApplicationWithDelay> applications, List<String> voiceResponses) {
            this.applications = applications != null ? new ArrayList<>(applications) : new ArrayList<>();
            this.voiceResponses = voiceResponses != null ? new ArrayList<>(voiceResponses) : new ArrayList<>();
        }
        
        public List<ApplicationWithDelay> getApplications() { return new ArrayList<>(applications); }
        public boolean hasApplication() { return applications != null && !applications.isEmpty(); }
        public boolean hasVoiceResponses() { return voiceResponses != null && !voiceResponses.isEmpty(); }
        public List<String> getVoiceResponses() { return new ArrayList<>(voiceResponses); }
    }

    public static List<String> readConfig(String path, String key) throws IOException {
        CachedConfig cached = getCachedConfig(path);
        return "delete".equals(key) ? new ArrayList<>(cached.deleteWords) : new ArrayList<>();
    }

    public static CommandResult findCommand(String path, String input) throws IOException {
        if (input == null || input.trim().isEmpty()) {
            return null;
        }
        
        CachedConfig cached = getCachedConfig(path);
        String normalizedInput = input.toLowerCase().trim();
        
        // Поиск в обычных командах
        for (CommandEntry entry : cached.commands) {
            for (String activator : entry.activators) {
                if (normalizedInput.contains(activator.toLowerCase())) {
                    return new CommandResult(entry.applications, entry.voiceResponses);
                }
            }
        }
        
        // Поиск в голосовых командах
        for (Map.Entry<String, List<String>> entry : cached.voiceOnlyCommands.entrySet()) {
            for (String activator : entry.getKey().split("\\s*,\\s*")) {
                if (normalizedInput.contains(activator.toLowerCase().trim())) {
                    return new CommandResult(null, entry.getValue());
                }
            }
        }
        
        return null;
    }

    private static CachedConfig getCachedConfig(String path) throws IOException {
        Path filePath = Paths.get(path);
        if (!Files.exists(filePath)) {
            throw new IOException("Конфигурационный файл не существует: " + path);
        }
        
        long lastModified = Files.getLastModifiedTime(filePath).toMillis();
        CachedConfig cached = configCache.get(path);
        
        if (cached == null || cached.lastModified != lastModified || cached.isExpired()) {
            CachedConfig newConfig = parseConfigFile(filePath);
            configCache.put(path, newConfig);
            return newConfig;
        }
        
        return cached;
    }

    private static CachedConfig parseConfigFile(Path filePath) throws IOException {
        Map<String, String> variables = new HashMap<>();
        List<CommandEntry> commands = new ArrayList<>();
        List<String> deleteWords = new ArrayList<>();
        Map<String, List<String>> voiceOnlyCommands = new LinkedHashMap<>();
        
        List<String> mergedLines = readAndMergeLines(filePath);
        
        int lineNumber = 0;
        for (String line : mergedLines) {
            lineNumber++;
            try {
                line = removeComment(line).trim();
                if (line.isEmpty()) continue;
                
                if (line.startsWith("$")) {
                    parseVariable(line, variables);
                } else if (line.startsWith("delete")) {
                    parseDeleteWords(line, deleteWords, variables);
                } else if (line.startsWith("=")) {
                    parseVoiceOnlyCommand(line, voiceOnlyCommands, variables);
                } else if (countOccurrences(line, '=') >= 2) {
                    parseCommand(line, commands, variables);
                }
            } catch (Exception e) {
                System.err.println("Ошибка парсинга строки " + lineNumber + ": " + line + " - " + e.getMessage());
            }
        }
        
        return new CachedConfig(Files.getLastModifiedTime(filePath).toMillis(),
                commands, deleteWords, voiceOnlyCommands);
    }

    private static List<String> readAndMergeLines(Path filePath) throws IOException {
        List<String> mergedLines = new ArrayList<>();
        StringBuilder currentLine = new StringBuilder();
        
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = br.readLine()) != null) {
                line = removeComment(line);
                
                if (line.trim().endsWith("\\")) {
                    currentLine.append(line.substring(0, line.length() - 1).trim()).append(" ");
                } else {
                    currentLine.append(line);
                    mergedLines.add(currentLine.toString());
                    currentLine = new StringBuilder();
                }
            }
            
            if (currentLine.length() > 0) {
                mergedLines.add(currentLine.toString());
            }
        }
        
        return mergedLines;
    }

    private static void parseVariable(String line, Map<String, String> variables) {
        int equalsIndex = line.indexOf('=');
        if (equalsIndex > 0) {
            String varName = line.substring(0, equalsIndex).trim();
            String varValue = line.substring(equalsIndex + 1).trim();
            if (!varName.isEmpty() && !varValue.isEmpty()) {
                variables.put(varName, expandVariables(varValue, variables));
            }
        }
    }

    private static void parseDeleteWords(String line, List<String> deleteWords, Map<String, String> variables) {
        int equalsIndex = line.indexOf('=');
        if (equalsIndex > 0) {
            String valuesStr = expandVariables(line.substring(equalsIndex + 1).trim(), variables);
            if (!valuesStr.isEmpty()) {
                String[] words = valuesStr.split("\\s*,\\s*");
                for (String word : words) {
                    String trimmedWord = word.trim();
                    if (!trimmedWord.isEmpty()) {
                        deleteWords.add(trimmedWord);
                    }
                }
            }
        }
    }

    private static void parseVoiceOnlyCommand(String line, Map<String, List<String>> voiceOnlyCommands, 
            Map<String, String> variables) {
        String content = line.substring(1).trim();
        String[] parts = content.split("=", 2);
        if (parts.length == 2) {
            String activatorsStr = expandVariables(parts[0].trim(), variables);
            String voicesStr = expandVariables(parts[1].trim(), variables);
            if (!activatorsStr.isEmpty() && !voicesStr.isEmpty()) {
                voiceOnlyCommands.put(activatorsStr, parseVoiceList(voicesStr));
            }
        }
    }

    private static void parseCommand(String line, List<CommandEntry> commands, Map<String, String> variables) {
        String[] parts = line.split("=", 3);
        if (parts.length == 3) {
            String applicationsStr = expandVariables(parts[0].trim(), variables);
            String activatorsStr = expandVariables(parts[1].trim(), variables);
            String voicesStr = expandVariables(parts[2].trim(), variables);
            
            if (applicationsStr.isEmpty() || activatorsStr.isEmpty()) {
                return;
            }
            
            // Парсим список приложений с их задержками
            List<ApplicationWithDelay> applications = parseApplicationList(applicationsStr);
            List<String> activators = parseActivatorList(activatorsStr);
            List<String> voices = parseVoiceList(voicesStr);
            
            if (!applications.isEmpty() && !activators.isEmpty()) {
                commands.add(new CommandEntry(applications, activators, voices));
            }
        }
    }

    /**
     * Парсит список приложений с индивидуальными задержками
     * Пример: "chromium(5), firefox(15)" -> два ApplicationWithDelay
     */
    private static List<ApplicationWithDelay> parseApplicationList(String applicationsStr) {
        List<ApplicationWithDelay> applications = new ArrayList<>();
        String[] parts = applicationsStr.split("\\s*,\\s*");
        
        for (String part : parts) {
            String application = part.trim();
            if (application.isEmpty()) continue;
            
            int delay = 0;
            String actualApplication = application;
            
            // Проверяем наличие задержки в формате "приложение(задержка)"
            java.util.regex.Matcher matcher = DELAY_PATTERN.matcher(application);
            if (matcher.matches()) {
                actualApplication = matcher.group(1).trim();
                try {
                    delay = Integer.parseInt(matcher.group(2));
                } catch (NumberFormatException e) {
                    delay = 0;
                }
            }
            
            applications.add(new ApplicationWithDelay(actualApplication, delay));
        }
        
        return applications;
    }

    private static List<String> parseActivatorList(String activatorsStr) {
        List<String> activators = new ArrayList<>();
        String[] parts = activatorsStr.split("\\s*,\\s*");
        for (String part : parts) {
            String activator = part.trim();
            if (!activator.isEmpty()) {
                activators.add(activator);
            }
        }
        return activators;
    }

    private static List<String> parseVoiceList(String voicesStr) {
        List<String> voices = new ArrayList<>();
        String[] parts = voicesStr.split("\\s*,\\s*");
        for (String part : parts) {
            String voice = part.trim();
            if (!voice.isEmpty()) {
                voices.add(voice);
            }
        }
        return voices;
    }

    private static String expandVariables(String input, Map<String, String> variables) {
        String result = input;
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            result = result.replace(entry.getKey(), entry.getValue());
        }
        return result;
    }

    private static int countOccurrences(String str, char ch) {
        int count = 0;
        for (char c : str.toCharArray()) {
            if (c == ch) count++;
        }
        return count;
    }

    private static String removeComment(String line) {
        int idx = line.indexOf('#');
        return idx >= 0 ? line.substring(0, idx) : line;
    }
}