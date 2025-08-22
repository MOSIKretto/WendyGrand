package src.main.logic.main.managers.mainLogics;

import src.main.logic.helpers.Performer;

import java.io.IOException;
import java.util.*;


public class SystemValueManager 
{
    public static final String VOLUME_CMD = "pactl";
    public static final String BRIGHTNESS_CMD = "brightnessctl";
    public static final String DEFAULT_SINK = "@DEFAULT_SINK@";
    public static final Map<String, Integer> NUMBER_MAP;
    
    static 
    {
        Map<String, Integer> map = new HashMap<>(100);
        
        // Единицы
        String[] units = {"ноль", "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять"};
        for (int i = 0; i < units.length; i++) map.put(units[i], i);
        
        // Десятки (10-19)
        String[] teens = {"десять", "одиннадцать", "двенадцать", "тринадцать", "четырнадцать", 
                         "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать"};
        for (int i = 0; i < teens.length; i++) map.put(teens[i], i + 10);
        
        // Десятки (20-90)
        String[] tens = {"двадцать", "тридцать", "сорок", "пятьдесят", "шестьдесят", "семьдесят", "восемьдесят", "девяносто"};
        for (int i = 0; i < tens.length; i++) 
        {
            int value = (i + 2) * 10;
            map.put(tens[i], value);
            
            // Составные числа (21, 32 и т.д.)
            for (int j = 1; j < 10; j++) map.put(tens[i] + " " + units[j], value + j);
        }
        
        NUMBER_MAP = Collections.unmodifiableMap(map);
    }
    
    public static boolean containsAny(String input, List<String> keywords) 
    {
        for (String keyword : keywords)
            if (input.contains(keyword)) 
                return true;

        return false;
    }
    
    public static Integer parseNumber(String input) 
    {
        String[] words = input.split("\\s+");
        for (int i = 0; i < words.length - 1; i++) 
        {
            String twoWordNumber = words[i] + " " + words[i + 1];
            Integer value = NUMBER_MAP.get(twoWordNumber);
            if (value != null) return value;
        }
        
        // Затем попробуем найти однозначные числа
        for (String word : words) 
        {
            Integer value = NUMBER_MAP.get(word);
            if (value != null)
                return value;
        }
        
        return null;
    }
    
    public static void setSystemVolume(int volume) throws 
    IOException 
    {
        String arg;
        switch (volume) 
        {
            case -2 -> arg = "+10%";
            case -3 -> arg = "-10%";
            default -> arg = volume + "%";
        }
        Performer.execute(VOLUME_CMD, "set-sink-volume", DEFAULT_SINK, arg);
    }
    
    public static void setSystemBrightness(int brightness) throws 
    IOException 
    {
        String arg;
        switch (brightness) 
        {
            case -2 -> arg = "10%+";
            case -3 -> arg = "10%-";
            default -> arg = brightness + "%";
        }
        Performer.execute(BRIGHTNESS_CMD, "set", arg);
    }
}