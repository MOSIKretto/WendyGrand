import java.lang.reflect.InvocationTargetException;
import Resources.Managers.SystemManager;
import Resources.Managers.AppManager;
import java.io.IOException;
import java.util.List;


public class ActionHandler 
{
    
    public static void CallFunction(String FunctionName, Object... args) throws 
    InvocationTargetException, 
    IllegalArgumentException, 
    IllegalAccessException, 
    NoSuchMethodException, 
    InterruptedException, 
    SecurityException,
    IOException 
    {
        new ProcessBuilder("python3", "Voiceover.py", FunctionName + "Voiceover")
            .start()
            .waitFor();

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
        List<String> browser = GeneralHelper.readConfig("../WendyGrand/Configs/Apps.conf", "browser");
        if (!browser.isEmpty()) 
            AppManager.execute(browser.get(0));
        // else Вызов озвучки, что конфиг не заполнен
    }

    public static void CallConductor() throws 
    IOException 
    {
        List<String> conductor = GeneralHelper.readConfig("../WendyGrand/Configs/Apps.conf", "conductor");
        if (!conductor.isEmpty()) 
            AppManager.execute(conductor.get(0));
        // else Вызов озвучки, что конфиг не заполнен
    }

    public static void CallTerminal() throws 
    IOException 
    {
        List<String> terminal = GeneralHelper.readConfig("../WendyGrand/Configs/Apps.conf", "terminal");
        if (!terminal.isEmpty()) 
            AppManager.execute(terminal.get(0));
        // else Вызов озвучки, что конфиг не заполнен
    }

    public static void CallStore() throws 
    IOException 
    {
        List<String> store = GeneralHelper.readConfig("../WendyGrand/Configs/Apps.conf", "store");
        if (!store.isEmpty()) 
            AppManager.execute(store.get(0));
        // else Вызов озвучки, что конфиг не заполнен
    }

    public static void CallOffice() throws 
    IOException 
    {
        List<String> office = GeneralHelper.readConfig("../WendyGrand/Configs/Apps.conf", "office");
        if (!office.isEmpty()) 
            AppManager.execute(office.get(0));
        // else Вызов озвучки, что конфиг не заполнен
    }

    public static void CallMessenger() throws 
    IOException 
    {
        List<String> messenger = GeneralHelper.readConfig("../WendyGrand/Configs/Apps.conf", "messenger");
        if (!messenger.isEmpty()) 
            AppManager.execute(messenger.get(0));
        // else Вызов озвучки, что конфиг не заполнен
    }

    public static void CallSocialNetwork() throws 
    IOException 
    {
        List<String> social = GeneralHelper.readConfig("../WendyGrand/Configs/Apps.conf", "socialnetwork");
        if (!social.isEmpty()) 
            AppManager.execute(social.get(0));
        // else Вызов озвучки, что конфиг не заполнен
    }

    public static void CallNotes() throws 
    IOException 
    {
        List<String> notes = GeneralHelper.readConfig("../WendyGrand/Configs/Apps.conf", "notes");
        if (!notes.isEmpty()) 
            AppManager.execute(notes.get(0));
        // else Вызов озвучки, что конфиг не заполнен
    }

    public static void CallCodeEditor() throws 
    IOException 
    {
        List<String> editor = GeneralHelper.readConfig("../WendyGrand/Configs/Apps.conf", "codeeditor");
        if (!editor.isEmpty()) 
            AppManager.execute(editor.get(0));
        // else Вызов озвучки, что конфиг не заполнен
    }

    // Работа с системой
    public static void CallReboot() throws 
    InterruptedException, 
    IOException
    {
        SystemManager.systemShutdown("-r", " перезапущена ");
    }

    public static void CallShutdown() throws 
    InterruptedException, 
    IOException
    {
        SystemManager.systemShutdown("-h", " выключена ");
    }

    public static void CallSleep() throws 
    InterruptedException, 
    IOException
    {
        SystemManager.systemSleep(" переведена в спящий режим ");
    }

    public static void CallVolume(String arg) 
    {
        SystemManager.handleVolumeCommand(arg);
    }

    // Поиск
    public static void CallWebSearch(String search) throws 
    IOException 
    {
        List<String> browser = GeneralHelper.readConfig("../WendyGrand/Configs/Apps.conf", "browser");
        List<String> searchEngine = GeneralHelper.readConfig("../WendyGrand/Configs/Apps.conf", "websearch");

        if (!browser.isEmpty() && !searchEngine.isEmpty()) 
            AppManager.execute(browser.get(0), searchEngine.get(0) + search);
        // else Вызов озвучки, что конфиг не заполнен
    }

    public static void CallYouTubeSearch(String search) throws 
    IOException 
    {
        List<String> browser = GeneralHelper.readConfig("../WendyGrand/Configs/Apps.conf", "browser");
        List<String> searchEngine = GeneralHelper.readConfig("../WendyGrand/Configs/Apps.conf", "videosearch");

        if (!browser.isEmpty() && !searchEngine.isEmpty()) 
            AppManager.execute(browser.get(0), searchEngine.get(0) + search);
        // else Вызов озвучки, что конфиг не заполнен
    }
}