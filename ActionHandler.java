import java.lang.reflect.InvocationTargetException;
import java.io.IOException;
import java.util.List;
import Actions.*;


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
    public static void CallBrowser() throws IOException 
    {
        List<String> browser = ConfigReader.readConfig("../WendyGrand/Configs/Apps.conf", "browser");
        if (!browser.isEmpty()) 
            AppManager.startApp(browser.get(0));
        // else Вызов озвучки, что конфиг не заполнен
    }

    public static void CallConductor() throws IOException 
    {
        List<String> conductor = ConfigReader.readConfig("../WendyGrand/Configs/Apps.conf", "conductor");
        if (!conductor.isEmpty()) 
            AppManager.startApp(conductor.get(0));
        // else Вызов озвучки, что конфиг не заполнен
    }

    public static void CallTerminal() throws IOException 
    {
        List<String> terminal = ConfigReader.readConfig("../WendyGrand/Configs/Apps.conf", "terminal");
        if (!terminal.isEmpty()) 
            AppManager.startApp(terminal.get(0));
        // else Вызов озвучки, что конфиг не заполнен
    }

    public static void CallStore() throws IOException 
    {
        List<String> store = ConfigReader.readConfig("../WendyGrand/Configs/Apps.conf", "store");
        if (!store.isEmpty()) 
            AppManager.startApp(store.get(0));
        // else Вызов озвучки, что конфиг не заполнен
    }

    public static void CallOffice() throws IOException 
    {
        List<String> office = ConfigReader.readConfig("../WendyGrand/Configs/Apps.conf", "office");
        if (!office.isEmpty()) 
            AppManager.startApp(office.get(0));
        // else Вызов озвучки, что конфиг не заполнен
    }

    public static void CallMessenger() throws IOException 
    {
        List<String> messenger = ConfigReader.readConfig("../WendyGrand/Configs/Apps.conf", "messenger");
        if (!messenger.isEmpty()) 
            AppManager.startApp(messenger.get(0));
        // else Вызов озвучки, что конфиг не заполнен
    }

    public static void CallSocialNetwork() throws IOException 
    {
        List<String> social = ConfigReader.readConfig("../WendyGrand/Configs/Apps.conf", "socialnetwork");
        if (!social.isEmpty()) 
            AppManager.startApp(social.get(0));
        // else Вызов озвучки, что конфиг не заполнен
    }

    public static void CallNotes() throws IOException 
    {
        List<String> notes = ConfigReader.readConfig("../WendyGrand/Configs/Apps.conf", "notes");
        if (!notes.isEmpty()) 
            AppManager.startApp(notes.get(0));
        // else Вызов озвучки, что конфиг не заполнен
    }

    public static void CallCodeEditor() throws IOException 
    {
        List<String> editor = ConfigReader.readConfig("../WendyGrand/Configs/Apps.conf", "codeeditor");
        if (!editor.isEmpty()) 
            AppManager.startApp(editor.get(0));
        // else Вызов озвучки, что конфиг не заполнен
    }

    // Работа с системой
    public static void CallReboot() throws IOException, InterruptedException 
    {
        SystemManager.systemShutdown("-r", " перезапущена ");
    }

    public static void CallShutdown() throws IOException, InterruptedException 
    {
        SystemManager.systemShutdown("-h", " выключена ");
    }

    public static void CallSleep() throws IOException, InterruptedException 
    {
        SystemManager.systemSleep(" переведена в спящий режим ");
    }

    public static void CallVolume(String arg) 
    {
        SystemManager.volumeArgs(arg);
    }

    // Поиск
    public static void CallWebSearch(String search) throws IOException 
    {
        List<String> browser = ConfigReader.readConfig("../WendyGrand/Configs/Apps.conf", "browser");
        List<String> searchEngine = ConfigReader.readConfig("../WendyGrand/Configs/Apps.conf", "websearch");

        if (!browser.isEmpty() && !searchEngine.isEmpty()) 
            SearchManager.startSearch(browser.get(0), searchEngine.get(0), search);
        // else Вызов озвучки, что конфиг не заполнен
    }

    public static void CallYouTubeSearch(String search) throws IOException 
    {
        List<String> browser = ConfigReader.readConfig("../WendyGrand/Configs/Apps.conf", "browser");
        List<String> searchEngine = ConfigReader.readConfig("../WendyGrand/Configs/Apps.conf", "videosearch");

        if (!browser.isEmpty() && !searchEngine.isEmpty()) 
            SearchManager.startSearch(browser.get(0), searchEngine.get(0), search);
        // else Вызов озвучки, что конфиг не заполнен
    }
}