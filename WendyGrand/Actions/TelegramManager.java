package Actions;

import java.io.IOException;

public class TelegramManager
{
    public static void startTelegram()
    {
        String[] Telegram = {"telegram-desktop"};
        String os = System.getProperty("os.name").toLowerCase();
        Runtime runtime = Runtime.getRuntime();
    
        if (os.contains("nux")) 
        {
            try 
            {
                runtime.exec(Telegram);
            } 
            catch (IOException ignored) 
            {
            }
        }
    }
}
