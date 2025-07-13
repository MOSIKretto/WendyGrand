package ui.panels;

// Готово

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.*;

public class MainPanel
{
    public static JPanel mainPanel()
    {

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) 
            {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            }
        };
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); 
        mainPanel.setBackground(Color.WHITE);

        GridBagConstraints gbcTop = new GridBagConstraints(); // Для ControllerPanel
        GridBagConstraints gbcNav = new GridBagConstraints(); // Для NavigationPanel
        GridBagConstraints gbcScr = new GridBagConstraints(); // Для ScreenPanel

        // ПАРАМЕТРЫ (НЕ ТРОГАТЬ)
        gbcTop.gridx = 0;
        gbcTop.gridy = 0;
        gbcTop.gridwidth = 2;
        gbcTop.fill = GridBagConstraints.HORIZONTAL;
        gbcTop.weightx = 1.0;
        gbcTop.ipady = 1; 

        gbcNav.gridx = 0;
        gbcNav.gridy = 1;
        gbcNav.weightx = 0.33;
        gbcNav.weighty = 1.0;
        gbcNav.fill = GridBagConstraints.BOTH;
        gbcNav.insets = new Insets(5, 0, 0, 5);

        gbcScr.gridx = 1;
        gbcScr.gridy = 1;
        gbcScr.weightx = 0.67;
        gbcScr.weighty = 1.0;
        gbcScr.fill = GridBagConstraints.BOTH;
        gbcScr.insets = new Insets(5, 0, 0, 0);

        mainPanel.add(ControllersPanel.createControllerPanel(), gbcTop);
        mainPanel.add(NavigationPanel.createNavigationPanel(), gbcNav);
        mainPanel.add(ScreenPanel.createScreenPanel(), gbcScr);

        return mainPanel;
    }
}