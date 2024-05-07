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
import java.util.stream.Collectors;

import static java.util.Map.entry;

public class Java_Dictionary 
{
    public static void main(String[] args)
    {
        for (String arg : args) 
        {
            /*
            * RU Ненужный мусор во фразах и его удаление 
            * EU Unnecessary garbage in phrases and its removal
            */

            ArrayList <String> Remove = new ArrayList<String>(
                Arrays.asList(
                    "пожалуйста", "ладно", "давай", "сейчас", "типо", "типа", "будь", "добра", "ну", 
                    "что-то",  "что", "то", "открой"
                )
            );
            
            String ClearText = Arrays.stream(arg.split("\\s+"))
                .filter(word -> !Remove.contains(word))
                .collect(Collectors.joining(" "));
            System.out.println("Java_Dictionary: " + ClearText);
    
            /*
            * RU Словари с командами 
            * EU Dictionaries with commands
            */

            Map <String, String> FunctionsDictionary = Map.ofEntries(
                entry("Browser", "CallBrowser"), 
                entry("Telegram", "CallTelegram"),
                entry("VScode", "CallVScode"),
                entry("Stores", "CallStores")
            );

            ArrayList <String> Hello = new ArrayList<String>(
                Arrays.asList("привет", "здравствуй", "ты тут")
            );

            ArrayList <String> HowYou = new ArrayList<String>(
                Arrays.asList("как дела", "как ты")
            );

            ArrayList <String> Browser = new ArrayList<String>(
                Arrays.asList("браузер", "браузера", "интернет")
            );

            ArrayList <String> Telegram = new ArrayList<String>(
                Arrays.asList("telegram", "телеграм", "телега", "телегу", "телеграмма", "телеграма", "телеграмм")
            );

            ArrayList <String> VScode = new ArrayList<String>(
                Arrays.asList("vs code", "vs код", "vs", "vs кода", "код", "кода")
            );

            ArrayList <String> Stores = new ArrayList<String>(
                Arrays.asList("магазин", "стор", "store", "магазина", "стора", "стара")
            );
            
            /*
            * RU Проверка на соответсвие и отдача команды на выполнение задачи 
            * EU Checking for compliance and issuing a command to complete the task
            */

            if (Hello.contains(ClearText))
            {
                System.out.println("Привет, User!");
            }
            //--------------------------------------------------------------------------------------------------------------
            if (HowYou.contains(ClearText))
            {
                System.out.println("Хорошо! Надеюсь у вас еще лучше, User!");
            }
            //--------------------------------------------------------------------------------------------------------------
            if (Browser.contains(ClearText))
            {
                ActionHandler.CallFunction(FunctionsDictionary.get("Browser"));
            }
            //--------------------------------------------------------------------------------------------------------------
            if (Telegram.contains(ClearText))
            {
                ActionHandler.CallFunction(FunctionsDictionary.get("Telegram"));
            }
            //--------------------------------------------------------------------------------------------------------------
            if (VScode.contains(ClearText))
            {
                ActionHandler.CallFunction(FunctionsDictionary.get("VScode"));
            }
            //--------------------------------------------------------------------------------------------------------------
            if (Stores.contains(ClearText))
            {
                ActionHandler.CallFunction(FunctionsDictionary.get("Stores"));
            }
            //--------------------------------------------------------------------------------------------------------------
        }
    }
}
