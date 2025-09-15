package src.main.logic.main.managers;

import src.main.logic.helpers.Performer;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SystemValueManager 
{
    private static final Map<String, Integer> NUMBER_MAP = new HashMap<>();
    static 
    {
        String[] units = {"ноль","один","два","три","четыре","пять","шесть","семь","восемь","девять"};
        String[] teens = {"десять","одиннадцать","двенадцать","тринадцать","четырнадцать","пятнадцать","шестнадцать","семнадцать","восемнадцать","девятнадцать"};
        String[] tens = {"двадцать","тридцать","сорок","пятьдесят","шестьдесят","семьдесят","восемьдесят","девяносто"};
        
        for (int i = 0; i < units.length; i++) NUMBER_MAP.put(units[i], i);
        for (int i = 0; i < teens.length; i++) NUMBER_MAP.put(teens[i], i + 10);
        for (int i = 0; i < tens.length; i++) {
            int value = (i + 2) * 10;
            NUMBER_MAP.put(tens[i], value);
            for (int j = 1; j < 10; j++) NUMBER_MAP.put(tens[i] + " " + units[j], value + j);
        }
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
        
        for (String word : words) 
        {
            Integer value = NUMBER_MAP.get(word);
            if (value != null) return value;
        }
        
        return null;
    }
    
    public static void setSystemVolume(int volume) throws 
    IOException 
    {
        String arg;
        if (volume == -2) arg = "+10%";
        else if (volume == -3) arg = "-10%";
        else arg = volume + "%";
        Performer.execute("pactl", "set-sink-volume", "@DEFAULT_SINK@", arg);
    }
    
    public static void setSystemBrightness(int brightness) throws 
    IOException 
    {
        String arg;
        if (brightness == -2) arg = "10%+";
        else if (brightness == -3) arg = "10%-";
        else arg = brightness + "%";
        Performer.execute("brightnessctl", "set", arg);
    }
}