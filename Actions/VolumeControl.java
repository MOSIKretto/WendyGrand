package Actions;

import java.util.HashMap;
import java.util.Map;

public class VolumeControl
{
    private static final Map<String, Integer> numberMap = new HashMap<>();

    static 
    {
        numberMap.put("ноль", 0);
        numberMap.put("один", 1);
        numberMap.put("два", 2);
        numberMap.put("три", 3);
        numberMap.put("четыре", 4);
        numberMap.put("пять", 5);
        numberMap.put("шесть", 6);
        numberMap.put("семь", 7);
        numberMap.put("восемь", 8);
        numberMap.put("девять", 9);
        numberMap.put("десять", 10);
        numberMap.put("одиннадцать", 11);
        numberMap.put("двенадцать", 12);
        numberMap.put("тринадцать", 13);
        numberMap.put("четырнадцать", 14);
        numberMap.put("пятнадцать", 15);
        numberMap.put("шестнадцать", 16);
        numberMap.put("семнадцать", 17);
        numberMap.put("восемнадцать", 18);
        numberMap.put("девятнадцать", 19);
        numberMap.put("двадцать", 20);
        numberMap.put("двадцать один", 21);
        numberMap.put("двадцать два", 22);
        numberMap.put("двадцать три", 23);
        numberMap.put("двадцать четыре", 24);
        numberMap.put("двадцать пять", 25);
        numberMap.put("двадцать шесть", 26);
        numberMap.put("двадцать семь", 27);
        numberMap.put("двадцать восемь", 28);
        numberMap.put("двадцать девять", 29);
        numberMap.put("тридцать", 30);
        numberMap.put("тридцать один", 31);
        numberMap.put("тридцать два", 32);
        numberMap.put("тридцать три", 33);
        numberMap.put("тридцать четыре", 34);
        numberMap.put("тридцать пять", 35);
        numberMap.put("тридцать шесть", 36);
        numberMap.put("тридцать семь", 37);
        numberMap.put("тридцать восемь", 38);
        numberMap.put("тридцать девять", 39);
        numberMap.put("сорок", 40);
        numberMap.put("сорок один", 41);
        numberMap.put("сорок два", 42);
        numberMap.put("сорок три", 43);
        numberMap.put("сорок четыре", 44);
        numberMap.put("сорок пять", 45);
        numberMap.put("сорок шесть", 46);
        numberMap.put("сорок семь", 47);
        numberMap.put("сорок восемь", 48);
        numberMap.put("сорок девять", 49);
        numberMap.put("пятьдесят", 50);
        numberMap.put("пятьдесят один", 51);
        numberMap.put("пятьдесят два", 52);
        numberMap.put("пятьдесят три", 53);
        numberMap.put("пятьдесят четыре", 54);
        numberMap.put("пятьдесят пять", 55);
        numberMap.put("пятьдесят шесть", 56);
        numberMap.put("пятьдесят семь", 57);
        numberMap.put("пятьдесят восемь", 58);
        numberMap.put("пятьдесят девять", 59);
        numberMap.put("шестьдесят", 60);
        numberMap.put("шестьдесят один", 61);
        numberMap.put("шестьдесят два", 62);
        numberMap.put("шестьдесят три", 63);
        numberMap.put("шестьдесят четыре", 64);
        numberMap.put("шестьдесят пять", 65);
        numberMap.put("шестьдесят шесть", 66);
        numberMap.put("шестьдесят семь", 67);
        numberMap.put("шестьдесят восемь", 68);
        numberMap.put("шестьдесят девять", 69);
        numberMap.put("семьдесят", 70);
        numberMap.put("семьдесят один", 71);
        numberMap.put("семьдесят два", 72);
        numberMap.put("семьдесят три", 73);
        numberMap.put("семьдесят четыре", 74);
        numberMap.put("семьдесят пять", 75);
        numberMap.put("семьдесят шесть", 76);
        numberMap.put("семьдесят семь", 77);
        numberMap.put("семьдесят восемь", 78);
        numberMap.put("семьдесят девять", 79);
        numberMap.put("восемьдесят", 80);
        numberMap.put("восемьдесят один", 81);
        numberMap.put("восемьдесят два", 82);
        numberMap.put("восемьдесят три", 83);
        numberMap.put("восемьдесят четыре", 84);
        numberMap.put("восемьдесят пять", 85);
        numberMap.put("восемьдесят шесть", 86);
        numberMap.put("восемьдесят семь", 87);
        numberMap.put("восемьдесят восемь", 88);
        numberMap.put("восемьдесят девять", 89);
        numberMap.put("девяносто", 90);
        numberMap.put("девяносто один", 91);
        numberMap.put("девяносто два", 92);
        numberMap.put("девяносто три", 93);
        numberMap.put("девяносто четыре", 94);
        numberMap.put("девяносто пять", 95);
        numberMap.put("девяносто шесть", 96);
        numberMap.put("девяносто семь", 97);
        numberMap.put("девяносто восемь", 98);
        numberMap.put("девяносто девять", 99);
        numberMap.put("сто", 100);
        numberMap.put("выключи звук", 0);
        numberMap.put("включи звук", 50);
        numberMap.put("увеличь громкость", -2); 
        numberMap.put("уменьши громкость", -3);
        numberMap.put("увеличить громкость", -2);
        numberMap.put("уменьшить громкость", -3);
        numberMap.put("увеличить громкость", -2);
        numberMap.put("уменьшить громкость", -3);
    }

    public static void VolumeArgs(String volumeText) 
    {
        int volume = convertTextToNumber(volumeText);

        if (volume != -1) 
        {
            setSystemVolume(volume);
        } 
        else 
        {
            // Не выводим сообщение об ошибке в терминал
        }
    }

    private static int convertTextToNumber(String text) 
    {
        return numberMap.getOrDefault(text, -1);
    }

    private static void setSystemVolume(int volume) 
    {
        try 
        {
            String command;
            if (volume == -2) 
            {
                command = "pactl set-sink-volume @DEFAULT_SINK@ +25%";
            } 
            else if (volume == -3) 
            {
                command = "pactl set-sink-volume @DEFAULT_SINK@ -25%";
            } 
            else 
            {
                command = "pactl set-sink-volume @DEFAULT_SINK@ " + volume + "%";
            }
            Runtime.getRuntime().exec(command).waitFor();
        } 
        catch (Exception e){}
    }
}