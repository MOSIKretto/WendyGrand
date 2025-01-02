import java.lang.reflect.InvocationTargetException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import Actions.*;

public class ActionHandler
{
    //запуска голоса и вызов фунции для вызова приложений
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

        Class<?>[] parameterTypes = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            parameterTypes[i] = args[i].getClass();}
        
        ActionHandler.class.getDeclaredMethod(FunctionName, parameterTypes).invoke(null, args); 
    }

    // Чтение конфигурации
    private static String personalConfigReader(String configResult) throws IOException 
    {
        try (BufferedReader reader = new BufferedReader(new FileReader("../WendyGrand/AppConfig.conf"))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                if (line.startsWith(configResult + "=")) 
                {
                    int equalsIndex = line.indexOf('=');
                    return line.substring(equalsIndex + 1);
                }
            }
        }
        return null;
    }
    

    //Вызов приложений (Добавить вызов озвучки при появлении Voiceover.java)
    public static void CallBrowser() throws IOException{
        AppManager.startApp(personalConfigReader("browser"));}

    public static void CallConductor() throws IOException{
        AppManager.startApp(personalConfigReader("conductor"));}

    public static void CallTerminal() throws IOException{
        AppManager.startApp(personalConfigReader("terminal"));}

    public static void CallStore() throws IOException{
        AppManager.startApp(personalConfigReader("store"));}

    public static void CallOffice() throws IOException{
        AppManager.startApp(personalConfigReader("office"));}

    public static void CallMessenger() throws IOException{
        AppManager.startApp(personalConfigReader("messenger"));}

    public static void CallSocialNetwork() throws IOException{
        AppManager.startApp(personalConfigReader("socialnetwork"));}

    public static void CallNotes() throws IOException{
        AppManager.startApp(personalConfigReader("notes"));}

    public static void CallCodeEditor() throws IOException{
        AppManager.startApp(personalConfigReader("codeeditor"));}

    //Работа с системой
    public static void CallReboot(){
        SystemShutdown.systemShutdown("-r", " перезапущена ");}

    public static void CallShutdown(){
        SystemShutdown.systemShutdown("-h", " выключена ");}

    public static void CallSleep(){
        SystemShutdown.systemSleep("systemctl suspend", " переведена в спящий режим ");}

    public static void CallVolume(String arg){
        VolumeControl.VolumeArgs(arg);}

    //Поиск
    public static void CallWebSearch(String search) throws IOException{
        SearchManager.startSearch(personalConfigReader("websearch"), search);}

    public static void CallYouTubeSearch(String search){
        SearchManager.startSearch("https://www.youtube.com/results?search_query=", search);}
}