package src.main.logic.main.activityHandlers;

import src.main.logic.main.managers.ShutdownManager;
import src.main.logic.main.managers.SystemValueManager;
import src.main.voiceover.main.Voiceover;
import src.main.logic.helpers.ConfigReaderLogic;
import src.main.logic.helpers.Performer;
import src.main.logic.helpers.Scholar;
import src.main.logic.helpers.enums.ConstantsLogic;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public class ActionHandler
{
    private static final Map<String, Integer> VALUE_ACTIONS = Map.of(
        "value+", -2,
        "value-", -3,
        "valuemax", 100,
        "valuemin", 10,
        "valueon", 50,
        "valueoff", 0
    );

    public static void callFunction(String functionName, Object arg) throws 
    Exception 
    {
        if (functionName.equals("CallApps")) callApps((String) arg);
        else callShutdown((String) arg);
    }

    private static void callApps(String args) throws 
    IOException 
    {
        Voiceover.startVoice(args);
        List<String> app = ConfigReaderLogic.readConfig(ConstantsLogic.APPS_CONF.getConfPath(), args);
        if (app != null && !app.isEmpty()) Performer.execute(app.get(0));
    }

    private static void callShutdown(String args) throws 
    Exception 
    {
        Voiceover.startVoice(args);
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
                ConfigReaderLogic.readConfig(ConstantsLogic.DICTIONARY_CONF.getConfPath(), "deletevideosearch") :
                ConfigReaderLogic.readConfig(ConstantsLogic.DICTIONARY_CONF.getConfPath(), "deletewebsearch");
            
            String query = Scholar.cleanInput(input, cleanWords).replace(" ", "%20").trim();
            
            if (!query.isEmpty()) 
            {
                String searchType = isYoutube ? "videosearch" : "websearch";
                List<String> browser = ConfigReaderLogic.readConfig(ConstantsLogic.APPS_CONF.getConfPath(), "browser");
                List<String> searchEngine = ConfigReaderLogic.readConfig(ConstantsLogic.APPS_CONF.getConfPath(), searchType);

                Voiceover.startVoice(searchType);
                Performer.execute(browser.get(0), searchEngine.get(0) + query);
            }
        }
    }
    
    public static void callSystemValue(String input, List<String> valueKeys, String valueType) throws 
    IOException 
    {
        if (valueKeys.stream().noneMatch(input::contains)) return;
        
        try 
        {
            String cleanedInput = Scholar.cleanInput(input, ConfigReaderLogic.readConfig(ConstantsLogic.DICTIONARY_CONF.getConfPath(), "delete" + valueType));
            Integer targetValue = null;
            
            for (Map.Entry<String, Integer> entry : VALUE_ACTIONS.entrySet()) 
            {
                List<String> configValues = ConfigReaderLogic.readConfig(ConstantsLogic.DICTIONARY_CONF.getConfPath(), entry.getKey());
                if (SystemValueManager.containsAny(cleanedInput, configValues)) 
                {
                    targetValue = entry.getValue();
                    break;
                }
            }
            
            if (targetValue == null) targetValue = SystemValueManager.parseNumber(cleanedInput);
            
            if (targetValue != null) 
            {
                switch (valueType) 
                {
                    case "volume" -> SystemValueManager.setSystemVolume(targetValue);
                    case "brightness" -> SystemValueManager.setSystemBrightness(targetValue);
                }
                Voiceover.startVoice(valueType);
            }
        }
        catch (IOException e) { Voiceover.startVoice(valueType + "Err"); }
    }
}