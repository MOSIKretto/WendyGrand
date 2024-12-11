import java.lang.reflect.InvocationTargetException;
import java.util.stream.Collectors;
import static java.util.Map.entry;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Java_Dictionary 
{
    public static void main(String[] args) throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InterruptedException
    {
        for (String arg : args) 
        {
            //слова для выреза
            ArrayList <String> Remove = new ArrayList<String>(
                Arrays.asList(
                    "пожалуйста", "ладно", "давай", "прямо", "сейчас", "типо", "типа", "будь", "добра", "ну", 
                    "что-то", "открой", "откройте", "блять", "нахуй", "сука",
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

            //передача в ActionHandler
            Map <String, String> FunctionsDictionary = Map.ofEntries(

                entry("Hello", "CallHello"), 
                
                entry("Browser", "CallBrowser"), 
                entry("Conductor", "CallConductor"), 
                entry("Terminal", "CallTerminal"),
                entry("Stores", "CallStores"),
                entry("Office", "CallOffice"),

                entry("Messenger", "CallMessenger"),
                entry("SocialNetwork", "CallSocialNetwork"),

                entry("Notes", "CallNotes"),
                entry("CodeEditor", "CallCodeEditor"),

                entry("Reboot", "CallReboot"),
                entry("Shutdown", "CallShutdown"),
                entry("Sleep", "CallSleep"),
                entry("Volume", "CallVolume"),

                entry("WebSearch", "CallWebSearch"),
                entry("YouTubeSearch", "CallYouTubeSearch")
            );
            


            ArrayList <String> Hello = new ArrayList<String>(
                Arrays.asList("здравствуй", "ты тут", "привет")
            );
            

            ArrayList <String> Browser = new ArrayList<String>(
                Arrays.asList("браузер", "браузера", "интернет")
            );
             
            ArrayList <String> Conductor = new ArrayList<String>(
                Arrays.asList("проводник")
            );

            ArrayList <String> Terminal = new ArrayList<String>(
                Arrays.asList("терминал")
            );

            ArrayList <String> Stores = new ArrayList<String>(
                Arrays.asList("магазин", "стор")
            );

            ArrayList <String> Office = new ArrayList<String>(
                Arrays.asList("офис")
            );


            ArrayList <String> Messenger = new ArrayList<String>(
                Arrays.asList("мессенджер", "телега", "телеграмм")
            );

            ArrayList <String> SocialNetwork = new ArrayList<String>(
                Arrays.asList("соц сеть", "социальная сеть")
            );


            ArrayList <String> Notes = new ArrayList<String>(
                Arrays.asList("обсидиан", "заметки")
            );

            ArrayList <String> CodeEditor = new ArrayList<String>(
                Arrays.asList("редактор кода", "код")
            );
            

            ArrayList <String> Reboot = new ArrayList<String>(
                Arrays.asList("перезапуск", "перезапусти компьютер")
            );

            ArrayList <String> Shutdown = new ArrayList<String>(
                Arrays.asList(
                    "выключения", "выключение", "выключени",
                    "выключения компьютера", "выключение компьютера", 
                    "выключи компьютер", "выключить компьютер", 
                    "занавес"
                )
            );

            ArrayList <String> Sleep = new ArrayList<String>(
                Arrays.asList("спящий режим", "спать", "антракт")
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

            //Вызов приложений
            if (Browser.contains(ClearText))
            {
                ActionHandler.CallFunction(FunctionsDictionary.get("Browser"));
            }
            //--------------------------------------------------------------------------------------------------------------
            if (Conductor.contains(ClearText))
            {
                ActionHandler.CallFunction(FunctionsDictionary.get("Conductor"));
            }
            //--------------------------------------------------------------------------------------------------------------
            if (Terminal.contains(ClearText))
            {
                ActionHandler.CallFunction(FunctionsDictionary.get("Terminal"));
            }
            //--------------------------------------------------------------------------------------------------------------
            if (Stores.contains(ClearText))
            {
                ActionHandler.CallFunction(FunctionsDictionary.get("Stores"));
            }
            //--------------------------------------------------------------------------------------------------------------
            if (Office.contains(ClearText))
            {
                ActionHandler.CallFunction(FunctionsDictionary.get("Office"));
            }
            //--------------------------------------------------------------------------------------------------------------
            if (Messenger.contains(ClearText))
            {
                ActionHandler.CallFunction(FunctionsDictionary.get("Messenger"));
            }
            //--------------------------------------------------------------------------------------------------------------
            if (SocialNetwork.contains(ClearText))
            {
                ActionHandler.CallFunction(FunctionsDictionary.get("SocialNetwork"));
            }
            //--------------------------------------------------------------------------------------------------------------
            if (Notes.contains(ClearText))
            {
                ActionHandler.CallFunction(FunctionsDictionary.get("Notes"));
            }
            //--------------------------------------------------------------------------------------------------------------
            if (CodeEditor.contains(ClearText))
            {
                ActionHandler.CallFunction(FunctionsDictionary.get("CodeEditor"));
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
            if (Sleep.contains(ClearText))
            {
                ActionHandler.CallFunction(FunctionsDictionary.get("Sleep"));
            }
            //--------------------------------------------------------------------------------------------------------------
            if (ClearText.startsWith("увеличь громкость") || ClearText.startsWith("уменьши громкость") ||
            ClearText.startsWith("увеличить громкость") || ClearText.startsWith("уменьшить громкость") ||
            ClearText.startsWith("громкость больше") || ClearText.startsWith("громкость меньше") ||
            ClearText.startsWith("выключи звук") || ClearText.startsWith("включи звук") ||
            ClearText.startsWith("звук меньше") || ClearText.startsWith("звук больше") ||
            ClearText.startsWith("громкость на") || ClearText.startsWith("громкость мне") || ClearText.startsWith("громкость"))
            {
                ArrayList <String> VolumeClear  = new ArrayList<String>(
                    Arrays.asList("громкость", "на", "мне", "процента", "процент", "процентов")
                );

                String ClearTextVolume = Arrays.stream(ClearText.split("\\s+"))
                    .filter(word -> !VolumeClear.contains(word))
                    .collect(Collectors.joining(" "));

                ProcessBuilder builder = new ProcessBuilder("python3", "../WendyGrand/Voiceover.py", "StandardModule_StandardResponse");
                builder.start();

                Thread.sleep(1500);

                if (ClearTextVolume.startsWith("увеличь") || ClearTextVolume.startsWith("уменьши") ||
                ClearTextVolume.startsWith("увеличить") || ClearTextVolume.startsWith("уменьшить"))
                {
                    ActionHandler.CallVolume(ClearTextVolume + " громкость");
                }
                else if (ClearTextVolume.startsWith("больше") || ClearTextVolume.startsWith("меньше"))
                {
                    ActionHandler.CallVolume("громкость " + ClearTextVolume);
                }
                else
                {
                    ActionHandler.CallVolume(ClearTextVolume);
                }
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

                ActionHandler.CallFunction(FunctionsDictionary.get("YouTubeSearch"), "https://www.youtube.com/results?search_query=", ClearTextYouTubeSearch);
            }
            else
            {
                ArrayList <String> RemoveWebSearch  = new ArrayList<String>(
                    Arrays.asList("найди", "найти", "в", "интернете", "ищи")
                );

                String ClearTextWebSearch = Arrays.stream(ClearText.split("\\s+"))
                    .filter(word -> !RemoveWebSearch.contains(word))
                    .collect(Collectors.joining("%20"));

                ActionHandler.CallFunction(FunctionsDictionary.get("WebSearch"), "https://duckduckgo.com/?q=", ClearTextWebSearch);
            }
            }
            //--------------------------------------------------------------------------------------------------------------
                        
            //Модульность 
            if (!ClearText.equals("")){ActionHandlerModules.TXTreader(ClearText);}
        }
    }
}
