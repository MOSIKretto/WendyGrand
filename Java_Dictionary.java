/* *Java_Dictionary*
 *
 * RU Ищет соответствие по словарям и отдает команду на выполнение 
 * ---------------------------------------------------------------------
 * EN Looks for a match in dictionaries and issues a command to execute
 * 
*/

import java.util.stream.Collectors;
import static java.util.Map.entry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Java_Dictionary 
{
    public static void main(String[] args)
    {
        for (String arg : args) 
        {
            /*
            * RU Ненужный мусор во фразах и его удаление 
            * EU Unnecessary garbage in phrases and it's removal
            */

            ArrayList <String> Remove = new ArrayList<String>(
                Arrays.asList(
                    "пожалуйста", "ладно", "давай", "прямо", "сейчас", "типо", "типа", "будь", "добра", "ну", 
                    "что-то",  "что", "то", "открой", "да", "блять", "нахуй", "сука", 
                    //Имя
                    "венди", "среда", "вэнди"
                )
            );
            
            String ClearText = Arrays.stream(arg.split("\\s+"))
                .filter(word -> !Remove.contains(word))
                .collect(Collectors.joining(" "));
    
            /*
            * RU Словари с командами 
            * EU Dictionaries with commands
            */

            Map <String, String> FunctionsDictionary = Map.ofEntries(
                entry("Hello", "CallHello"),
                entry("Browser", "CallBrowser"),
                entry("WebSearch", "CallWebSearch"),
                entry("YouTubeSearch", "CallYouTubeSearch"),
                entry("Telegram", "CallTelegram"),
                entry("VScode", "CallVScode"),
                entry("Stores", "CallStores"),
                entry("Obsidian", "CallObsidian"),
                entry("Reboot", "CallReboot"),
                entry("Shutdown", "CallShutdown"),
                entry("Upgrade", "CallUpgrade")
            );
            
            ArrayList <String> Hello = new ArrayList<String>(
                Arrays.asList("привет", "здравствуй", "ты тут")
            );
                    
            ArrayList <String> Browser = new ArrayList<String>(
                Arrays.asList("браузер", "браузера", "интернет")
            );
                
            ArrayList <String> Telegram = new ArrayList<String>(
                Arrays.asList("телеграм", "телеграмм", "телега", "телегу", "телеграма", "телеграмма", "телеграммы", "телеграмму")
            );

            ArrayList <String> Obsidian = new ArrayList<String>(
                Arrays.asList("обсидиан")
            );

            ArrayList <String> VScode = new ArrayList<String>(
                Arrays.asList(
                    "в скотт", "в скот", 
                    "вес скотт", "вес код", 
                    "вы скотт", "вы скот", "вы и скотт",
                    "скотт", "код"
                )
            );
            
            ArrayList <String> Stores = new ArrayList<String>(
                Arrays.asList("магазин", "стор")
            );

            ArrayList <String> Reboot = new ArrayList<String>(
                Arrays.asList("перезапуск", "перезапусти компьютер")
            );

            ArrayList <String> Shutdown = new ArrayList<String>(
                Arrays.asList(
                    "выключения", "выключение", "выключени",
                    "выключения компьютера", "выключкение компьютера", 
                    "выключи компьютер", "выключить компьютер"
                )
            );

            ArrayList <String> Upgrade = new ArrayList<String>(
                Arrays.asList(
                    "обнови систему", "обнови системы", 
                    "обновить систему", "обновить системы"
                    )
            );
                
            /*
            * RU Проверка на соответсвие и отдача команды на выполнение задачи 
            * EU Checking for compliance and issuing a command to complete the task
            */
            
            //Озвучка фраз не требующей функционала
            if (Hello.contains(ClearText))
            {
                youSay(arg);
                ActionHandler.CallFunction(FunctionsDictionary.get("Hello"));
            }
            //--------------------------------------------------------------------------------------------------------------

            //Вызов приложений
            if (Browser.contains(ClearText))
            {
                youSay(arg);
                ActionHandler.CallFunction(FunctionsDictionary.get("Browser"));
            }
            //--------------------------------------------------------------------------------------------------------------
            if (Telegram.contains(ClearText))
            {
                youSay(arg);
                ActionHandler.CallFunction(FunctionsDictionary.get("Telegram"));
            }
            //--------------------------------------------------------------------------------------------------------------
            if (Obsidian.contains(ClearText))
            {
                youSay(arg);
                ActionHandler.CallFunction(FunctionsDictionary.get("Obsidian"));
            }
            //--------------------------------------------------------------------------------------------------------------
            if (VScode.contains(ClearText))
            {
                youSay(arg);
                ActionHandler.CallFunction(FunctionsDictionary.get("VScode"));
            }
            //--------------------------------------------------------------------------------------------------------------
            if (Stores.contains(ClearText))
            {
                youSay(arg);
                ActionHandler.CallFunction(FunctionsDictionary.get("Stores"));
            }
            //--------------------------------------------------------------------------------------------------------------

            //Работа с системой
            if (Reboot.contains(ClearText))
            {
                youSay(arg);
                ActionHandler.CallFunction(FunctionsDictionary.get("Reboot"));
            }
            //--------------------------------------------------------------------------------------------------------------
            if (Shutdown.contains(ClearText))
            {
                youSay(arg);
                ActionHandler.CallFunction(FunctionsDictionary.get("Shutdown"));
            }
            //--------------------------------------------------------------------------------------------------------------
            if (Upgrade.contains(ClearText))
            {
                youSay(arg);
                ActionHandler.CallFunction(FunctionsDictionary.get("Upgrade"));
            }
            //--------------------------------------------------------------------------------------------------------------

            //Поиск
            if (ClearText.startsWith("найди") || ClearText.startsWith("найди в интернете") || ClearText.startsWith("ищи") || 
            ClearText.startsWith("что такое") || ClearText.startsWith("когда") || ClearText.startsWith("в каком году") || 
            ClearText.startsWith("где") || ClearText.startsWith("кто такой") || ClearText.startsWith("кто") || 
            ClearText.startsWith("кто такая") || ClearText.startsWith("найти") || ClearText.startsWith("найти в интернете")) 
            {
                if (ClearText.contains("найди на ютубе") || ClearText.contains("ищи на ютубе") || ClearText.contains("найти на ютубе") || 
                ClearText.contains("найди на ютуб") || ClearText.contains("ищи на ютуб") || ClearText.contains("найти на ютуб")||
                ClearText.contains("найди на ютюбе") || ClearText.contains("ищи на ютюбе") || ClearText.contains("найти на ютюбе") ||
                ClearText.contains("найди на ютюб") || ClearText.contains("ищи на ютюб") || ClearText.contains("найти на ютюб"))
                {
                    ArrayList <String> RemoveYouTubeSearch  = new ArrayList<String>(
                        Arrays.asList("найди", "найти", "на", "ищи", "ютубе", "ютюбе", "ютуб", "ютюб")
                    );

                    String ClearTextYouTubeSearch = Arrays.stream(ClearText.split("\\s+"))
                        .filter(word -> !RemoveYouTubeSearch.contains(word))
                        .collect(Collectors.joining("%20"));

                    ActionHandler.CallFunction(FunctionsDictionary.get("YouTubeSearch"));
                    youSay(arg);
                    ActionHandler.CallYouTubeSearch("https://www.youtube.com/results?search_query=", ClearTextYouTubeSearch);
                }
                else
                {
                    ArrayList <String> RemoveWebSearch  = new ArrayList<String>(
                        Arrays.asList("найди", "найти", "в", "интернете", "ищи")
                    );

                    String ClearTextWebSearch = Arrays.stream(ClearText.split("\\s+"))
                        .filter(word -> !RemoveWebSearch.contains(word))
                        .collect(Collectors.joining("%20"));

                    ActionHandler.CallFunction(FunctionsDictionary.get("WebSearch"));
                    youSay(arg);
                    ActionHandler.CallWebSearch("https://duckduckgo.com/?q=", ClearTextWebSearch);
                }
            }
            //--------------------------------------------------------------------------------------------------------------
        }
    }

    private static void youSay(String message)
    {
        System.out.println("Распознано: " + message);
    }
}
