package src.main.UI;

import src.main.UI.assistant.core.GuiApplicationLauncher;

public class GuiStarter 
{
    public static void main(String[] args) 
    {
        try 
        {
            GuiApplicationLauncher.create().launch();
        } catch (Exception e) 
        {
            System.err.println("Критическая ошибка инициализации: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}