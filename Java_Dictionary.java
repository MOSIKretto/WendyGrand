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
            * EU Unnecessary garbage in phrases and it's removal
            */

            ArrayList <String> Remove = new ArrayList<String>(
                Arrays.asList(
                    "пожалуйста", "ладно", "давай", "сейчас", "типо", "типа", "будь", "добра", "ну", 
                    "что-то",  "что", "то", "открой", "блять", "нахуй", "сука"
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
                entry("Hello", "CallHello"),
                entry("Browser", "CallBrowser"),
                entry("WebSearch", "CallWebSearch"),
                entry("YouTubeSearch", "CallYouTubeSearch"),
                entry("Telegram", "CallTelegram"),
                entry("VScode", "CallVScode"),
                entry("Stores", "CallStores"),
                entry("Obsidian", "CallObsidian"),
                entry("Reboot", "CallReboot"),
                entry("Shutdown", "CallShutdown")
            );
            
            ArrayList <String> Hello = new ArrayList<String>(
                Arrays.asList("привет", "здравствуй", "ты тут", "")
            );
                /*Сделать*/
            ArrayList <String> HowYou = new ArrayList<String>(
                Arrays.asList("как дела", "как ты")
            );
                    
            ArrayList <String> Browser = new ArrayList<String>(
                Arrays.asList("браузер", "браузера", "интернет")
            );
                
            ArrayList <String> Telegram = new ArrayList<String>(
                Arrays.asList("телеграм", "телега", "телегу", "телеграмма", "телеграма", "телеграмм")
            );

            ArrayList <String> Obsidian = new ArrayList<String>(
                Arrays.asList("обсидиан")
            );

            ArrayList <String> VScode = new ArrayList<String>(
                Arrays.asList("в скотт", "в скот", "вес скотт","вы скотт", "скотт", "код", "вес код")
            );
            
            ArrayList <String> Stores = new ArrayList<String>(
                Arrays.asList("магазин", "стор")
            );

            ArrayList <String> Reboot = new ArrayList<String>(
                Arrays.asList("ребут", "перезапуск")
            );

            ArrayList <String> Shutdown = new ArrayList<String>(
                Arrays.asList("выключения", "выключение")
            );
                
            /*
            * RU Проверка на соответсвие и отдача команды на выполнение задачи 
            * EU Checking for compliance and issuing a command to complete the task
            */
            
            //Озвучка фраз не требующей функционала
            if (Hello.contains(ClearText))
            {
                ActionHandler.CallFunction(FunctionsDictionary.get("Hello"));
            }
            //--------------------------------------------------------------------------------------------------------------
            if (HowYou.contains(ClearText))
            {
                System.out.println("Хорошо! Надеюсь у вас еще лучше, User!");
            }
            //--------------------------------------------------------------------------------------------------------------

            //Вызов приложений
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
            if (Obsidian.contains(ClearText))
            {
                ActionHandler.CallFunction(FunctionsDictionary.get("Obsidian"));
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

            //Работа с системой
            if (Reboot.contains(ClearText))
            {
                ActionHandler.CallFunction(FunctionsDictionary.get("Reboot"));
            }
            //--------------------------------------------------------------------------------------------------------------
            if (Shutdown.contains(ClearText))
            {
                ActionHandler.CallFunction(FunctionsDictionary.get("Shutdown"));
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
                        Arrays.asList("найди", "найти", "на", "ищи", "ютубе", "ютюбе", "ютуб")
                    );

                    String ClearTextYouTubeSearch = Arrays.stream(arg.split("\\s+"))
                        .filter(word -> !RemoveYouTubeSearch.contains(word))
                        .collect(Collectors.joining("%20"));

                    ActionHandler.CallFunction(FunctionsDictionary.get("YouTubeSearch"));
                    ActionHandler.CallYouTubeSearch("https://www.youtube.com/results?search_query=", ClearTextYouTubeSearch);
                }
                else
                {
                    ArrayList <String> RemoveWebSearch  = new ArrayList<String>(
                        Arrays.asList("найди", "найти", "в", "интернете", "ищи")
                    );

                    String ClearTextWebSearch = Arrays.stream(arg.split("\\s+"))
                        .filter(word -> !RemoveWebSearch.contains(word))
                        .collect(Collectors.joining("%20"));

                    ActionHandler.CallFunction(FunctionsDictionary.get("WebSearch"));
                    ActionHandler.CallWebSearch("https://duckduckgo.com/?q=", ClearTextWebSearch);
                }
            }
            //--------------------------------------------------------------------------------------------------------------
        }
    }
}
