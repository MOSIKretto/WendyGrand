package src.main.UI.main.panels.pagesPanel;

import src.main.UI.main.panels.tabPanel.dictionary.ModulesDictPanel;
import src.main.UI.main.panels.tabPanel.dictionary.DictionaryPanel;
import src.main.UI.main.panels.tabPanel.dictionary.VoiceOverDict;
import src.main.UI.main.components.UniversalTabPanel;

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