import java.lang.reflect.InvocationTargetException;
import java.util.stream.Collectors;
import static java.util.Map.entry;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Dictionary 
{
    public static void main(String[] args) throws
    InvocationTargetException, 
    IllegalAccessException, 
    NoSuchMethodException, 
    InterruptedException,
    IOException
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
                entry("Store", "CallStore"),
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

            ArrayList <String> Store = new ArrayList<String>(
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

            ArrayList <String> Volume = new ArrayList<String>(
                Arrays.asList(
                    "увеличь громкость", "уменьши громкость", "увеличить громкость", 
                    "уменьшить громкость", "громкость больше", "громкость меньше", 
                    "выключи звук", "включи звук", "звук меньше", "звук больше",
                    "громкость на", "громкость мне", "громкость"
                )
            );

            ArrayList <String> WebSearch = new ArrayList<String>(
                Arrays.asList(
                    "найди", "найди в интернете", "ищи", "что такое", 
                    "когда", "в каком году", "где", "кто такой", "кто", 
                    "кто такая", "найти", "найти в интернете"
                )
            );

            ArrayList <String> YouTubeSearch = new ArrayList<String>(
                Arrays.asList(
                    "найди на ютубе", "ищи на ютубе", "найти на ютубе", 
                    "найди на ютуб", "ищи на ютуб", "найти на ютуб", 
                    "найди на ютюбе", "ищи на ютюбе", "найти на ютюбе", 
                    "найди на ютюб", "ищи на ютюб", "найти на ютюб", 
                    "найди в ютубе", "ищи в ютубе", "найти в ютубе", 
                    "найди в ютуб", "ищи в ютуб", "найти в ютуб", 
                    "найди в ютюбе", "ищи в ютюбе", "найти в ютюбе", 
                    "найди в ютюб", "ищи в ютюб", "найти в ютюб"
                )
            );


            /*
            * RU Проверка на соответсвие и отдача команды на выполнение задачи 
            * EU Checking for compliance and issuing a command to complete the task
            */
            
            //Озвучка фраз не требующей функционала
            if (Hello.contains(ClearText)){
                ActionHandler.CallFunction(FunctionsDictionary.get("Hello"));}
            //--------------------------------------------------------------------------------------------------------------

            //Вызов приложений
            if (Browser.contains(ClearText)){
                ActionHandler.CallFunction(FunctionsDictionary.get("Browser"));}
            //--------------------------------------------------------------------------------------------------------------
            
            if (Conductor.contains(ClearText)){
                ActionHandler.CallFunction(FunctionsDictionary.get("Conductor"));}
            //--------------------------------------------------------------------------------------------------------------

            if (Terminal.contains(ClearText)){
                ActionHandler.CallFunction(FunctionsDictionary.get("Terminal"));}
            //--------------------------------------------------------------------------------------------------------------

            if (Store.contains(ClearText)){
                ActionHandler.CallFunction(FunctionsDictionary.get("Store"));}
            //--------------------------------------------------------------------------------------------------------------

            if (Office.contains(ClearText)){
                ActionHandler.CallFunction(FunctionsDictionary.get("Office"));}
            //--------------------------------------------------------------------------------------------------------------

            if (Messenger.contains(ClearText)){
                ActionHandler.CallFunction(FunctionsDictionary.get("Messenger"));}
            //--------------------------------------------------------------------------------------------------------------

            if (SocialNetwork.contains(ClearText)){
                ActionHandler.CallFunction(FunctionsDictionary.get("SocialNetwork"));}
            //--------------------------------------------------------------------------------------------------------------

            if (Notes.contains(ClearText)){
                ActionHandler.CallFunction(FunctionsDictionary.get("Notes"));}
            //--------------------------------------------------------------------------------------------------------------
            
            if (CodeEditor.contains(ClearText)){
                ActionHandler.CallFunction(FunctionsDictionary.get("CodeEditor"));}
            //--------------------------------------------------------------------------------------------------------------
            
            //Работа с системой
            if (Reboot.contains(ClearText)){
                ActionHandler.CallFunction(FunctionsDictionary.get("Reboot"));}
            //--------------------------------------------------------------------------------------------------------------

            if (Shutdown.contains(ClearText)){
                ActionHandler.CallFunction(FunctionsDictionary.get("Shutdown"));}
            //--------------------------------------------------------------------------------------------------------------

            if (Sleep.contains(ClearText)){
                ActionHandler.CallFunction(FunctionsDictionary.get("Sleep"));}
            //--------------------------------------------------------------------------------------------------------------

            if (Volume.stream().anyMatch(ClearText::startsWith))
            {
                ArrayList <String> VolumeClear  = new ArrayList<String>(
                    Arrays.asList("громкость", "на", "мне", "процента", "процент", "процентов")
                );

                String ClearTextVolume = Arrays.stream(ClearText.split("\\s+"))
                .filter(word -> !VolumeClear
                .contains(word))
                .collect(Collectors.joining(" ")).toLowerCase();

                new ProcessBuilder("python3", "../WendyGrand/Voiceover.py", "StandardModule_StandardResponse").start();
                Thread.sleep(1500);

                if (ClearTextVolume.matches("^(увеличь|увеличить|уменьши|уменьшить)\\b.*")){
                    ActionHandler.CallVolume(ClearTextVolume + " громкость");} 
                else if (ClearTextVolume.matches("^(больше|меньше)\\b.*")){
                    ActionHandler.CallVolume("громкость " + ClearTextVolume);} 
                else{
                    ActionHandler.CallVolume(ClearTextVolume);}
            }
            //--------------------------------------------------------------------------------------------------------------

            //Поиск
            if (WebSearch.stream().anyMatch(ClearText::startsWith)) 
            {
                if (YouTubeSearch.stream().anyMatch(ClearText::startsWith))
                {
                    ArrayList <String> RemoveYouTubeSearch  = new ArrayList<String>(
                        Arrays.asList("найди", "найти", "на", "ищи", "ютубе", "ютюбе", "ютуб", "ютюб")
                    );

                    String ClearTextYouTubeSearch = Arrays.stream(ClearText.split("\\s+"))
                    .filter(word -> !RemoveYouTubeSearch.contains(word))
                    .collect(Collectors.joining("%20"));

                    ActionHandler.CallFunction(FunctionsDictionary.get("YouTubeSearch"), ClearTextYouTubeSearch);
                }
                else
                {
                    ArrayList <String> RemoveWebSearch  = new ArrayList<String>(
                        Arrays.asList("найди", "найти", "в", "интернете", "ищи")
                    );

                    String ClearTextWebSearch = Arrays.stream(ClearText.split("\\s+"))
                    .filter(word -> !RemoveWebSearch.contains(word))
                    .collect(Collectors.joining("%20"));

                    ActionHandler.CallFunction(FunctionsDictionary.get("WebSearch"), ClearTextWebSearch);
                }
            }
            //--------------------------------------------------------------------------------------------------------------
                        
            //Модульность 
            if (!ClearText.equals("")){
                ActionHandlerModules.TXTreader(ClearText);}
        }
    }
}
