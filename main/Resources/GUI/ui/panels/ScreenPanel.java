package ui.panels;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import utils.ColorPalette;
import java.awt.Color;

public class ScreenPanel extends JPanel
{
    public static JPanel createScreenPanel()
    {
        JPanel panel = new JPanel();
        panel.setBackground(ColorPalette.BACKGROUND);
        panel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        return panel;
    }
}
