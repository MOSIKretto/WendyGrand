package main.Java.Handlers;

import main.Resources.GeneralHelper;
import java.io.IOException;
import java.util.List;
import java.io.File;


public class ActionHandlerModules 
{
    
    private final static String CONFIG = "../WendyGrand/Configs/DictionaryModules.conf";

    public static void handleModule(String word) throws 
    InterruptedException, 
    IOException 
    {
        List<String> modules = GeneralHelper.readConfig(CONFIG, word);
        boolean voiceoverPlayed = false;
        boolean errorVoiceoverPlayed = false;

        for (String module : modules) 
        {
            File moduleFile = new File("../WendyGrand/Modules/", module);

            if (moduleFile.exists()) 
            {
                if (!modules.isEmpty() && !voiceoverPlayed) 
                {
                    GeneralHelper.Voiceover("StandardModule_StandardResponse");
                    Thread.sleep(500);
                    voiceoverPlayed = true;
                }

                System.out.println("Активация модуля: " + module.trim());
                RunModulesHandler.run(module.trim());
            } 
            else 
            {
                if (!errorVoiceoverPlayed) 
                {
                    GeneralHelper.Voiceover("ErrModule");
                    Thread.sleep(500);
                    errorVoiceoverPlayed = true;
                }

                System.err.println("Ошибка при запуске модуля: " + module);
            }
        }
    }
}