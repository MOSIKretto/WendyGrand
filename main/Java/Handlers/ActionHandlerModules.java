package main.Java.Handlers;

import main.Resources.GeneralHelper;
import java.io.IOException;
import java.util.List;
import java.io.File;


public class ActionHandlerModules 
{
    // обработка модулей
    public static void handleModule(String word) throws 
    InterruptedException, 
    IOException
    {
        List<String> modules = GeneralHelper.readConfig("../WendyGrand/Configs/DictionaryModules.conf", word);
        
        if (!modules.isEmpty())
        {
            GeneralHelper.Performer("python3", "../WendyGrand/main/Python/Voiceover.py", "StandardModule_StandardResponse");
            Thread.sleep(500);

            for (String module : modules)
            {
                try{
                    startModule(module.trim());} 
                catch (IOException | InterruptedException e) 
                {
                    System.err.println("Ошибка при запуске модуля: " + module);
                    GeneralHelper.Voiceover("ErrModuleCode");
                    e.printStackTrace();
                }
            }
        }
    }

    // запуск модуля или предупреждение, что его нет
    private static void startModule(String moduleName) throws 
    InterruptedException, 
    IOException
    {
        File moduleFile = new File("../WendyGrand/Modules/", moduleName);

        if (moduleFile.exists()) 
        {
            System.out.println("Активация модуля: " + moduleName);
            RunModulesHandler.run(moduleName);
        }
        else
            GeneralHelper.Voiceover("ErrModule");
    }
}