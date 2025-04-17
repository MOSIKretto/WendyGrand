import java.lang.reflect.InvocationTargetException;
import java.io.IOException;
import Actions.*;


public class ActionHandler 
{
    // Запуск голоса и вызов функции для вызова приложений
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
    public static void CallBrowser() throws IOException {
        AppManager.startApp(getConfigValue("browser"));
    }

    public static void CallConductor() throws IOException {
        AppManager.startApp(getConfigValue("conductor"));
    }

    public static void CallTerminal() throws IOException {
        AppManager.startApp(getConfigValue("terminal"));
    }

    public static void CallStore() throws IOException {
        AppManager.startApp(getConfigValue("store"));
    }

    public static void CallOffice() throws IOException {
        AppManager.startApp(getConfigValue("office"));
    }

    public static void CallMessenger() throws IOException {
        AppManager.startApp(getConfigValue("messenger"));
    }

    public static void CallSocialNetwork() throws IOException {
        AppManager.startApp(getConfigValue("socialnetwork"));
    }

    public static void CallNotes() throws IOException {
        AppManager.startApp(getConfigValue("notes"));
    }

    public static void CallCodeEditor() throws IOException {
        AppManager.startApp(getConfigValue("codeeditor"));
    }

    // Работа с системой
    public static void CallReboot() throws IOException, InterruptedException {
        SystemManager.systemShutdown("-r", " перезапущена ");
    }

    public static void CallShutdown() throws IOException, InterruptedException {
        SystemManager.systemShutdown("-h", " выключена ");
    }

    public static void CallSleep() throws IOException, InterruptedException {
        SystemManager.systemSleep(" переведена в спящий режим ");
    }

    public static void CallVolume(String arg) {
        SystemManager.volumeArgs(arg);
    }

    // Поиск
    public static void CallWebSearch(String search) throws IOException {
        SearchManager.startSearch(getConfigValue("browser"), getConfigValue("websearch"), search);
    }

    public static void CallYouTubeSearch(String search) throws IOException {
        SearchManager.startSearch(getConfigValue("browser"), "https://www.youtube.com/results?search_query=", search);
    }

    // Вспомогательный метод для чтения конфигурации через ConfigReader
    private static String getConfigValue(String configKey) throws IOException 
    {
        String[] values = ConfigReader.readConfig("../WendyGrand/Configs/Apps.conf", configKey);
        return (values != null && values.length > 0) ? values[0] : null;
    }
}