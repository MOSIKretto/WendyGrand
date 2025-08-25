package src.main.UI.assistant.dialogs;

public interface ErrorHandler 
{
    void handleFatalError(String message, Throwable throwable);
    void showWarning(String title, String message);
    void showInfo(String title, String message);
    void showConfirm(String title, String message, ConfirmCallback callback);
    
    interface ConfirmCallback 
    {
        void onResult(boolean confirmed);
    }
}
