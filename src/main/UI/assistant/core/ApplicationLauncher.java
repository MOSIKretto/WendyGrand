package src.main.UI.assistant.core;

import src.main.UI.assistant.dialogs.SwingErrorHandler;
import src.main.UI.assistant.dialogs.ErrorHandler;

public abstract class ApplicationLauncher 
{
    protected final ErrorHandler errorHandler;
    
    protected ApplicationLauncher() 
    {
        this.errorHandler = new SwingErrorHandler();
    }
    
    protected ApplicationLauncher(ErrorHandler errorHandler) 
    {
        this.errorHandler = errorHandler;
    }
    
    public abstract void launch();
    
    protected void setupGlobalExceptionHandling() 
    {
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> 
        {
            errorHandler.handleFatalError("Критическая ошибка в потоке " + thread.getName(), throwable);
        });
    }
    
    public ErrorHandler getErrorHandler() 
    {
        return errorHandler;
    }
}