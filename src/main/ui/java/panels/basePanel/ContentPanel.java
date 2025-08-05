package src.main.ui.java.panels.basePanel;

import src.main.ui.java.panels.pagesPanel.DictionariesPanel;
import src.main.ui.java.panels.pagesPanel.SettingsPanel;
import src.main.ui.java.panels.pagesPanel.HistoryPanel;
import src.main.ui.java.panels.pagesPanel.ModulesPanel;
import src.main.ui.java.panels.pagesPanel.WendyPanel;
import src.main.ui.java.WindowMaker;

import javax.swing.*;
import java.awt.*;

public class ContentPanel extends JPanel 
{
    public ContentPanel(WindowMaker frame) 
    {
        super(new CardLayout());
        setBackground(new Color(50, 50, 50));
        
        add(new WendyPanel(), "0");
        add(new SettingsPanel(), "1");
        add(new ModulesPanel(frame), "2");
        add(new DictionariesPanel(), "3");
        add(new HistoryPanel(), "4");
    }
}