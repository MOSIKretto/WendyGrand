package main.Java.Handlers;

import main.Resources.enums.ConstPaths;
import main.Resources.GeneralHelper;
import java.io.IOException;
import java.util.List;
import java.io.File;


public class ActionHandlerModules 
{

    public static void handleModule(String word) throws 
    InterruptedException,
    IOException 
    {
        List<String> modules = GeneralHelper.readConfig(ConstPaths.DICTIONARY_MODULES.getConfPath(), word);

        for (String module : modules) 
        {
            File moduleFile = new File(ConstPaths.DIRECTORY_MODULES.getConfPath(), module);

            if (moduleFile.exists()) 
            {
                if (!modules.isEmpty()) 
                {
                    GeneralHelper.Voiceover("StandardModule_StandardResponse");
                    Thread.sleep(500);
                }

                System.out.println("Активация модуля: " + module.trim());
                RunModulesHandler.run(module.trim());
            } 
            else 
            {
                GeneralHelper.Voiceover("ErrModule");
                Thread.sleep(500);
                    
                System.err.println("Ошибка при запуске модуля: " + module);
            }
        }
    }
}