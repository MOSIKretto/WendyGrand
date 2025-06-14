import Resources.Managers.RunModuleManager;
import java.io.IOException;
import java.util.List;
import java.io.File;

public class ActionHandlerModules 
{
    
    // Обработка модулей
    public static void handleModule(String word) throws 
    InterruptedException, 
    IOException
    {
        List<String> modules = GeneralHelper.readConfig("../WendyGrand/Configs/DictionaryModules.conf", word);
        
        if (!modules.isEmpty()) 
        {
            voiceoverScript("StandardModule_StandardResponse");

            for (String module : modules) 
            {
                try{
                    startModule(module.trim());} 
                catch (IOException | InterruptedException e) 
                {
                    System.err.println("Ошибка при запуске модуля: " + module);
                    //добавить озвучку ошибка модуля (типо ошибка в коде модуля)
                    e.printStackTrace();
                }
            }
        }
    }

    // Запуск модуля или предупреждение, что его нет
    private static void startModule(String moduleName) throws 
    InterruptedException, 
    IOException
    {
        File moduleFile = new File("../WendyGrand/Modules/", moduleName);

        if (moduleFile.exists()) 
        {
            System.out.println("Активация модуля: " + moduleName);
            RunModuleManager.run(moduleName);
        }
        else 
            voiceoverScript("ErrModule");
    }

    // Запуск озвучки
    private static void voiceoverScript(String scriptPath) throws 
    InterruptedException, 
    IOException
    {
        new ProcessBuilder("python3", "../WendyGrand/Voiceover.py", scriptPath)
            .start()
            .waitFor();
    }
}