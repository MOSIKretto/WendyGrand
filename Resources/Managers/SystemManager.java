package Resources.Managers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import Resources.GeneralHelper;


public class SystemManager 
{
    
    private static final String SHUTDOWN_CMD = "shutdown";
    private static final String SLEEP_CMD = "systemctl";
    private static final String VOLUME_CMD = "pactl";
    private static final String DEFAULT_SINK = "@DEFAULT_SINK@";

    private static final Map<String, Integer> NUMBER_MAP = createNumberMap();

    private static Map<String, Integer> createNumberMap() 
    {
        Map<String, Integer> map = new HashMap<>();
        
        String[] units = {"ноль", "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять"};
        String[] teens = {"десять", "одиннадцать", "двенадцать", "тринадцать", "четырнадцать", 
                            "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать"};
        String[] tens = {"", "", "двадцать", "тридцать", "сорок", "пятьдесят", 
                        "шестьдесят", "семьдесят", "восемьдесят", "девяносто"};

        // Заполнение базовых чисел
        for (int i = 0; i <= 9; i++) 
        {
            map.put(units[i], i);
            map.put(teens[i], i + 10);
        }

        // Заполнение составных чисел (20-99)
        for (int i = 2; i <= 9; i++) 
        {
            map.put(tens[i], i * 10);
            for (int j = 1; j <= 9; j++)
                map.put(tens[i] + " " + units[j], i * 10 + j);
        }

        // Специальные команды
        map.put("сто", 100);
        map.put("выключи звук", 0);
        map.put("включи звук", 50);
        map.put("увеличь", -2);
        map.put("уменьши", -3);
        map.put("больше", -2);
        map.put("меньше", -3);
        map.put("увеличить", -2);
        map.put("уменьшить", -3);
        map.put("звук больше", -2);
        map.put("звук меньше", -3);

        return map;
    }

    // Управление питанием системы
    public static void systemShutdown(String arg, String message) throws IOException, InterruptedException 
    {
        GeneralHelper.message(message);
        executeCommand(new String[]{SHUTDOWN_CMD, arg, "now"});
    }

    public static void systemSleep(String message) throws IOException, InterruptedException 
    {
        GeneralHelper.message(message);
        executeCommand(new String[]{SLEEP_CMD, "suspend", "-i"});
    }

    // Управление громкостью
    public static void handleVolumeCommand(String volumeText) 
    {
        Integer volume = NUMBER_MAP.get(volumeText.toLowerCase());
        
        if (volume != null)
            setSystemVolume(volume);
        else
            System.err.println("Неизвестная команда громкости: " + volumeText);
    }

    private static void setSystemVolume(int volume) 
    {
        try 
        {
            String[] command;
            
            if (volume == -2)
                command = new String[]{VOLUME_CMD, "set-sink-volume", DEFAULT_SINK, "+25%"};

            else if (volume == -3)
                command = new String[]{VOLUME_CMD, "set-sink-volume", DEFAULT_SINK, "-25%"};
                
            else
                command = new String[]{VOLUME_CMD, "set-sink-volume", DEFAULT_SINK, volume + "%"};
            
            executeCommand(command);
        } 
        catch (Exception e) {
            System.err.println("Ошибка изменения громкости: " + e.getMessage());}
    }

    // Унифицированный метод выполнения команд
    private static void executeCommand(String[] command) throws IOException, InterruptedException 
    {
        new ProcessBuilder(command)
            .inheritIO()
            .start()
            .waitFor();
    }
}