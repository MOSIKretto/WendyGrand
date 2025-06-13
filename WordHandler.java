import java.lang.reflect.InvocationTargetException;
import java.util.stream.Collectors;
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
            String clearText = cleanInput(arg, REMOVE_WORDS);

            // Команды
            executeCommand(clearText, ConfigReader.readConfig("../WendyGrand/Configs/Dictionary.conf", "hello"), FUNCTIONS_DICTIONARY.get("Hello"));
            executeCommand(clearText, ConfigReader.readConfig("../WendyGrand/Configs/Dictionary.conf", "browser"), FUNCTIONS_DICTIONARY.get("Browser"));
            executeCommand(clearText, ConfigReader.readConfig("../WendyGrand/Configs/Dictionary.conf", "conductor"), FUNCTIONS_DICTIONARY.get("Conductor"));
            executeCommand(clearText, ConfigReader.readConfig("../WendyGrand/Configs/Dictionary.conf", "terminal"), FUNCTIONS_DICTIONARY.get("Terminal"));
            executeCommand(clearText, ConfigReader.readConfig("../WendyGrand/Configs/Dictionary.conf", "store"), FUNCTIONS_DICTIONARY.get("Store"));
            executeCommand(clearText, ConfigReader.readConfig("../WendyGrand/Configs/Dictionary.conf", "office"), FUNCTIONS_DICTIONARY.get("Office"));
            executeCommand(clearText, ConfigReader.readConfig("../WendyGrand/Configs/Dictionary.conf", "messenger"), FUNCTIONS_DICTIONARY.get("Messenger"));
            executeCommand(clearText, ConfigReader.readConfig("../WendyGrand/Configs/Dictionary.conf", "socialnetwork"), FUNCTIONS_DICTIONARY.get("SocialNetwork"));
            executeCommand(clearText, ConfigReader.readConfig("../WendyGrand/Configs/Dictionary.conf", "notes"), FUNCTIONS_DICTIONARY.get("Notes"));
            executeCommand(clearText, ConfigReader.readConfig("../WendyGrand/Configs/Dictionary.conf", "codeeditor"), FUNCTIONS_DICTIONARY.get("CodeEditor"));
            executeCommand(clearText, ConfigReader.readConfig("../WendyGrand/Configs/Dictionary.conf", "reboot"), FUNCTIONS_DICTIONARY.get("Reboot"));
            executeCommand(clearText, ConfigReader.readConfig("../WendyGrand/Configs/Dictionary.conf", "shutdown"), FUNCTIONS_DICTIONARY.get("Shutdown"));
            executeCommand(clearText, ConfigReader.readConfig("../WendyGrand/Configs/Dictionary.conf", "sleep"), FUNCTIONS_DICTIONARY.get("Sleep"));

            // Исключения
            handleVolumeCommand(clearText, ConfigReader.readConfig("../WendyGrand/Configs/Dictionary.conf", "volume"));
            handleSearchCommand(clearText, ConfigReader.readConfig("../WendyGrand/Configs/Dictionary.conf", "websearch"), 
                                           ConfigReader.readConfig("../WendyGrand/Configs/Dictionary.conf", "videosearch"));

            // Модульность
            List<String> modules = ConfigReader.readConfig("../WendyGrand/Configs/Dictionary.conf", "modules");
            if (!clearText.isEmpty() || !modules.isEmpty()) 
            {
                String clearModules = cleanInput(clearText, modules);
                ActionHandlerModules.txtReader(clearModules);
            }
        }
    }

    private static String cleanInput(String input, List<String> wordsToRemove) 
    {
        return Arrays.stream(input.split("\\s+"))
            .filter(word -> !wordsToRemove.contains(word))
            .collect(Collectors.joining(" "));
    }

    private static void handleVolumeCommand(String input, List<String> volumeCommands) throws IOException, InterruptedException 
    {
        if (volumeCommands != null && !volumeCommands.isEmpty() && volumeCommands.stream().anyMatch(input::startsWith)) 
        {
            String clearTextVolume = cleanInput(input, List.of("громкость", "на", "мне", "меня", "процента", "процент", "процентов"));
            
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
                String clearTextYouTubeSearch = cleanInput(input, removeYouTubeSearchWords).replace(" ", "%20");
                ActionHandler.CallFunction(FUNCTIONS_DICTIONARY.get("YouTubeSearch"), clearTextYouTubeSearch);
            } 
            else 
            {
                List<String> removeWebSearchWords = List.of("найди", "найти", "в", "интернете", "ищи");
                String clearTextWebSearch = cleanInput(input, removeWebSearchWords).replace(" ", "%20");
                ActionHandler.CallFunction(FUNCTIONS_DICTIONARY.get("WebSearch"), clearTextWebSearch);
            }
        }
    }

    private static void executeCommand(String input, List<String> commands, String functionName) throws 
    InvocationTargetException, 
    IllegalArgumentException, 
    IllegalAccessException, 
    NoSuchMethodException, 
    SecurityException, 
    InterruptedException, 
    IOException 
    {
        if (commands != null && !commands.isEmpty() && commands.contains(input)) 
            ActionHandler.CallFunction(functionName);
    }
}