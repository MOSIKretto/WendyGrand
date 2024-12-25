import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.File;

public class ActionHandlerModules 
{
    // Чтение Dictionary.conf
    public static void TXTreader(String word) throws IOException, InterruptedException
    {
        BufferedReader reader = new BufferedReader(new FileReader("../WendyGrand/Modules/Dictionary.conf"));
        String line;

        while ((line = reader.readLine()) != null) 
        {
            String[] parts = line.split("=");
            if (parts.length >= 2 && parts[0].trim().equals(word)) 
            {
                VoiceoverScript("StandardModule_StandardResponse");

                // Разделяем значение на отдельные модули по запятой
                String[] modules = parts[1].trim().split(",");
                for (String module : modules) {
                    functionStart(module.trim());}
            }
        }

        reader.close();
    }

    // Запуск модуля или предупреждение, что его нет
    private static void functionStart(String function) throws IOException, InterruptedException
    {
        File Modules = new File("../WendyGrand/Modules/YourModules/");
        File[] files = Modules.listFiles();

        File ModulesCheck = new File(Modules, function);

        if (files != null && ModulesCheck.exists()) 
        {
            System.out.println("Активация модуля: " + function);
            runModules.run(function);
        }
        else {
            VoiceoverScript("ErrModule");}
    }

    // Запуск озвучки
    private static void VoiceoverScript(String scriptPath) throws IOException, InterruptedException
    {
        new ProcessBuilder("python3", "../WendyGrand/Voiceover.py", scriptPath)
        .start()
        .waitFor();
    }
}