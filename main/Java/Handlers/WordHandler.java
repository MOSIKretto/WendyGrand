package main.Java.Handlers;

import java.lang.reflect.InvocationTargetException;
import main.Resources.GeneralHelper;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class WordHandler 
{

    private static final List<String> REMOVE_WORDS = Arrays.asList(
        "пожалуйста", "ладно", "давай", "прямо", "сейчас", "типо", "типа", "будь", "добра", "ну",
        "что-то", "открой", "откройте", "запусти", "начни", "выполнение", "выполнения", "начинаем", 
        "хз", "блять", "нахуй", "сука",
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
            executeCommand(clearText, GeneralHelper.readConfig(PATH, "hello"), "CallApps", "hello");
            executeCommand(clearText, GeneralHelper.readConfig(PATH, "browser"), "CallApps", "browser");
            executeCommand(clearText, GeneralHelper.readConfig(PATH, "conductor"), "CallApps", "conductor");
            executeCommand(clearText, GeneralHelper.readConfig(PATH, "terminal"), "CallApps", "terminal");
            executeCommand(clearText, GeneralHelper.readConfig(PATH, "store"), "CallApps", "store");
            executeCommand(clearText, GeneralHelper.readConfig(PATH, "office"), "CallApps", "office");
            executeCommand(clearText, GeneralHelper.readConfig(PATH, "messenger"), "CallApps", "messenger");
            executeCommand(clearText, GeneralHelper.readConfig(PATH, "socialnetwork"), "CallApps", "socialnetwork");
            executeCommand(clearText, GeneralHelper.readConfig(PATH, "notes"), "CallApps", "notes");
            executeCommand(clearText, GeneralHelper.readConfig(PATH, "codeeditor"), "CallApps", "codeeditor");
            executeCommand(clearText, GeneralHelper.readConfig(PATH, "reboot"), "CallShutdown", "reboot");
            executeCommand(clearText, GeneralHelper.readConfig(PATH, "shutdown"), "CallShutdown", "shutdown");
            executeCommand(clearText, GeneralHelper.readConfig(PATH, "sleep"), "CallShutdown", "sleep");
            ActionHandler.CallVolume(clearText, GeneralHelper.readConfig(PATH, "volume"));
            ActionHandler.CallSearch(clearText, GeneralHelper.readConfig(PATH, "websearch"), 
                                                GeneralHelper.readConfig(PATH, "videosearch"));

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