package main.Java.Handlers;

import java.lang.reflect.InvocationTargetException;
import main.Resources.GeneralHelper;
import static java.util.Map.entry;
import java.io.IOException;
import java.util.*;


public class WordHandler 
{
    
    private static final Map<String, String> FUNCTIONS_DICTIONARY = Map.ofEntries(
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
        entry("Sleep", "CallSleep")
    );

    private static final List<String> REMOVE_WORDS = Arrays.asList(
        "пожалуйста", "ладно", "давай", "прямо", "сейчас", "типо", "типа", "будь", "добра", "ну",
        "что-то", "открой", "откройте", "хз", "блять", "нахуй", "сука",
        "венди", "среда", "вэнди"
    );

    private static final String PATH = "../WendyGrand/Configs/Dictionary.conf";

    public static void main(String[] args) throws 
    Exception 
    {
        for (String arg : args) 
        {
            String clearText = GeneralHelper.cleanInput(arg, REMOVE_WORDS);

            // Основные команды
            executeCommand(clearText, GeneralHelper.readConfig(PATH, "hello"), FUNCTIONS_DICTIONARY.get("Hello"));
            executeCommand(clearText, GeneralHelper.readConfig(PATH, "browser"), FUNCTIONS_DICTIONARY.get("Browser"));
            executeCommand(clearText, GeneralHelper.readConfig(PATH, "conductor"), FUNCTIONS_DICTIONARY.get("Conductor"));
            executeCommand(clearText, GeneralHelper.readConfig(PATH, "terminal"), FUNCTIONS_DICTIONARY.get("Terminal"));
            executeCommand(clearText, GeneralHelper.readConfig(PATH, "store"), FUNCTIONS_DICTIONARY.get("Store"));
            executeCommand(clearText, GeneralHelper.readConfig(PATH, "office"), FUNCTIONS_DICTIONARY.get("Office"));
            executeCommand(clearText, GeneralHelper.readConfig(PATH, "messenger"), FUNCTIONS_DICTIONARY.get("Messenger"));
            executeCommand(clearText, GeneralHelper.readConfig(PATH, "socialnetwork"), FUNCTIONS_DICTIONARY.get("SocialNetwork"));
            executeCommand(clearText, GeneralHelper.readConfig(PATH, "notes"), FUNCTIONS_DICTIONARY.get("Notes"));
            executeCommand(clearText, GeneralHelper.readConfig(PATH, "codeeditor"), FUNCTIONS_DICTIONARY.get("CodeEditor"));
            executeCommand(clearText, GeneralHelper.readConfig(PATH, "reboot"), FUNCTIONS_DICTIONARY.get("Reboot"));
            executeCommand(clearText, GeneralHelper.readConfig(PATH, "shutdown"), FUNCTIONS_DICTIONARY.get("Shutdown"));
            executeCommand(clearText, GeneralHelper.readConfig(PATH, "sleep"), FUNCTIONS_DICTIONARY.get("Sleep"));
            GeneralHelper.handleVolumeCommand(clearText, GeneralHelper.readConfig(PATH, "volume"));
            GeneralHelper.handleSearchCommand(clearText, GeneralHelper.readConfig(PATH, "websearch"), 
                                                         GeneralHelper.readConfig(PATH, "videosearch"));

            // Модульность
            List<String> modules = GeneralHelper.readConfig(PATH, "modules");

            if (!clearText.isEmpty() || !modules.isEmpty()) 
                ActionHandlerModules.handleModule(GeneralHelper.cleanInput(clearText, modules));
        }
    }

    private static void executeCommand(String input, List<String> commands, String functionName) throws 
    InvocationTargetException,
    IllegalAccessException, 
    NoSuchMethodException,
    InterruptedException,
    IOException 
    {
        if (commands != null && !commands.isEmpty() && commands.contains(input))
            ActionHandler.CallFunction(functionName);
    }
}