package main.Java.Handlers;

import main.Resources.Managers.Addons.RunPythonModules;
import main.Resources.GeneralHelper;
import java.io.IOException;
import java.nio.file.Paths;


public class RunModulesHandler
{

    private final static String PATH = "../WendyGrand/Modules/";

    // определение языка по расширению файла
    private static String sliceUntilPeriod(String inputString)
    {
        int periodIndex = inputString.lastIndexOf('.');
        return periodIndex != -1 ? inputString.substring(periodIndex + 1) : "";
    }

    // проверка на язык и выполнение соответствующего модуля
    public static void run(String arg) throws 
    InterruptedException, 
    IOException
    {
        String extension = sliceUntilPeriod(arg);
        String modulePath = Paths.get(PATH, arg).toString();

        switch (extension) 
        {
            case "py":
                RunPythonModules.runPythonModule(modulePath, PATH);
                break;

            case "":
                GeneralHelper.Performer("./" + modulePath);
                break;
            
            default:
                GeneralHelper.Performer(extension, modulePath);
                break;
        }
    }
}