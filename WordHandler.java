import java.lang.reflect.InvocationTargetException;
import static java.util.Map.entry;
import java.io.IOException;
import java.util.*;


public class WordHandler 
{
    
    private static final Map<String, String> FUNCTIONS_DICTIONARY = Map.ofEntries(
        // Изменяемые
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

    private static final List<String> REMOVE_WORDS = Arrays.asList(
        "пожалуйста", "ладно", "давай", "прямо", "сейчас", "типо", "типа", "будь", "добра", "ну",
        "что-то", "открой", "откройте", "хз", "блять", "нахуй", "сука",
        // Имя
        "венди", "среда", "вэнди"
    );

    public static void main(String[] args) throws 
    InvocationTargetException, 
    IllegalAccessException,
    NoSuchMethodException, 
    InterruptedException, 
    IOException 
    {
        for (String arg : args) 
        {
            String clearText = GeneralHelper.cleanInput(arg, REMOVE_WORDS);

            // Команды
            executeCommand(clearText, GeneralHelper.readConfig("../WendyGrand/Configs/Dictionary.conf", "hello"), FUNCTIONS_DICTIONARY.get("Hello"));
            executeCommand(clearText, GeneralHelper.readConfig("../WendyGrand/Configs/Dictionary.conf", "browser"), FUNCTIONS_DICTIONARY.get("Browser"));
            executeCommand(clearText, GeneralHelper.readConfig("../WendyGrand/Configs/Dictionary.conf", "conductor"), FUNCTIONS_DICTIONARY.get("Conductor"));
            executeCommand(clearText, GeneralHelper.readConfig("../WendyGrand/Configs/Dictionary.conf", "terminal"), FUNCTIONS_DICTIONARY.get("Terminal"));
            executeCommand(clearText, GeneralHelper.readConfig("../WendyGrand/Configs/Dictionary.conf", "store"), FUNCTIONS_DICTIONARY.get("Store"));
            executeCommand(clearText, GeneralHelper.readConfig("../WendyGrand/Configs/Dictionary.conf", "office"), FUNCTIONS_DICTIONARY.get("Office"));
            executeCommand(clearText, GeneralHelper.readConfig("../WendyGrand/Configs/Dictionary.conf", "messenger"), FUNCTIONS_DICTIONARY.get("Messenger"));
            executeCommand(clearText, GeneralHelper.readConfig("../WendyGrand/Configs/Dictionary.conf", "socialnetwork"), FUNCTIONS_DICTIONARY.get("SocialNetwork"));
            executeCommand(clearText, GeneralHelper.readConfig("../WendyGrand/Configs/Dictionary.conf", "notes"), FUNCTIONS_DICTIONARY.get("Notes"));
            executeCommand(clearText, GeneralHelper.readConfig("../WendyGrand/Configs/Dictionary.conf", "codeeditor"), FUNCTIONS_DICTIONARY.get("CodeEditor"));
            executeCommand(clearText, GeneralHelper.readConfig("../WendyGrand/Configs/Dictionary.conf", "reboot"), FUNCTIONS_DICTIONARY.get("Reboot"));
            executeCommand(clearText, GeneralHelper.readConfig("../WendyGrand/Configs/Dictionary.conf", "shutdown"), FUNCTIONS_DICTIONARY.get("Shutdown"));
            executeCommand(clearText, GeneralHelper.readConfig("../WendyGrand/Configs/Dictionary.conf", "sleep"), FUNCTIONS_DICTIONARY.get("Sleep"));

            // Исключения
            handleVolumeCommand(clearText, GeneralHelper.readConfig("../WendyGrand/Configs/Dictionary.conf", "volume"));
            handleSearchCommand(clearText, GeneralHelper.readConfig("../WendyGrand/Configs/Dictionary.conf", "websearch"), 
                                           GeneralHelper.readConfig("../WendyGrand/Configs/Dictionary.conf", "videosearch"));

            // Модульность
            List<String> modules = GeneralHelper.readConfig("../WendyGrand/Configs/Dictionary.conf", "modules");

            if (!clearText.isEmpty() || !modules.isEmpty()) 
                ActionHandlerModules.handleModule(GeneralHelper.cleanInput(clearText, modules));
        }
    }

    private static void handleVolumeCommand(String input, List<String> volumeCommands) throws 
    InterruptedException, 
    IOException
    {
        if (volumeCommands != null && !volumeCommands.isEmpty() && volumeCommands.stream().anyMatch(input::startsWith)) 
        {
            String clearTextVolume = GeneralHelper.cleanInput(input, List.of("громкость", "на", "мне", "меня", "процента", "процент", "процентов"));
            
            new ProcessBuilder("python3", "../WendyGrand/Voiceover.py", "StandardModule_StandardResponse").start();
            Thread.sleep(1500);
            
            if (clearTextVolume.matches("^(увеличь|увеличить|уменьши|уменьшить)\\b.*")) 
                ActionHandler.CallVolume(clearTextVolume + " громкость");
            
            else if (clearTextVolume.matches("^(больше|меньше)\\b.*"))
                ActionHandler.CallVolume("громкость " + clearTextVolume);
            
            else
                ActionHandler.CallVolume(clearTextVolume);
        }
    }
    
    private static void handleSearchCommand(String input, List<String> webSearchCommands, List<String> youtubeSearchCommands) throws 
    InvocationTargetException, 
    IllegalArgumentException, 
    IllegalAccessException, 
    NoSuchMethodException,
    InterruptedException,
    SecurityException, 
    IOException 
    {
        if (webSearchCommands != null && !webSearchCommands.isEmpty() && webSearchCommands.stream().anyMatch(input::startsWith)) 
        {
            if (youtubeSearchCommands != null && !youtubeSearchCommands.isEmpty() && youtubeSearchCommands.stream().anyMatch(input::startsWith)) 
            {
                List<String> removeYouTubeSearchWords = List.of("найди", "найти", "на", "ищи", "ютубе", "ютюбе", "ютуб", "ютюб");
                String clearTextYouTubeSearch = GeneralHelper.cleanInput(input, removeYouTubeSearchWords).replace(" ", "%20");
                ActionHandler.CallFunction(FUNCTIONS_DICTIONARY.get("YouTubeSearch"), clearTextYouTubeSearch);
            } 
            else 
            {
                List<String> removeWebSearchWords = List.of("найди", "найти", "в", "интернете", "ищи");
                String clearTextWebSearch = GeneralHelper.cleanInput(input, removeWebSearchWords).replace(" ", "%20");
                ActionHandler.CallFunction(FUNCTIONS_DICTIONARY.get("WebSearch"), clearTextWebSearch);
            }
        }
    }

    private static void executeCommand(String input, List<String> commands, String functionName) throws 
    InvocationTargetException, 
    IllegalArgumentException, 
    IllegalAccessException, 
    NoSuchMethodException, 
    InterruptedException, 
    SecurityException, 
    IOException 
    {
        if (commands != null && !commands.isEmpty() && commands.contains(input)) 
            ActionHandler.CallFunction(functionName);
    }
}