package src.main.logic.main;

import src.main.logic.main.activityHandlers.*;
import src.main.logic.helpers.ConfigReaderLogic;
import src.main.logic.helpers.Scholar;
import src.main.logic.helpers.enums.ConstantsLogic;

import java.util.List;


public class DictionaryHandler 
{
    public static void main(String[] args) throws 
    Exception 
    {
        for (String arg : args) 
        {
            String clearText = Scholar.cleanInput(arg, ConfigReaderLogic.readConfig(ConstantsLogic.DICTIONARY_CONF.getConfPath(), "delete"));

            executeCommand(clearText, "hello", "CallApps", "hello");
            executeCommand(clearText, "browser", "CallApps", "browser");
            executeCommand(clearText, "conductor", "CallApps", "conductor");
            executeCommand(clearText, "terminal", "CallApps", "terminal");
            executeCommand(clearText, "store", "CallApps", "store");
            executeCommand(clearText, "office", "CallApps", "office");
            executeCommand(clearText, "messenger", "CallApps", "messenger");
            executeCommand(clearText, "socialnetwork", "CallApps", "socialnetwork");
            executeCommand(clearText, "notes", "CallApps", "notes");
            executeCommand(clearText, "codeeditor", "CallApps", "codeeditor");
            executeCommand(clearText, "reboot", "CallShutdown", "reboot");
            executeCommand(clearText, "shutdown", "CallShutdown", "shutdown");
            executeCommand(clearText, "sleep", "CallShutdown", "sleep");
            ActionHandler.callSystemValue(clearText, ConfigReaderLogic.readConfig(ConstantsLogic.DICTIONARY_CONF.getConfPath(), "volume"), "volume");
            ActionHandler.callSystemValue(clearText, ConfigReaderLogic.readConfig(ConstantsLogic.DICTIONARY_CONF.getConfPath(), "brightness"), "brightness");
            ActionHandler.callSearch(clearText, ConfigReaderLogic.readConfig(ConstantsLogic.DICTIONARY_CONF.getConfPath(), "websearch"), 
                                                ConfigReaderLogic.readConfig(ConstantsLogic.DICTIONARY_CONF.getConfPath(), "videosearch"));

            if (!clearText.isEmpty()) ActionHandlerModules.handleModule(clearText);
        }
    }

    private static void executeCommand(String input, String commandKey, String functionName, String argForFunctions) throws 
    Exception 
    {
        List<String> commands = ConfigReaderLogic.readConfig(ConstantsLogic.DICTIONARY_CONF.getConfPath(), commandKey);
        if (commands != null && !commands.isEmpty() && commands.contains(input)) ActionHandler.callFunction(functionName, argForFunctions);
    }
}