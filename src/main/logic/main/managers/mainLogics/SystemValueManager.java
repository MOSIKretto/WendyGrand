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

        String[] units = {"ноль", "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять"};
        String[] teens = {"десять", "одиннадцать", "двенадцать", "тринадцать", "четырнадцать", 
                         "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать"};
        String[] tens = {"", "", "двадцать", "тридцать", "сорок", "пятьдесят", 
                        "шестьдесят", "семьдесят", "восемьдесят", "девяносто"};
        
        for (int i = 0; i < units.length; i++)
            map.put(units[i], i);

        for (int i = 0; i < teens.length; i++)
            map.put(teens[i], i + 10);

        for (int i = 2; i < tens.length; i++) 
        {
            if (!tens[i].isEmpty()) 
            {
                map.put(tens[i], i * 10);

                for (int j = 0; j < units.length; j++)
                    map.put(tens[i] + " " + units[j], i * 10 + j);
            }
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

        for (int i = 0; i < words.length; i++) {
            Integer value = NUMBER_MAP.get(words[i]);

            if (value != null) 
                return value;
            
            if (i < words.length - 1) 
            {
                value = NUMBER_MAP.get(words[i] + " " + words[i + 1]);
                if (value != null) 
                    return value;
            }
        }
        return null;
    }
    
    public static void setSystemVolume(int volume) throws 
    IOException 
    {
        String arg = switch (volume) {
            case -2 -> "+10%";
            case -3 -> "-10%";
            default -> volume + "%";
        };
        Performer.execute(VOLUME_CMD, "set-sink-volume", DEFAULT_SINK, arg);
    }
    
    public static void setSystemBrightness(int brightness) throws 
    IOException 
    {
        String arg = switch (brightness) {
            case -2 -> "10%+";
            case -3 -> "10%-";
            default -> brightness + "%";
        };
        Performer.execute(BRIGHTNESS_CMD, "set", arg);
    }
}