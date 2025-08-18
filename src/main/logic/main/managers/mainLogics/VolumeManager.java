package src.main.logic.main.managers.mainLogics;

import src.main.logic.helpers.GeneralHelper;
import java.util.Collections;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class VolumeManager 
{

    public static final String VOLUME_CMD = "pactl";
    public static final String DEFAULT_SINK = "@DEFAULT_SINK@";
    
    public static final Map<String, Integer> NUMBER_MAP;
    
    static
    {
        Map<String, Integer> map = new HashMap<>();
        
        final String[] units = {"ноль", "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять"};
        final String[] teens = {"десять", "одиннадцать", "двенадцать", "тринадцать", "четырнадцать", 
                                "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать"};
        final String[] tens = {"", "", "двадцать", "тридцать", "сорок", "пятьдесят", 
                                "шестьдесят", "семьдесят", "восемьдесят", "девяносто"};
        
        // Базовые числа (0-9)
        for (int i = 0; i < units.length; i++)
            map.put(units[i], i);
        
        // Числа 10-19
        for (int i = 0; i < teens.length; i++)
            map.put(teens[i], i + 10);
        
        // Составные числа (20-99)
        for (int i = 2; i < tens.length; i++) 
        {
            map.put(tens[i], i * 10);
            for (int j = 0; j < units.length; j++)
                map.put(tens[i] + " " + units[j], i * 10 + j);
        }
        
        // Специальные команды
        map.put("сто", 100);
        map.put("максимум", 100);
        map.put("выключи звук", 0);
        map.put("минимум", 10);
        map.put("включи звук", 50);
        map.put("увеличь", -2);
        map.put("уменьши", -3);
        map.put("больше", -2);
        map.put("меньше", -3);
        map.put("увеличить", -2);
        map.put("уменьшить", -3);
        map.put("громче", -2);
        map.put("тише", -3);
        
        NUMBER_MAP = Collections.unmodifiableMap(map);
    }
    
    public static void setSystemVolume(int volume) throws 
    IOException 
    {
        String arg = switch (volume) 
        {
            case -2 -> "+10%";
            case -3 -> "-10%";
            default -> volume + "%";
        };
        
        GeneralHelper.Performer(new String[]{VOLUME_CMD, "set-sink-volume", DEFAULT_SINK, arg});
    }
}