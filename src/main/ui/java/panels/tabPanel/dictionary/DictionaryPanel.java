package src.main.ui.java.panels.tabPanel.dictionary;

import src.main.ui.java.components.UniversalDictionaryPanel;
import src.main.helpers.java.enums.ConstPaths;

public class DictionaryPanel extends UniversalDictionaryPanel
{
    public DictionaryPanel()
    {
        super(ConstPaths.DICTIONARY.getConfPath(), "Основной словарь");
    }
}