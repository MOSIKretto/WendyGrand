package main.Resources.UI.Panels.PagesPanel;

import main.Resources.UI.Panels.TabPanel.Dictionary.ModulesDictPanel;
import main.Resources.UI.Panels.TabPanel.Dictionary.DictionaryPanel;
import main.Resources.UI.Panels.TabPanel.Dictionary.VoiceOverDict;
import main.Resources.UI.Components.CustomTabbedPane;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Color;

public class DictionariesPanel extends JPanel 
{
    private CustomTabbedPane tabs;

    public DictionariesPanel() 
    {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(0, 0, 0, 0));
        setBackground(new Color(50, 50, 50));

        tabs = new CustomTabbedPane();

        tabs.addTab("Основной словарь", new DictionaryPanel());
        tabs.addTab("Словарь модулей", new ModulesDictPanel());
        tabs.addTab("Словарь озвучки", new VoiceOverDict());

        if (tabs.getTabCount() > 0) 
        {
            tabs.setSelectedIndex(0);
        }

        add(tabs, BorderLayout.CENTER);
    }

    public CustomTabbedPane getTabs() 
    {
        return tabs;
    }
}