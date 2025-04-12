package Actions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class SystemManager
{
    // Перезапуск выключение и спящий режим
    public static void systemShutdown(String arg, String message) throws IOException, InterruptedException
    {
        message(message);
        
        new ProcessBuilder("shutdown", arg, "now")
        .start()
        .waitFor();
    }

    @SuppressWarnings("deprecation")
    public static void systemSleep(String arg, String message) throws IOException, InterruptedException
    {
        message(message);

        Runtime.getRuntime().exec("systemctl suspend"); 
    }


    private static void message(String message) throws IOException, InterruptedException
    {
        System.out.println("Система будет" + message + "через 5 секунд...");
        
        for (int i = 5; i >= 0; i--)
        {
            System.out.println(i);
            Thread.sleep(1000);
        }
    }


    //Громкость
    private static final Map<String, Integer> numberMap = new HashMap<>();

    static 
    {
        String[] units = {"ноль", "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять"};
        String[] teens = {"десять", "одиннадцать", "двенадцать", "тринадцать", "четырнадцать", "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать"};
        String[] tens = {"", "", "двадцать", "тридцать", "сорок", "пятьдесят", "шестьдесят", "семьдесят", "восемьдесят", "девяносто"};

        for (int i = 0; i <= 9; i++)
        numberMap.put(units[i], i);
        
        for (int i = 0; i <= 9; i++)
        numberMap.put(teens[i], i + 10);
        
        for (int i = 2; i <= 9; i++) 
        {
            numberMap.put(tens[i], i * 10);

            for (int j = 1; j <= 9; j++) 
            numberMap.put(tens[i] + " " + units[j], i * 10 + j);
        }
        // Специальные команды
        numberMap.put("сто", 100);
        numberMap.put("выключи звук", 0);
        numberMap.put("включи звук", 50);
        numberMap.put("увеличь", -2);
        numberMap.put("уменьши", -3);
        numberMap.put("больше", -2);
        numberMap.put("меньше", -3);
        numberMap.put("увеличить", -2);
        numberMap.put("уменьшить", -3);
        numberMap.put("звук больше", -2);
        numberMap.put("звук меньше", -3);
    }

    public static void volumeArgs(String volumeText) 
    {
        int volume = convertTextToNumber(volumeText);

        if (volume != -1)
        setSystemVolume(volume);
        
        else 
        // Обработка отсутствующего значения (Должна быть озвучка по идее)
        System.out.println("Неизвестная команда: " + volumeText);
    }

    private static int convertTextToNumber(String text) 
    {
        return numberMap.getOrDefault(text, -1);
    }

    @SuppressWarnings("deprecation")
    private static void setSystemVolume(int volume)
    {
        try 
        {
            String command;

            switch (volume) 
            {
                case -2:
                    command = "pactl set-sink-volume @DEFAULT_SINK@ +25%";
                    break;

                case -3:
                    command = "pactl set-sink-volume @DEFAULT_SINK@ -25%";
                    break;

                default:
                    command = "pactl set-sink-volume @DEFAULT_SINK@ " + volume + "%";
                    break;
            }
            Runtime.getRuntime()
            .exec(command)
            .waitFor();
        }
        catch (Exception e){
            e.printStackTrace();}
    }
}
