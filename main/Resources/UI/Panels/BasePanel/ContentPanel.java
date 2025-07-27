package main.Resources.UI.Panels.BasePanel;

import main.Resources.UI.Panels.PagesPanel.DictionariesPanel;
import main.Resources.UI.Panels.PagesPanel.SettingsPanel;
import main.Resources.UI.Panels.PagesPanel.HistoryPanel;
import main.Resources.UI.Panels.PagesPanel.ModulesPanel;
import main.Resources.UI.Panels.PagesPanel.WendyPanel;
import main.Resources.UI.WindowMaker;
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