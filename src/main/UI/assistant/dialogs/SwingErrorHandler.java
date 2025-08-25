package src.main.UI.assistant.dialogs;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class SwingErrorHandler implements ErrorHandler 
{
    
    @Override
    public void handleFatalError(String message, Throwable throwable) 
    {
        System.err.println(message + ": " + throwable.getMessage());
        throwable.printStackTrace();
        
        SwingUtilities.invokeLater(() -> {
            String errorMessage = message + ":\n\n" + 
                throwable.getMessage() + "\n\n" +
                "Подробности см. в логах приложения.";
            
            JOptionPane.showMessageDialog(
                null,
                errorMessage,
                "Ошибка приложения",
                JOptionPane.ERROR_MESSAGE
            );
            
            System.exit(1);
        });
    }
    
    @Override
    public void showWarning(String title, String message) 
    {
        SwingUtilities.invokeLater(() -> 
        {
            JOptionPane.showMessageDialog(
                null,
                message,
                title,
                JOptionPane.WARNING_MESSAGE
            );
        });
    }
    
    @Override
    public void showInfo(String title, String message) 
    {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(
                null,
                message,
                title,
                JOptionPane.INFORMATION_MESSAGE
            );
        });
    }
    
    @Override
    public void showConfirm(String title, String message, ConfirmCallback callback) 
    {
        SwingUtilities.invokeLater(() -> {
            int response = JOptionPane.showConfirmDialog(
                null,
                message,
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            callback.onResult(response == JOptionPane.YES_OPTION);
        });
    }
}