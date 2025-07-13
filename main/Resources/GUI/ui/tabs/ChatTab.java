package ui.tabs;

import utils.ColorPalette;
import javax.swing.*;
import java.awt.*;

public class ChatTab extends JPanel 
{
    public ChatTab() {
        setBackground(ColorPalette.TAB_BACKGROUND);
        setLayout(new BorderLayout());
        
        // Здесь ваш чат-интерфейс
        add(new JLabel("Чат-интерфейс", SwingConstants.CENTER));
    }
}