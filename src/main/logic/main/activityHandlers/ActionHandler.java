package src.main.logic.main.activityHandlers;

import src.main.logic.main.managers.mainLogics.ShutdownManager;
import src.main.logic.main.managers.mainLogics.SystemValueManager;
import src.main.logic.helpers.ConfigReader;
import src.main.logic.helpers.Performer;
import src.main.logic.helpers.Scholar;
import src.main.logic.helpers.enums.ConstPaths;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public class ActionHandler 
{
    private static final String APPS_CONF = ConstPaths.APPS_CONF.getConfPath();
    private static final String DICTIONARY_CONF = ConstPaths.DICTIONARY_CONF.getConfPath();
    private static final String VOICEOVER = ConstPaths.VOICEOVER.getConfPath();
    private static final String VOICEOVERVENV = ConstPaths.VOICEOVERVENV.getConfPath();

    public static void callFunction(String functionName, Object arg) throws 
    InterruptedException,
    IOException
    {
        if (functionName.equals("CallApps"))
            callApps((String) arg);
        else
            callShutdown((String) arg);
    }

    private static void callApps(String args) throws 
    IOException
    {
        Performer.execute(VOICEOVERVENV, VOICEOVER, args);
        List<String> app = ConfigReader.readConfig(APPS_CONF, args);
        if (app != null && !app.isEmpty())
            Performer.execute(app.get(0));
    }

    private static void callShutdown(String args) throws 
    InterruptedException,
    IOException 
    {
        Performer.execute(VOICEOVERVENV, VOICEOVER, args);
        switch (args) 
        {
            case "shutdown" -> ShutdownManager.systemShutdown("-h", "выключена");
            case "reboot" -> ShutdownManager.systemShutdown("-r", "перезапущена");
            case "sleep" -> ShutdownManager.systemSleep("переведена в спящий режим");
        }
    }
    
    public static void callSearch(String input, List<String> webSearchCommands, List<String> youtubeSearchCommands) throws 
    IOException 
    {
        boolean isYoutube = youtubeSearchCommands.stream().anyMatch(input::startsWith);
        boolean isWeb = webSearchCommands.stream().anyMatch(input::startsWith);
        
        if (isYoutube || isWeb) 
        {
            List<String> cleanWords = isYoutube ? 
                ConfigReader.readConfig(DICTIONARY_CONF, "deletevideosearch") :
                ConfigReader.readConfig(DICTIONARY_CONF, "deletewebsearch");
            
            String query = Scholar.cleanInput(input, cleanWords).replace(" ", "%20").trim();
            
            if (!query.isEmpty()) 
            {
                String searchType = isYoutube ? "videosearch" : "websearch";
                List<String> browser = ConfigReader.readConfig(APPS_CONF, "browser");
                List<String> searchEngine = ConfigReader.readConfig(APPS_CONF, searchType);

                Performer.execute(VOICEOVERVENV, VOICEOVER, searchType);
                Performer.execute(browser.get(0), searchEngine.get(0) + query);
            }
        }
    }
    
    private static final Map<String, Integer> VALUE_ACTIONS = Map.of(
        "value+", -2, 
        "value-", -3, 
        "valuemax", 100,
        "valuemin", 10, 
        "valueon", 50, 
        "valueoff", 0
    );

    public static void callSystemValue(String input, List<String> valueKeys, String valueType) throws 
    IOException 
    {
        if (valueKeys.stream().noneMatch(input::contains)) 
            return;
        
        try 
        {
            String cleanedInput = Scholar.cleanInput(input, ConfigReader.readConfig(DICTIONARY_CONF, "delete" + valueType));
            Integer targetValue = null;
            
            for (Map.Entry<String, Integer> entry : VALUE_ACTIONS.entrySet()) 
            {
                List<String> configValues = ConfigReader.readConfig(DICTIONARY_CONF, entry.getKey());

                if (SystemValueManager.containsAny(cleanedInput, configValues)) 
                {
                    targetValue = entry.getValue();
                    break;
                }
            }
            
            if (targetValue == null)
                targetValue = SystemValueManager.parseNumber(cleanedInput);
            
            if (targetValue != null) 
            {
                switch (valueType) 
                {
                    case "volume" -> SystemValueManager.setSystemVolume(targetValue);
                    case "brightness" -> SystemValueManager.setSystemBrightness(targetValue);
                }
                Performer.execute(VOICEOVERVENV, VOICEOVER, valueType);
            }
        } 
        catch (IOException e)
        {
            Performer.execute(VOICEOVERVENV, VOICEOVER, valueType + "Err");
        }
    }
}