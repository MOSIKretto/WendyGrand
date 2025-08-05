package src.main.ui.java.panels.tabPanel.dictionary;

import src.main.ui.java.components.UniversalDictionaryPanel;
import src.main.helpers.java.enums.ConstPaths;

public class ModulesDictPanel extends UniversalDictionaryPanel 
{
    public ModulesDictPanel() 
    {
        super(ConstPaths.DICTIONARY_MODULES.getConfPath(), "Словарь модулей");
    }
}