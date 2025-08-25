package src.main.UI.main.panels.tabPanel.dictionary;

import src.main.UI.main.components.UniversalDictionaryPanel;
import src.main.UI.helpers.enums.ConstPaths;

public class ModulesDictPanel extends UniversalDictionaryPanel 
{
    public ModulesDictPanel() 
    {
        super(ConstPaths.DICTIONARY_MODULES.getConfPath(), "Словарь модулей");
    }
}