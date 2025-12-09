package org.wendygrand.core;

import org.wendygrand.config.*;
import org.wendygrand.enums.Constants;
import org.wendygrand.communication.Voiceover;

import java.util.Collection;
import java.util.logging.Logger;

public class Distributor
{
    private static final Logger LOGGER = Logger.getLogger(Distributor.class.getName());
    
    public static void search(String arg) 
    {
        try 
        {
            ConfigReader.CommandResult result = ConfigReader.findCommand(
                Constants.DICTIONARY_CONF.getConfPath(), 
                cleanInput(
                    arg,
                    ConfigReader.readConfig(Constants.DICTIONARY_CONF.getConfPath(), "delete")
                )
            );
            
            if (result == null) 
            {
                LOGGER.fine("Команда не найдена для ввода: " + arg);
                return;
            }
            
            if (result.hasApplication()) 
            {   
                for (ConfigReader.ApplicationWithDelay appWithDelay : result.getApplications()) 
                {
                    String application = appWithDelay.getApplication();
                    int delay = appWithDelay.getDelay();
                    
                    if (delay <= 0) 
                    {
                        // Без задержки
                        try 
                        {
                            Performer.execute(application);
                            LOGGER.info("Запущена программа: " + application);
                        } 
                        catch (Exception e) { LOGGER.warning("Ошибка при запуске программы " + application + ": " + e.getMessage()); }
                    } 
                    else 
                    {
                        // С задержкой
                        Thread thread = new Thread(() -> {
                            try 
                            {
                                LOGGER.info("Ожидание " + delay + " секунд перед запуском: " + application);
                                Thread.sleep(delay * 1000L);
                                Performer.execute(application);
                                LOGGER.info("Запущена программа: " + application);
                            } 
                            catch (Exception e) { LOGGER.warning("Ошибка при запуске программы " + application + " с задержкой: " + e.getMessage()); }
                        });
                        thread.setDaemon(true);
                        thread.start();
                    }
                }
            }

            // Если есть голосовые ответы воспроизводим их сразу
            if (result.hasVoiceResponses()) 
            {
                try 
                {
                    Voiceover.playRandomFromList(result.getVoiceResponses());
                } 
                catch (Exception e) { LOGGER.warning("Ошибка при воспроизведении голосового ответа: " + e.getMessage()); }
            }
        } 
        catch (Exception e) { LOGGER.severe("Критическая ошибка в Distributor: " + e.getMessage()); }
    }

    private static String cleanInput(String input, Collection<String> words) 
    {
        if (input == null || words == null) 
            return input == null ? "" : input;
        
        String result = input;
        for (String word : words) 
            result = result.replace(word, "");

        return result.replaceAll("\\s+", " ").trim();
    }
}