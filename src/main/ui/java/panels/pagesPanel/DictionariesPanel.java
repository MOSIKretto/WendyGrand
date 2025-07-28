package src.main.ui.java.panels.pagesPanel;

import src.main.ui.java.panels.tabPanel.dictionary.ModulesDictPanel;
import src.main.ui.java.panels.tabPanel.dictionary.DictionaryPanel;
import src.main.ui.java.panels.tabPanel.dictionary.VoiceOverDict;
import src.main.ui.java.components.CustomTabbedPane;

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