package src.main.logic.main;

import src.main.logic.main.activityHandlers.ActionHandler;
import src.main.logic.main.activityHandlers.ActionHandlerModules;
import src.main.logic.helpers.ConfigReader;
import src.main.logic.helpers.Scholar;
import src.main.logic.helpers.enums.ConstPaths;

import java.lang.reflect.InvocationTargetException;
import java.io.IOException;
import java.util.List;


public class DictionaryHandler
{

    private static final String CONFIG = ConstPaths.DICTIONARY_CONF.getConfPath();

    public static void main(String[] args) throws 
    Exception
    {
        for (String arg : args) 
        {
            String clearText = Scholar.cleanInput(arg, ConfigReader.readConfig(CONFIG, "delete"));

            // Основные команды
            executeCommand(clearText, ConfigReader.readConfig(CONFIG, "hello"), "CallApps", "hello");
            executeCommand(clearText, ConfigReader.readConfig(CONFIG, "browser"), "CallApps", "browser");
            executeCommand(clearText, ConfigReader.readConfig(CONFIG, "conductor"), "CallApps", "conductor");
            executeCommand(clearText, ConfigReader.readConfig(CONFIG, "terminal"), "CallApps", "terminal");
            executeCommand(clearText, ConfigReader.readConfig(CONFIG, "store"), "CallApps", "store");
            executeCommand(clearText, ConfigReader.readConfig(CONFIG, "office"), "CallApps", "office");
            executeCommand(clearText, ConfigReader.readConfig(CONFIG, "messenger"), "CallApps", "messenger");
            executeCommand(clearText, ConfigReader.readConfig(CONFIG, "socialnetwork"), "CallApps", "socialnetwork");
            executeCommand(clearText, ConfigReader.readConfig(CONFIG, "notes"), "CallApps", "notes");
            executeCommand(clearText, ConfigReader.readConfig(CONFIG, "codeeditor"), "CallApps", "codeeditor");
            executeCommand(clearText, ConfigReader.readConfig(CONFIG, "reboot"), "CallShutdown", "reboot");
            executeCommand(clearText, ConfigReader.readConfig(CONFIG, "shutdown"), "CallShutdown", "shutdown");
            executeCommand(clearText, ConfigReader.readConfig(CONFIG, "sleep"), "CallShutdown", "sleep");
            ActionHandler.CallVolume(clearText, ConfigReader.readConfig(CONFIG, "volume"));
            ActionHandler.CallSearch(clearText, ConfigReader.readConfig(CONFIG, "websearch"), 
                                                ConfigReader.readConfig(CONFIG, "videosearch"));

            if (!clearText.isEmpty()) 
                ActionHandlerModules.handleModule(clearText);
        }
    }

    private static void executeCommand(String input, List<String> commands, String functionName, String argForFunctions) throws
    InvocationTargetException,
    IllegalAccessException,
    NoSuchMethodException,
    InterruptedException,
    IOException
    {
        if (commands != null && !commands.isEmpty() && commands.contains(input))
            ActionHandler.CallFunction(functionName, argForFunctions);
    }
}