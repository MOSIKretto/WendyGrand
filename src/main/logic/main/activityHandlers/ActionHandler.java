package src.main.logic.main.activityHandlers;

import src.main.logic.main.managers.mainLogics.ShutdownManager;
import src.main.logic.main.managers.mainLogics.VolumeManager;
import src.main.logic.helpers.ConfigReader;
import src.main.logic.helpers.Performer;
import src.main.logic.helpers.Scholar;
import src.main.logic.helpers.enums.ConstPaths;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ActionHandler 
{

    private static final String CONFIG = ConstPaths.APPS_CONF.getConfPath();
    private static final String VOICEOVER = ConstPaths.VOICEOVER.getConfPath();
    private static final String VOICEOVERVENV = ConstPaths.VOICEOVERVENV.getConfPath();

    public static void CallFunction(String functionName, Object... args) throws 
    InterruptedException, 
    IOException
    {
        if (args == null) 
            return;
        
        if (functionName.equals("CallApps"))
            CallApps((String) args[0]);

        else
            CallShutdown((String) args[0]);
    }

    // вызов приложений
    public static void CallApps(String args) throws 
    IOException 
    {
        Performer.execute(VOICEOVERVENV, VOICEOVER, args);
        List<String> app = ConfigReader.readConfig(CONFIG, args);
        if (app != null && !app.isEmpty())
            Performer.execute(app.get(0));
    }

    // работа с системой
    public static void CallShutdown(String args) throws 
    InterruptedException, 
    IOException 
    {
        switch (args) 
        {
            case "shutdown":
                Performer.execute(VOICEOVERVENV, VOICEOVER, args);
                ShutdownManager.systemShutdown("-h", "выключена");
                break;
            case "reboot":
                Performer.execute(VOICEOVERVENV, VOICEOVER, args);
                ShutdownManager.systemShutdown("-r", "перезапущена");
                break;
            case "sleep":
                Performer.execute(VOICEOVERVENV, VOICEOVER, args);
                ShutdownManager.systemSleep("переведена в спящий режим");
                break;
        }
    }

    // для громкости
    public static void CallVolume(String input, List<String> volumeCommands) throws 
    InterruptedException, 
    IOException
    {
        if (volumeCommands.stream().noneMatch(input::startsWith))
            return;

        String clearTextVolume = Scholar.cleanInput(input, List.of("громкость", "на", "мне", "меня", "процента", 
                                                                        "процент", "процентов", "сделай", "поставь", "установи"));
        
        Integer volume = VolumeManager.NUMBER_MAP.get(clearTextVolume);
        if (volume != null) 
        {
            VolumeManager.setSystemVolume(volume);
            Performer.execute(VOICEOVERVENV, VOICEOVER, "volume");
            return;
        }

        for (Map.Entry<String, Integer> entry : VolumeManager.NUMBER_MAP.entrySet()) 
        {
            if (clearTextVolume.contains(entry.getKey())) 
            {
                VolumeManager.setSystemVolume(entry.getValue());
                Performer.execute(VOICEOVERVENV, VOICEOVER, "volume");
                return;
            }
        }

        Performer.execute(VOICEOVERVENV, VOICEOVER, "volumeErr");
    }

    // для поиска в интернете и на видео площадках
    public static void CallSearch(String input, List<String> webSearchCommands, List<String> youtubeSearchCommands) throws 
    InterruptedException,
    IOException
    {
        final boolean isYoutubeSearch = youtubeSearchCommands.stream().anyMatch(input::startsWith);
        final boolean isWebSearch = !isYoutubeSearch && webSearchCommands.stream().anyMatch(input::startsWith);
        
        if (!isWebSearch && !isYoutubeSearch)
            return;

        final String searchType = isYoutubeSearch ? "videosearch" : "websearch";
        final Set<String> cleanWords = isYoutubeSearch 
                                        ? Set.of("найди", "найти", "на", "ищи", "ютубе", "ютюбе", "ютуб", "ютюб")
                                        : Set.of("найди", "найти", "в", "интернете", "ищи");

        String searchQuery = Scholar.cleanInput(input, cleanWords).replace(" ", "%20").trim();
        
        if (searchQuery.isEmpty())
            return;

        List<String> browser = ConfigReader.readConfig(CONFIG, "browser");
        List<String> searchEngine = ConfigReader.readConfig(CONFIG, searchType);

        Performer.execute(VOICEOVERVENV, VOICEOVER, searchType);
        Performer.execute(browser.get(0), searchEngine.get(0) + searchQuery);
    }
}