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
            executeCommand(clearText, getConfigValues("hello"), FUNCTIONS_DICTIONARY.get("Hello"));
            executeCommand(clearText, getConfigValues("browser"), FUNCTIONS_DICTIONARY.get("Browser"));
            executeCommand(clearText, getConfigValues("conductor"), FUNCTIONS_DICTIONARY.get("Conductor"));
            executeCommand(clearText, getConfigValues("terminal"), FUNCTIONS_DICTIONARY.get("Terminal"));
            executeCommand(clearText, getConfigValues("store"), FUNCTIONS_DICTIONARY.get("Store"));
            executeCommand(clearText, getConfigValues("office"), FUNCTIONS_DICTIONARY.get("Office"));
            executeCommand(clearText, getConfigValues("messenger"), FUNCTIONS_DICTIONARY.get("Messenger"));
            executeCommand(clearText, getConfigValues("socialnetwork"), FUNCTIONS_DICTIONARY.get("SocialNetwork"));
            executeCommand(clearText, getConfigValues("notes"), FUNCTIONS_DICTIONARY.get("Notes"));
            executeCommand(clearText, getConfigValues("codeeditor"), FUNCTIONS_DICTIONARY.get("CodeEditor"));
            executeCommand(clearText, getConfigValues("reboot"), FUNCTIONS_DICTIONARY.get("Reboot"));
            executeCommand(clearText, getConfigValues("shutdown"), FUNCTIONS_DICTIONARY.get("Shutdown"));
            executeCommand(clearText, getConfigValues("sleep"), FUNCTIONS_DICTIONARY.get("Sleep"));
            handleVolumeCommand(clearText, getConfigValues("volume"));
            handleSearchCommand(clearText, getConfigValues("websearch"), getConfigValues("youtubesearch"));

            // Модульность
            if (!clearText.isEmpty() || ConfigReader.readConfig("../WendyGrand/Configs/Dictionary.conf", "modules") != null) 
            {
                String clearModules = cleanInput(clearText, getConfigValues("modules"));
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

    private static List<String> getConfigValues(String key) throws IOException 
    {
        String[] values = ConfigReader.readConfig("../WendyGrand/Configs/Dictionary.conf", key);
        if (values == null) return null;
        
        List<String> result = new ArrayList<>();
        
        for (String value : values) 
        {
            String[] parts = value.split(",\\s*");
            Collections.addAll(result, parts);
        }
        return result;
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
        if (commands != null && commands.contains(input)) 
        ActionHandler.CallFunction(functionName);
    }

    private static void handleVolumeCommand(String input, List<String> volumeCommands) throws IOException, InterruptedException 
    {
        if (volumeCommands.stream().anyMatch(input::startsWith)) 
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
        if (webSearchCommands.stream().anyMatch(input::startsWith)) 
        {
            if (youtubeSearchCommands.stream().anyMatch(input::startsWith)) 
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
}