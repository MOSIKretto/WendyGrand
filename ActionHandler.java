/* *ActionHandler*
 *
 * RU Обработчик команд полученныйх с Java_Dictionary
 * --------------------------------------------------------
 * EN Handler for commands received from Java_Dictionary
 *
 */

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.io.File;

import Actions.*;

public class ActionHandler
{
    public static void CallFunction(String FunctionName)
    {
        ProcessBuilder builder = new ProcessBuilder("python3", "Voiceover.py", FunctionName + "Voiceover");
        File log = new File("Handler.log");
        File cwd = new File(System.getProperty("user.dir"));
        builder.redirectOutput(ProcessBuilder.Redirect.appendTo(log));
        builder.redirectError(ProcessBuilder.Redirect.appendTo(log));
        builder.directory(cwd.getAbsoluteFile());
        try
        {
            builder.start();
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

    public static void CallBrowser()
    {
        WebManager.startWeb("firefox" /*,"google-chrome"*/);
    }
    public static void CallWebSearch(String https, String search)
    {
        SearchManager.startSearch(https, search);
    }
    public static void CallYouTubeSearch(String https, String search)
    {
        SearchManager.startSearch(https, search);
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
}
