package main.Java.Handlers;

import main.Resources.Managers.SystemManager;
import main.Resources.Managers.AppManager;
import main.Resources.GeneralHelper;
import java.io.IOException;
import java.util.List;


public class ActionHandler 
{

    private static String PATH = "../WendyGrand/Configs/Apps.conf";

    public static void CallFunction(String FunctionName, Object... args) throws  
    InterruptedException,
    IOException 
    {
        GeneralHelper.Performer("python3", "../WendyGrand/main/Python/Voiceover.py", FunctionName + "Voiceover");

        try 
        {
            Class<?>[] parameterTypes = new Class<?>[args.length];

            for (int i = 0; i < args.length; i++)
                parameterTypes[i] = args[i].getClass();

            ActionHandler.class.getDeclaredMethod(FunctionName, parameterTypes).invoke(null, args); 
        } 
        catch (Exception e) {}
    }

    // Вызов приложений
    public static void CallBrowser() throws 
    IOException 
    {
        List<String> browser = GeneralHelper.readConfig(PATH, "browser");
        if (!browser.isEmpty()) 
            AppManager.execute(browser.get(0));
        // else Вызов озвучки, что конфиг не заполнен
    }

    public static void CallConductor() throws 
    IOException 
    {
        List<String> conductor = GeneralHelper.readConfig(PATH, "conductor");
        if (!conductor.isEmpty()) 
            AppManager.execute(conductor.get(0));
        // else Вызов озвучки, что конфиг не заполнен
    }

    public static void CallTerminal() throws 
    IOException 
    {
        List<String> terminal = GeneralHelper.readConfig(PATH, "terminal");
        if (!terminal.isEmpty()) 
            AppManager.execute(terminal.get(0));
        // else Вызов озвучки, что конфиг не заполнен
    }

    public static void CallStore() throws 
    IOException 
    {
        List<String> store = GeneralHelper.readConfig(PATH, "store");
        if (!store.isEmpty()) 
            AppManager.execute(store.get(0));
        // else Вызов озвучки, что конфиг не заполнен
    }

    public static void CallOffice() throws 
    IOException 
    {
        List<String> office = GeneralHelper.readConfig(PATH, "office");
        if (!office.isEmpty()) 
            AppManager.execute(office.get(0));
        // else Вызов озвучки, что конфиг не заполнен
    }

    public static void CallMessenger() throws 
    IOException 
    {
        List<String> messenger = GeneralHelper.readConfig(PATH, "messenger");
        if (!messenger.isEmpty()) 
            AppManager.execute(messenger.get(0));
        // else Вызов озвучки, что конфиг не заполнен
    }

    public static void CallSocialNetwork() throws 
    IOException 
    {
        List<String> social = GeneralHelper.readConfig(PATH, "socialnetwork");
        if (!social.isEmpty()) 
            AppManager.execute(social.get(0));
        // else Вызов озвучки, что конфиг не заполнен
    }

    public static void CallNotes() throws 
    IOException 
    {
        List<String> notes = GeneralHelper.readConfig(PATH, "notes");
        if (!notes.isEmpty()) 
            AppManager.execute(notes.get(0));
        // else Вызов озвучки, что конфиг не заполнен
    }

    public static void CallCodeEditor() throws 
    IOException 
    {
        List<String> editor = GeneralHelper.readConfig(PATH, "codeeditor");
        if (!editor.isEmpty()) 
            AppManager.execute(editor.get(0));
        // else Вызов озвучки, что конфиг не заполнен
    }

    // Работа с системой
    public static void CallReboot() throws 
    InterruptedException, 
    IOException
    {
        SystemManager.systemShutdown("-r", "перезапущена");
    }

    public static void CallShutdown() throws 
    InterruptedException, 
    IOException
    {
        SystemManager.systemShutdown("-h", "выключена");
    }

    public static void CallSleep() throws 
    InterruptedException, 
    IOException
    {
        SystemManager.systemSleep("переведена в спящий режим");
    }

    public static void CallVolume(String arg) 
    {
        SystemManager.handleVolumeCommand(arg);
    }

    // Поиск
    public static void CallSearch(String search, String key) throws 
    IOException 
    {
        List<String> browser = GeneralHelper.readConfig(PATH, "browser");
        List<String> searchEngine = GeneralHelper.readConfig(PATH, key);

        if (!browser.isEmpty() && !searchEngine.isEmpty()) 
            AppManager.execute(browser.get(0), searchEngine.get(0) + search);
        // else Вызов озвучки, что конфиг не заполнен
    }
}