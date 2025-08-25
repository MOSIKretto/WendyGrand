package src.main.UI.main.panels.tabPanel.dictionary;

import src.main.UI.main.components.UniversalDictionaryPanel;
import src.main.UI.helpers.enums.ConstPaths;

public class VoiceOverDict extends UniversalDictionaryPanel 
{
    public VoiceOverDict() 
    {
        super(ConstPaths.VOICEOVER.getConfPath(), "Словарь озвучки");
    }
}