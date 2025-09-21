package src.main.UI.main.panels.basePanel;

import src.main.UI.main.panels.pagesPanel.DictionariesPanel;
import src.main.UI.main.panels.pagesPanel.SettingsPanel;
import src.main.UI.main.panels.pagesPanel.HistoryPanel;
import src.main.UI.main.panels.pagesPanel.ModulesPanel;
import src.main.UI.main.panels.pagesPanel.WendyPanel;
import src.main.UI.main.core.WindowMaker;

import java.awt.CardLayout;
import javax.swing.JPanel;
import java.awt.Color;


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