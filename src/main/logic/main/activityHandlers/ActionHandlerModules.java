package src.main.logic.main.activityHandlers;

import src.main.logic.helpers.ConfigReader;
import src.main.logic.helpers.Performer;
import src.main.logic.helpers.enums.ConstPaths;
import src.main.logic.main.managers.modulesLogics.RunModulesManager;

import java.io.IOException;
import java.util.List;
import java.io.File;


public class ActionHandlerModules 
{
    private static final String VOICEOVER = ConstPaths.VOICEOVER.getConfPath();
    private static final String VOICEOVERVENV = ConstPaths.VOICEOVERVENV.getConfPath();

    public static void handleModule(String word) throws 
    InterruptedException,
    IOException
    {
        List<String> modules = ConfigReader.readConfig(ConstPaths.DICTIONARY_MODULES_CONF.getConfPath(), word);

        for (String module : modules)
        {
            File moduleFile = new File(ConstPaths.DIRECTORY_MODULES_PATH.getConfPath(), module);

            if (moduleFile.exists()) 
            {
                if (!modules.isEmpty()) 
                {
                    Performer.execute(VOICEOVERVENV, VOICEOVER, "StandardModule_StandardResponse");
                    Thread.sleep(500);
                }

                System.out.println("Активация модуля: " + module.trim());
                RunModulesManager.run(module.trim());
            } 
            else 
            {
                Performer.execute(VOICEOVERVENV, VOICEOVER, "ErrModule");
                Thread.sleep(500);
                    
                System.err.println("Ошибка при запуске модуля: " + module);
            }
        }
    }
}