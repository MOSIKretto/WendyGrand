package main.Resources;

import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.util.AbstractMap;
import java.util.Collection;
import java.io.IOException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Objects;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class GeneralHelper
{
    // Чтение конфигов
    public static List<String> readConfig(String path, String key) throws 
    IOException 
    {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) 
        {
            return br.lines()
            .map(line -> {
                // Удаляем комментарии (всё после #) и обрезаем пробелы
                int commentIndex = line.indexOf('#');

                if (commentIndex != -1)
                    line = line.substring(0, commentIndex);

                return line.trim();
            })
            .filter(line -> !line.isEmpty()) // Игнорируем пустые строки
            .map(line -> {
                int eqIndex = line.indexOf('=');

                if (eqIndex == -1)
                    return null; // Пропускаем строки без '='
                    
                // Разделяем на ключ и значение с удалением пробелов
                String k = line.substring(0, eqIndex).trim();
                String v = line.substring(eqIndex + 1).trim();
                return new AbstractMap.SimpleEntry<>(k, v);
            })
            .filter(Objects::nonNull) // Отфильтровываем строки без '='
            .filter(entry -> key.equals(entry.getKey())) // Ищем нужный ключ
            .map(Map.Entry::getValue)
            .flatMap(value -> Arrays.stream(value.split(",\\s*"))) // Разбиваем значения
            .collect(Collectors.toList());
        }
    }

    // Очищение текста
    public static String cleanInput(String input, Collection<String> words) 
    {
        Set<String> toRemove = words instanceof Set ? (Set<String>)words : new HashSet<>(words);
        return Arrays.stream(input.split("\\s+"))
        .filter(word -> !toRemove.contains(word))
        .collect(Collectors.joining(" "));
    }
    
    // Отсчет выключения системы
    public static void message(String message) throws 
    InterruptedException 
    {
        System.out.println("Система будет " + message + " через 5 секунд...");
        
        for (int i = 5; i > 0; i--) 
        {
            System.out.println(i);
            Thread.sleep(1000);
        }
    }
    
    // Озвучка
    public static void Voiceover(String FunctionVoice) throws 
    IOException 
    {
        GeneralHelper.Performer("python3", "../WendyGrand/main/Python/Voiceover.py", FunctionVoice);
    }

    // Запуск процессов
    public static void Performer(String... command) throws 
    IOException 
    {
        new ProcessBuilder(command)
        .inheritIO()
        .start();
    }
}