package src.main.ui.java.panels.tabPanel.settings;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

public class WindowSettingsPanel extends JPanel
{
    public WindowSettingsPanel()
    {
        initUi();
    }

    private void initUi()
    {
        setBackground(new Color(50, 50, 50));

        JPanel themePanel = new JPanel();
        themePanel.setLayout(new BoxLayout(themePanel, BoxLayout.Y_AXIS));
        themePanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        themePanel.setBackground(new Color(50, 50, 50));
        
        JLabel infoLabel = new JLabel("Смена темы в разработке");
        infoLabel.setFont(new Font("Courier", Font.BOLD, 32));
        infoLabel.setForeground(new Color(225, 215, 198));
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel comingSoonLabel = new JLabel("Доступно в следующих версиях");
        comingSoonLabel.setForeground(new Color(180, 180, 180));
        comingSoonLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        themePanel.add(infoLabel);
        themePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        themePanel.add(comingSoonLabel);

        add(themePanel);
    }
}
