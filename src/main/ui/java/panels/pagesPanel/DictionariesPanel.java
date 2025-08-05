package src.main.ui.java.panels.pagesPanel;

import src.main.ui.java.panels.tabPanel.dictionary.ModulesDictPanel;
import src.main.ui.java.panels.tabPanel.dictionary.DictionaryPanel;
import src.main.ui.java.panels.tabPanel.dictionary.VoiceOverDict;
import src.main.ui.java.components.UniversalTabPanel;

public class DictionariesPanel extends UniversalTabPanel
{
    public DictionariesPanel() {
        super();
        
        addTab("Основной словарь", new DictionaryPanel());
        addTab("Словарь модулей", new ModulesDictPanel());
        addTab("Словарь озвучки", new VoiceOverDict());
        
        setDefaultSelectedTab(0);
    }
}