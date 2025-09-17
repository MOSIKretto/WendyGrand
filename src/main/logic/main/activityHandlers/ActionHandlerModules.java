package src.main.logic.main.activityHandlers;

import src.main.logic.helpers.ConfigReaderLogic;
import src.main.logic.helpers.Performer;
import src.main.logic.helpers.enums.ConstantsLogic;
import src.main.logic.main.managers.addons.RunPythonModules;
import src.main.voiceover.main.Voiceover;

import java.nio.file.Paths;
import java.util.List;
import java.io.File;


public class ActionHandlerModules 
{
    public static void handleModule(String word) throws 
    Exception 
    {
        List<String> modules = ConfigReaderLogic.readConfig(ConstantsLogic.DICTIONARY_MODULES_CONF.getConfPath(), word);
        boolean hasValidModules = false;
        
        for (String module : modules) 
        {
            File moduleFile = new File(ConstantsLogic.DIRECTORY_MODULES_PATH.getConfPath(), module.trim());
            if (moduleFile.exists()) 
            {
                if (!hasValidModules)
                {
                    Voiceover.startVoice("MODULES");
                    Thread.sleep(500);
                    hasValidModules = true;
                }
                runModules(module.trim());
            } 
            else 
            {
                Voiceover.startVoice("moduleERR");
                Thread.sleep(500);
            }
        }
    }
    
    private static void runModules(String arg) throws 
    Exception 
    {
        int periodIndex = arg.lastIndexOf('.');
        String extension = periodIndex != -1 ? arg.substring(periodIndex + 1) : "";
        String modulePath = Paths.get(ConstantsLogic.DIRECTORY_MODULES_PATH.getConfPath(), arg).toString();

        switch (extension) 
        {
            case "py" -> RunPythonModules.runPythonModule(modulePath, ConstantsLogic.DIRECTORY_MODULES_PATH.getConfPath());
            case "" -> Performer.execute("./" + modulePath);
            default -> Performer.execute(extension, modulePath);
        }
    }
}