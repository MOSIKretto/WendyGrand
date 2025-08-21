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
    private static final String DICTIONARY_CONF = ConstPaths.DICTIONARY_CONF.getConfPath();

    public static void main(String[] args) throws
    Exception
    {
        for (String arg : args) 
        {
            String clearText = Scholar.cleanInput(arg, ConfigReader.readConfig(DICTIONARY_CONF, "delete"));

            // Основные команды
            executeCommand(clearText, ConfigReader.readConfig(DICTIONARY_CONF, "hello"), "CallApps", "hello");
            executeCommand(clearText, ConfigReader.readConfig(DICTIONARY_CONF, "browser"), "CallApps", "browser");
            executeCommand(clearText, ConfigReader.readConfig(DICTIONARY_CONF, "conductor"), "CallApps", "conductor");
            executeCommand(clearText, ConfigReader.readConfig(DICTIONARY_CONF, "terminal"), "CallApps", "terminal");
            executeCommand(clearText, ConfigReader.readConfig(DICTIONARY_CONF, "store"), "CallApps", "store");
            executeCommand(clearText, ConfigReader.readConfig(DICTIONARY_CONF, "office"), "CallApps", "office");
            executeCommand(clearText, ConfigReader.readConfig(DICTIONARY_CONF, "messenger"), "CallApps", "messenger");
            executeCommand(clearText, ConfigReader.readConfig(DICTIONARY_CONF, "socialnetwork"), "CallApps", "socialnetwork");
            executeCommand(clearText, ConfigReader.readConfig(DICTIONARY_CONF, "notes"), "CallApps", "notes");
            executeCommand(clearText, ConfigReader.readConfig(DICTIONARY_CONF, "codeeditor"), "CallApps", "codeeditor");
            executeCommand(clearText, ConfigReader.readConfig(DICTIONARY_CONF, "reboot"), "CallShutdown", "reboot");
            executeCommand(clearText, ConfigReader.readConfig(DICTIONARY_CONF, "shutdown"), "CallShutdown", "shutdown");
            executeCommand(clearText, ConfigReader.readConfig(DICTIONARY_CONF, "sleep"), "CallShutdown", "sleep");
            ActionHandler.callSearch(clearText, ConfigReader.readConfig(DICTIONARY_CONF, "websearch"), ConfigReader.readConfig(DICTIONARY_CONF, "videosearch"));
            ActionHandler.callSystemValue(clearText, ConfigReader.readConfig(DICTIONARY_CONF, "volume"), "volume");
            ActionHandler.callSystemValue(clearText, ConfigReader.readConfig(DICTIONARY_CONF, "brightness"), "brightness");

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
            ActionHandler.callFunction(functionName, argForFunctions);
    }
}