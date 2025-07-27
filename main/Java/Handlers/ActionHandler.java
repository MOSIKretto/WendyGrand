package main.Java.Handlers;

import main.Resources.Managers.ShutdownManager;
import main.Resources.Managers.VolumeManager;
import main.Resources.Managers.AppManager;
import main.Resources.enums.ConstPaths;
import main.Resources.GeneralHelper;
import java.io.IOException;
import java.util.List;
import java.util.Set;


public class ActionHandler 
{

    private static final String CONFIG = ConstPaths.APPS.getConfPath();

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
        GeneralHelper.Voiceover(args);
        List<String> app = GeneralHelper.readConfig(CONFIG, args);
        if (app != null && !app.isEmpty())
            AppManager.execute(app.get(0));
    }

    // работа с системой
    public static void CallShutdown(String args) throws 
    InterruptedException, 
    IOException 
    {
        switch (args) 
        {
            case "shutdown":
                GeneralHelper.Voiceover(args);
                ShutdownManager.systemShutdown("-h", "выключена");
                break;
            case "reboot":
                GeneralHelper.Voiceover(args);
                ShutdownManager.systemShutdown("-r", "перезапущена");
                break;
            case "sleep":
                GeneralHelper.Voiceover(args);
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

        String clearTextVolume = GeneralHelper.cleanInput(input, List.of("громкость", "на", "мне", "меня", "процента", "процент", "процентов", "сделай", "поставь", "установи"));

        VolumeManager.handleVolumeCommand(clearTextVolume);
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

        String searchQuery = GeneralHelper.cleanInput(input, cleanWords).replace(" ", "%20").trim();
        
        if (searchQuery.isEmpty())
            return;

        List<String> browser = GeneralHelper.readConfig(CONFIG, "browser");
        List<String> searchEngine = GeneralHelper.readConfig(CONFIG, searchType);

        GeneralHelper.Voiceover(searchType);
        AppManager.execute(browser.get(0), searchEngine.get(0) + searchQuery);
    }
}