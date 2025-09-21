package src.main.UI.helpers.enums;


public enum ConstPaths 
{
    // ПУТИ

    // КОНФИГИ
    DICTIONARY("../WendyGrand/settings/configs/Dictionary.conf"),
    DICTIONARY_MODULES("../WendyGrand/settings/configs/DictionaryModules.conf"),
    VOICEOVER("../WendyGrand/settings/configs/Voiceover.conf"),
    HISTORY("../WendyGrand/settings/configs/History.conf"),
    APPS("../WendyGrand/settings/configs/Apps.conf"),

    MODULES_DIR("../WendyGrand/settings/modules/");
    
    // ОБРАБОТКА ВОЗВРАЩЕНИЧ ДАННЫХ
    private final String path;
    
    ConstPaths(String path) 
    {
        this.path = path;
    }
    
    public String getConfPath() 
    {
        return path;
    }
}