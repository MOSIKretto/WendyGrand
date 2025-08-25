package src.main.UI.main.panels.tabPanel.dictionary;

import src.main.UI.main.components.UniversalDictionaryPanel;
import src.main.UI.helpers.enums.ConstPaths;

public class DictionaryPanel extends UniversalDictionaryPanel
{
    public DictionaryPanel()
    {
        super(ConstPaths.DICTIONARY.getConfPath(), "Основной словарь");
    }
}