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
import io.github.cdimascio.dotenv.Dotenv;

import Actions.*;

public class ActionHandler
{
    public static void CallFunction(String FunctionName)
    {
        System.out.println("Here");
        Dotenv dotenv = Dotenv.configure().load();
        ProcessBuilder builder = new ProcessBuilder("python3", dotenv.get("AUDIOHELPER_DIR") + "BrowserVoice.py", FunctionName + "Voiceover");
        File log = new File("Handler.log");
        File cwd = new File(System.getProperty("user.dir"));
        builder.redirectOutput(ProcessBuilder.Redirect.appendTo(log));
        builder.redirectError(ProcessBuilder.Redirect.appendTo(log));
        builder.directory(cwd);
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
            method.invoke();
        }
        catch (NoSuchMethodException ignored)
        {
            System.out.println("No " + FunctionName);
        }
        catch (InvocationTargetException | IllegalAccessException e)
        {
            e.printStackTrace(System.err);
        }
    }

    private void CallBrowser()
    {
        BrowserManager.startBrowser();
    }
    private void CallTelegram()
    {
        TelegramManager.startTelegram();
    }
    private void CallVScode()
    {
        VScodeManager.startVScode();
    }
}
