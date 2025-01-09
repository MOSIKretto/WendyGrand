import java.lang.reflect.InvocationTargetException;
import java.util.stream.Collectors;
import static java.util.Map.entry;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
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

        // Полуизменяемые
        entry("Volume", "CallVolume"),
        entry("WebSearch", "CallWebSearch"),
        entry("YouTubeSearch", "CallYouTubeSearch")
    );

    private static final List<String> REMOVE_WORDS = Arrays.asList(
        "пожалуйста", "ладно", "давай", "прямо", "сейчас", "типо", "типа", "будь", "добра", "ну",
        "что-то", "открой", "откройте", "блять", "нахуй", "сука",
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
        Map<String, List<String>> configMap = readConfig("../WendyGrand/Configs/Dictionary.conf");

        for (String arg : args) 
        {
            String clearText = cleanInput(arg, REMOVE_WORDS);

            // Изменяемые команды
            executeCommand(clearText, configMap.get("hello"),  FUNCTIONS_DICTIONARY.get("Hello"));
            executeCommand(clearText, configMap.get("browser"), FUNCTIONS_DICTIONARY.get("Browser"));
            executeCommand(clearText, configMap.get("conductor"), FUNCTIONS_DICTIONARY.get("Conductor"));
            executeCommand(clearText, configMap.get("terminal"), FUNCTIONS_DICTIONARY.get("Terminal"));
            executeCommand(clearText, configMap.get("store"), FUNCTIONS_DICTIONARY.get("Store"));
            executeCommand(clearText, configMap.get("office"), FUNCTIONS_DICTIONARY.get("Office"));
            executeCommand(clearText, configMap.get("messenger"), FUNCTIONS_DICTIONARY.get("Messenger"));
            executeCommand(clearText, configMap.get("socialnetwork"), FUNCTIONS_DICTIONARY.get("SocialNetwork"));
            executeCommand(clearText, configMap.get("notes"), FUNCTIONS_DICTIONARY.get("Notes"));
            executeCommand(clearText, configMap.get("codeeditor"), FUNCTIONS_DICTIONARY.get("CodeEditor"));
            executeCommand(clearText, configMap.get("reboot"), FUNCTIONS_DICTIONARY.get("Reboot"));
            executeCommand(clearText, configMap.get("shutdown"), FUNCTIONS_DICTIONARY.get("Shutdown"));
            executeCommand(clearText, configMap.get("sleep"), FUNCTIONS_DICTIONARY.get("Sleep"));
            
            // Полуизменяемые команды
            handleVolumeCommand(clearText, 
            configMap.getOrDefault("volume", List.of("увеличь громкость", "уменьши громкость", 
                                                    "увеличить громкость", "уменьшить громкость",
                                                    "громкость больше", "громкость меньше", 
                                                    "звук больше", "звук меньше", 
                                                    "включи звук", "выключи звук",
                                                    "громкость на", "громкость мне", "громкость")));

            handleSearchCommand(clearText, 
            configMap.getOrDefault("websearch", List.of("найди", "найди в интернете",
                                                        "найти", "найти в интернете", 
                                                        "ищи", "что такое", "когда", 
                                                        "в каком году", "где", "кто такой", 
                                                        "кто", "кто такая")), 

            configMap.getOrDefault("youtubesearch", List.of(
                "найди на ютубе", "ищи на ютубе", "найти на ютубе", 
                "найди на ютуб", "ищи на ютуб", "найти на ютуб",
                "найди на ютюбе", "ищи на ютюбе", "найти на ютюбе", 
                "найди на ютюб", "ищи на ютюб", "найти на ютюб",
                "найди в ютубе", "ищи в ютубе", "найти в ютубе", 
                "найди в ютуб", "ищи в ютуб", "найти в ютуб",
                "найди в ютюбе", "ищи в ютюбе", "найти в ютюбе", 
                "найди в ютюб", "ищи в ютюб", "найти в ютюб"
            )));

            // Модульность
            if (!clearText.isEmpty() || Optional.ofNullable(configMap.get("modules")).isPresent()) 
            {
                String clearModules = cleanInput(clearText, configMap.get("modules"));
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

    private static Map<String, List<String>> readConfig(String filePath) throws IOException 
    {
        Map<String, List<String>> configMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) 
        {
            String line;

            while ((line = reader.readLine()) != null) 
            {
                line = line.trim();

                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] parts = line.split("=");
                if (parts.length == 2) 
                {
                    String key = parts[0].trim();
                    List<String> values = Arrays.asList(parts[1].trim().split(",\\s*"));

                    configMap.put(key, values);
                }
            }
        }
        return configMap;
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
        if (commands != null && commands.contains(input)) ActionHandler.CallFunction(functionName);
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