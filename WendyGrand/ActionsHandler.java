/* *ActionsHandler*
 *
 * RU Обработчик команд полученныйх с Java_Dictionary
 * --------------------------------------------------------
 * EN Handler for commands received from Java_Dictionary
 * 
 */
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import Actions.*;

public class ActionsHandler {

    public static void CallFunction(String FunctionName) 
    {
        try 
        {
            Method method = ActionsHandler.class.getDeclaredMethod(FunctionName);
            method.invoke(null);
        } 
        catch (NoSuchMethodException ignored) 
        {
            System.out.println("No " + FunctionName);
        } 
        catch (InvocationTargetException ignored) 
        {
            ignored.printStackTrace(System.err);
        } 
        catch (IllegalAccessException ignored) 
        {
            ignored.printStackTrace(System.err);
        }
    }

    private static void CallBrowser() 
    {
        BrowserManager.startBrowser();
    }
    private static void CallTelegram()
    {
        TelegramManager.startTelegram();
    }
    private static void CallVScode()
    {
        VScodeManager.startVScode();
    }
}
