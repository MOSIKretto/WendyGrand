package main.Resources.UI.Panels;

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
        add(new ModulesPanel(), "2");
        add(new DictionaryPanel(), "3");
        add(new ModulesDictPanel(), "4");
        add(new VoiceOverDict(), "5");
        add(new HistoryPanel(), "6");
    }
}