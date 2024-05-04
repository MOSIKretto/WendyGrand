/* *Java_Dictionary*
 *
 * RU Ищет соответствие по словарям и отдает команду на выполнение 
 * ---------------------------------------------------------------------
 * EN Looks for a match in dictionaries and issues a command to execute
 * 
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import static java.util.Map.entry;

public class Java_Dictionary 
{
    public static void main(String[] args)
    {
        for (String arg : args) 
        {
            System.out.println(arg);
            System.out.println(System.getProperty("user.dir"));

            /*
            * RU Словари с командами 
            * EU Dictionaries with commands
            */

            Map <String, String> FunctionsDictionary = Map.ofEntries(
                entry("Browser", "CallBrowser"), 
                entry("Telegram", "CallTelegram"),
                entry("VScode", "CallVScode")
            );

            ArrayList <String> Hello = new ArrayList<String>(
                Arrays.asList("привет", "здравствуй", "ты тут")
            );

            ArrayList <String> HowYou = new ArrayList<String>(
                Arrays.asList("как дела", "как ты")
            );

            ArrayList <String> Browser = new ArrayList<String>(
                Arrays.asList("открой браузер", "браузер", "интернет", "открой интернет")
            );

            ArrayList <String> Telegram = new ArrayList<String>(
                Arrays.asList("открой телеграм", "открой telegram", "телеграм", "telegram", "телега","открой телегу")
            );

            ArrayList <String> VScode = new ArrayList<String>(
                Arrays.asList("открой vs code", "открой vs код", "vs", "vs code", "vs код")
            );

            /*
            * RU Проверка на соответсвие и отдача команды на выполнение задачи 
            * EU Checking for compliance and issuing a command to complete the task
            */

            if (Hello.contains(arg))
            {
                System.out.println("Привет, User!");
            }
            //--------------------------------------------------------------------------------------------------------------
            if (HowYou.contains(arg))
            {
                System.out.println("Хорошо! Надеюсь у вас еще лучше, User!");
            }
            //--------------------------------------------------------------------------------------------------------------
            if (Browser.contains(arg))
            {
                ActionHandler.CallFunction(FunctionsDictionary.get("Browser"));
            }
            //--------------------------------------------------------------------------------------------------------------
            if (Telegram.contains(arg))
            {
                ActionHandler.CallFunction(FunctionsDictionary.get("Telegram"));
            }
            if (VScode.contains(arg))
            {
                ActionHandler.CallFunction(FunctionsDictionary.get("VScode"));
            }
        }
    }
}
