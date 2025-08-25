package src.main.UI.assistant.core;

import src.main.UI.assistant.dialogs.ErrorHandler;
import src.main.UI.main.core.WindowMaker;
import javax.swing.SwingUtilities;

public class GuiApplicationLauncher extends ApplicationLauncher 
{
    
    public GuiApplicationLauncher() 
    {
        super();
    }
    
    public GuiApplicationLauncher(ErrorHandler errorHandler) 
    {
        super(errorHandler);
    }
    
    @Override
    public void launch() {
        System.out.println("Starting Wendy UI Microservice...");
        setupGlobalExceptionHandling();
        
        SwingUtilities.invokeLater(() -> 
        {
            try {
                WindowMaker.startWindow();
                System.out.println("UI successfully started");
            } catch (Exception e) 
            {
                errorHandler.handleFatalError("Ошибка при запуске UI", e);
            }
        });
    }
    
    public static GuiApplicationLauncher create() 
    {
        return new GuiApplicationLauncher();
    }
    
    public static GuiApplicationLauncher createWithCustomHandler(ErrorHandler errorHandler) 
    {
        return new GuiApplicationLauncher(errorHandler);
    }
}