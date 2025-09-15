package src.main.logic.helpers.enums;


public enum ConstantsLogic
{
    // ПУТИ
    DIRECTORY_MODULES_PATH("../WendyGrand/settings/modules/"),
    
    // КОНФИГИ
    DICTIONARY_CONF("../WendyGrand/settings/configs/Dictionary.conf"),
    APPS_CONF("../WendyGrand/settings/configs/Apps.conf"),
    DICTIONARY_MODULES_CONF("../WendyGrand/settings/configs/DictionaryModules.conf");


    // ОБРАБОТКА ВОЗВРАТА ДАННЫХ
    private final String path;
    
    ConstantsLogic(String path) 
    {
        this.path = path;
    }
    
    public String getConfPath() 
    {
        return path;
    }
}