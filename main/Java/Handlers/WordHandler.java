package main.Java.Handlers;

import java.lang.reflect.InvocationTargetException;
import main.Resources.GeneralHelper;
import java.util.Collections;
import java.io.IOException;
import java.util.List;


public class WordHandler
{

    private static final String CONFIG = "../WendyGrand/Configs/Dictionary.conf";

    private static List<String> REMOVE_WORDS() 
    {
        try { return GeneralHelper.readConfig(CONFIG, "delete"); } 
        catch (IOException e) { return Collections.emptyList(); } 
    }

    public static void main(String[] args) throws 
    Exception
    {
        for (String arg : args) 
        {
            String clearText = GeneralHelper.cleanInput(arg, REMOVE_WORDS());

            // Основные команды
            executeCommand(clearText, GeneralHelper.readConfig(CONFIG, "hello"), "CallApps", "hello");
            executeCommand(clearText, GeneralHelper.readConfig(CONFIG, "browser"), "CallApps", "browser");
            executeCommand(clearText, GeneralHelper.readConfig(CONFIG, "conductor"), "CallApps", "conductor");
            executeCommand(clearText, GeneralHelper.readConfig(CONFIG, "terminal"), "CallApps", "terminal");
            executeCommand(clearText, GeneralHelper.readConfig(CONFIG, "store"), "CallApps", "store");
            executeCommand(clearText, GeneralHelper.readConfig(CONFIG, "office"), "CallApps", "office");
            executeCommand(clearText, GeneralHelper.readConfig(CONFIG, "messenger"), "CallApps", "messenger");
            executeCommand(clearText, GeneralHelper.readConfig(CONFIG, "socialnetwork"), "CallApps", "socialnetwork");
            executeCommand(clearText, GeneralHelper.readConfig(CONFIG, "notes"), "CallApps", "notes");
            executeCommand(clearText, GeneralHelper.readConfig(CONFIG, "codeeditor"), "CallApps", "codeeditor");
            executeCommand(clearText, GeneralHelper.readConfig(CONFIG, "reboot"), "CallShutdown", "reboot");
            executeCommand(clearText, GeneralHelper.readConfig(CONFIG, "shutdown"), "CallShutdown", "shutdown");
            executeCommand(clearText, GeneralHelper.readConfig(CONFIG, "sleep"), "CallShutdown", "sleep");
            ActionHandler.CallVolume(clearText, GeneralHelper.readConfig(CONFIG, "volume"));
            ActionHandler.CallSearch(clearText, GeneralHelper.readConfig(CONFIG, "websearch"), 
                                                GeneralHelper.readConfig(CONFIG, "videosearch"));

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