package ui.panels;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import ui.components.CustomButton;
import utils.ColorPalette;
import utils.UIStyler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

public class NavigationPanel extends JPanel
{
    public static JPanel createNavigationPanel()
    {
        JPanel panel = new JPanel();
        panel.setBackground(ColorPalette.MENU_BACKGROUND);
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel chatButtonPanel = new JPanel(new BorderLayout());
        JPanel settingsButtonPanel = new JPanel(new BorderLayout());
        JPanel dictionaryButtonPanel = new JPanel(new BorderLayout());
        JPanel moduleButtonPanel = new JPanel(new BorderLayout());
        JPanel exitButtonPanel = new JPanel(new BorderLayout());
        JPanel emptyPanel = new JPanel();

        emptyPanel.setBackground(ColorPalette.MENU_BACKGROUND);

        chatButtonPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 10));
        settingsButtonPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 10));
        dictionaryButtonPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 10));
        moduleButtonPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 10));
        exitButtonPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 10));
        emptyPanel.setPreferredSize(new Dimension(1, 250));

        CustomButton chatButton = UIStyler.createMenuButton("Chat", panel.getWidth(), "Обычная");
        CustomButton settingsButton = UIStyler.createMenuButton("Settings", panel.getWidth(),"Обычная");
        CustomButton dictionatyButton = UIStyler.createMenuButton("Main Dict", panel.getWidth(), "Обычная");
        CustomButton modulesButton = UIStyler.createMenuButton("Modul's Dict", panel.getWidth(), "Обычная"); 
        CustomButton exitButton = UIStyler.createMenuButton("EXIT", panel.getWidth(), "exit");

        chatButtonPanel.add(chatButton, BorderLayout.CENTER);
        settingsButtonPanel.add(settingsButton, BorderLayout.CENTER);
        dictionaryButtonPanel.add(dictionatyButton, BorderLayout.CENTER);
        moduleButtonPanel.add(modulesButton, BorderLayout.CENTER);
        exitButtonPanel.add(exitButton, BorderLayout.CENTER);
        

        panel.add(chatButtonPanel);
        panel.add(settingsButtonPanel);
        panel.add(dictionaryButtonPanel);
        panel.add(moduleButtonPanel);
        panel.add(exitButtonPanel);
        panel.add(emptyPanel);

        return panel;
    }
}
