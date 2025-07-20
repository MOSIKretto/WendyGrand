package main.Resources.UI.Panels;

import main.Resources.UI.Components.CustomTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.*;
import java.awt.*;


public class SettingsPanel extends JPanel 
{

    public SettingsPanel() 
    {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(0, 0, 0, 0));
        setBackground(new Color(50, 50, 50));
        
        JTabbedPane tabs = new JTabbedPane();
        tabs.setUI(new CustomTabbedPane());
        
        tabs.addTab("Окно", createWindowSettings());
        tabs.addTab("Программы", new AppsSettingsPanel());
        
        add(tabs, BorderLayout.CENTER);
    }

    private JPanel createWindowSettings() 
    {
        JPanel themePanel = new JPanel();
        themePanel.setLayout(new BoxLayout(themePanel, BoxLayout.Y_AXIS));
        themePanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        themePanel.setBackground(new Color(50, 50, 50));
        
        JLabel infoLabel = new JLabel("Смена темы в разработке");
        infoLabel.setForeground(new Color(225, 215, 198));
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel comingSoonLabel = new JLabel("Доступно в следующих версиях");
        comingSoonLabel.setForeground(new Color(180, 180, 180));
        comingSoonLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        themePanel.add(infoLabel);
        themePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        themePanel.add(comingSoonLabel);
        
        return themePanel;
    }
}