import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import Actions.BrowserManager;

public class ActionsHandler {
    
    public static void CallFunction(String FunctionName)
    {
        try
        {
        Method method = ActionsHandler.class.getDeclaredMethod(FunctionName);
        method.invoke(null);
        }
        catch(NoSuchMethodException ignored)
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
}
