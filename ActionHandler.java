import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.io.IOException;

import Actions.*;

public class ActionHandler
{
    public static void CallFunction(String FunctionName, Object... args) throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InterruptedException
    {
        ProcessBuilder builderVoiceover = new ProcessBuilder("python3", "Voiceover.py", FunctionName + "Voiceover");
        Process process = builderVoiceover.start();
        process.waitFor();
        
        Class<?>[] parameterTypes = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) 
        {
            parameterTypes[i] = args[i].getClass();
        }
        
        Method method = ActionHandler.class.getDeclaredMethod(FunctionName, parameterTypes);
        method.invoke(null, args);
    }
    
    //Вызов приложений
    public static void CallBrowser()
    {
        AppManager.startApp("firefox");
    }
    public static void CallConductor()
    {
        AppManager.startApp("nautilus");
    }
    public static void CallTerminal()
    {
        AppManager.startApp("gnome-terminal");
    }
    public static void CallStores()
    {
        AppManager.startApp("pamac-manager");
    }
    public static void CallOffice()
    {
        AppManager.startApp("libreoffice");
    }
    public static void CallMessenger()
    {
        AppManager.startApp("telegram-desktop");
    }
    public static void CallSocialNetwork()
    {
        AppManager.startApp("telegram-desktop");
    }
    public static void CallNotes()
    {
        AppManager.startApp("md.obsidian.Obsidian");
    }
    public static void CallCodeEditor()
    {
        AppManager.startApp("code");
    }

    //Работа с системой
    public static void CallReboot()
    {
        SystemShutdown.systemShutdown("-r", " перезапущена ");
    }
    public static void CallShutdown()
    {
        SystemShutdown.systemShutdown("-h", " выключена ");
    }
    public static void CallSleep()
    {
        SystemShutdown.systemSleep("systemctl suspend", " переведена в спящий режим ");
    }
    public static void CallVolume(String arg)
    {
        VolumeControl.VolumeArgs(arg);
    }

    //Поиск
    public static void CallWebSearch(String https, String search)
    {
        SearchManager.startSearch(https, search);
    }
    public static void CallYouTubeSearch(String https, String search)
    {
        SearchManager.startSearch(https, search);
    }
}