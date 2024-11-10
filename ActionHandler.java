/* *ActionHandler*
 *
 * RU Обработчик команд полученныйх с Java_Dictionary
 * --------------------------------------------------------
 * EN Handler for commands received from Java_Dictionary
 *
*/

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.io.IOException;

import Actions.*;

public class ActionHandler
{
    public static void CallFunction(String FunctionName)
    {
        ProcessBuilder builderVoiceover = new ProcessBuilder("python3", "Voiceover.py", FunctionName + "Voiceover");
        try
        {
            builderVoiceover.start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            Method method = ActionHandler.class.getDeclaredMethod(FunctionName);
            method.invoke(null);
        }
        catch (NoSuchMethodException ignored){}
        catch (InvocationTargetException | IllegalAccessException e)
        {
            e.printStackTrace(System.err);
        }
    }
    
    //Вызов приложений
    public static void CallBrowser()
    {
        AppManager.startApp("firefox" /*,"google-chrome"*/);
    }
    public static void CallTelegram()
    {
        AppManager.startApp("telegram-desktop");
    }
    public static void CallObsidian()
    {
        AppManager.startApp("md.obsidian.Obsidian");
    }
    public static void CallVScode()
    {
        AppManager.startApp("code");
    }
    public static void CallStores()
    {
        AppManager.startApp("pamac-manager" /* "snap-store", "gnome-software" */);
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
    public static void CallUpgrade()
    {
        ShHelper.StartSh("Upgrade.sh");
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