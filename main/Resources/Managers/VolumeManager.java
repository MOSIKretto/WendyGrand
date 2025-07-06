package main.Resources.Managers;

import main.Resources.GeneralHelper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class VolumeManager 
{

    private static final String VOLUME_CMD = "pactl";
    private static final String DEFAULT_SINK = "@DEFAULT_SINK@";

    private static final Map<String, Integer> NUMBER_MAP = createNumberMap();

    private static Map<String, Integer> createNumberMap() 
    {
        Map<String, Integer> map = new HashMap<>();
        
        String[] units = {"ноль", "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять"};
        String[] teens = {"десять", "одиннадцать", "двенадцать", "тринадцать", "четырнадцать", "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать"};
        String[] tens = {"", "", "двадцать", "тридцать", "сорок", "пятьдесят", "шестьдесят", "семьдесят", "восемьдесят", "девяносто"};

        // заполнение базовых чисел
        for (int i = 0; i <= 9; i++) 
        {
            map.put(units[i], i);
            if (i < teens.length)
                map.put(teens[i], i + 10);
        }

        // заполнение составных чисел (20-99)
        for (int i = 2; i <= 9; i++) 
        {
            map.put(tens[i], i * 10);
            for (int j = 1; j <= 9; j++)
                map.put(tens[i] + " " + units[j], i * 10 + j);
        }

        // спец команды
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
        map.put("звук больше", -2);
        map.put("звук меньше", -3);
        map.put("громче", -2);
        map.put("тише", -3);

        return map;
    }
    
    public static void handleVolumeCommand(String volumeText) throws 
    IOException 
    {
        // проверка на точное совпадения
        Integer volume = NUMBER_MAP.get(volumeText.toLowerCase());
        if (volume != null) 
        {
            setSystemVolume(volume);
            GeneralHelper.Voiceover("volume");
            return;
        }

        // Проверка на частичное совпадения
        for (Map.Entry<String, Integer> entry : NUMBER_MAP.entrySet()) 
        {
            if (volumeText.toLowerCase().contains(entry.getKey())) 
            {
                setSystemVolume(entry.getValue());
                GeneralHelper.Voiceover("volume");
                return;
            }
        }

        GeneralHelper.Voiceover("volumeErr");
    }
    
    private static void setSystemVolume(int volume) throws 
    IOException 
    {
        try 
        {
            String[] command;
            
            if (volume == -2)
                command = new String[]{VOLUME_CMD, "set-sink-volume", DEFAULT_SINK, "+10%"};

            else if (volume == -3)
                command = new String[]{VOLUME_CMD, "set-sink-volume", DEFAULT_SINK, "-10%"};

            else
                command = new String[]{VOLUME_CMD, "set-sink-volume", DEFAULT_SINK, volume + "%"};
            
            GeneralHelper.Performer(command);
        } 
        catch (Exception e) 
        {
            System.err.println("Ошибка изменения громкости: " + e.getMessage());
            GeneralHelper.Voiceover("volumeErrUtil");
        }
    }
}