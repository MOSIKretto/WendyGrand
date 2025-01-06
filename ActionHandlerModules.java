import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.Arrays;
import java.io.File;


public class ActionHandlerModules 
{
    // Пути к файлам и директориям
    private static final String CONFIG_PATH = "../WendyGrand/Configs/DictionaryModules.conf";
    private static final String MODULES_DIR = "../WendyGrand/Modules/";
    private static final String VOICEOVER_SCRIPT_PATH = "../WendyGrand/Voiceover.py";

    // Чтение DictionaryModules.conf
    public static void txtReader(String word) throws IOException, InterruptedException 
    {
        File configFile = new File(CONFIG_PATH);
        if (!configFile.exists()) 
        {
            System.err.println("Конфигурационный файл не найден: " + CONFIG_PATH); //Добавить озвучу отсутствия файла
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                line = line.trim();

                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] parts = line.split("=");
                if (parts.length >= 2 && parts[0].trim().equals(word)) 
                {
                    voiceoverScript("StandardModule_StandardResponse");

                    // Разделяем значение на отдельные модули по запятой
                    Arrays.stream(parts[1].trim().split(","))
                    .map(String::trim)
                    .forEach(module -> {
                        try 
                        {
                            functionStart(module);
                        } 
                        catch (IOException | InterruptedException e) 
                        {
                            System.err.println("Ошибка при запуске модуля: " + module); //добавить озвучку ошибка модуля
                            e.printStackTrace();
                        }
                    });
                }
            }
        }
    }

    // Запуск модуля или предупреждение, что его нет
    private static void functionStart(String function) throws IOException, InterruptedException 
    {
        File modulesDir = new File(MODULES_DIR);
        File moduleFile = new File(modulesDir, function);

        if (moduleFile.exists()) 
        {
            System.out.println("Активация модуля: " + function);
            RunModules.run(function);
        } 
        else voiceoverScript("ErrModule");
    }

    // Запуск озвучки (пропадет с появлением Voiceover.java)
    private static void voiceoverScript(String scriptPath) throws IOException, InterruptedException 
    {
        new ProcessBuilder("python3", VOICEOVER_SCRIPT_PATH, scriptPath)
        .start()
        .waitFor();
    }
}