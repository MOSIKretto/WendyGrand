package src.main.logic.main.managers.modulesLogics;

import src.main.logic.main.managers.modulesLogics.addons.RunPythonModules;
import src.main.logic.helpers.enums.ConstPaths;
import src.main.logic.helpers.Performer;

import java.io.IOException;
import java.nio.file.Paths;


public class RunModulesManager
{

    private final static String DIRECTORY_MODULES_PATH = ConstPaths.DIRECTORY_MODULES_PATH.getConfPath();

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
        String modulePath = Paths.get(DIRECTORY_MODULES_PATH, arg).toString();

        switch (extension) 
        {
            case "py":
                RunPythonModules.runPythonModule(modulePath, DIRECTORY_MODULES_PATH);
                break;

            case "":
                Performer.execute("./" + modulePath);
                break;
            
            default:
                Performer.execute(extension, modulePath);
                break;
        }
    }
}