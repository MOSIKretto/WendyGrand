package src.main.UI.assistant.dialogs;


import java.util.logging.Logger;

public class LoggingErrorHandler implements ErrorHandler 
{
    private static final Logger logger = Logger.getLogger(LoggingErrorHandler.class.getName());
    private final ErrorHandler fallbackHandler;
    
    public LoggingErrorHandler() 
    {
        this.fallbackHandler = new SwingErrorHandler();
    }
    
    public LoggingErrorHandler(ErrorHandler fallbackHandler) 
    {
        this.fallbackHandler = fallbackHandler;
    }
    
    @Override
    public void handleFatalError(String message, Throwable throwable) 
    {
        logger.severe(message + ": " + throwable.getMessage());
        fallbackHandler.handleFatalError(message, throwable);
    }
    
    @Override
    public void showWarning(String title, String message) 
    {
        logger.warning(title + ": " + message);
        fallbackHandler.showWarning(title, message);
    }
    
    @Override
    public void showInfo(String title, String message) 
    {
        logger.info(title + ": " + message);
        fallbackHandler.showInfo(title, message);
    }
    
    @Override
    public void showConfirm(String title, String message, ConfirmCallback callback) 
    {
        logger.info("Запрос подтверждения: " + title + " - " + message);
        fallbackHandler.showConfirm(title, message, callback);
    }
}