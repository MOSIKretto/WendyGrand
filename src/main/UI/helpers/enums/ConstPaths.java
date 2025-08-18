package src.main.UI.helpers.enums;


public enum ConstPaths 
{
    // ПУТИ

    // Для основной логики
    DICTIONARY("../WendyGrand/configs/mainConfigs/Dictionary.conf"),
    APPS("../WendyGrand/configs/mainConfigs/Apps.conf"),
    VOICEOVER("../WendyGrand/configs/mainConfigs/Voiceover.conf"),

    // Для модулей
    DIRECTORY_MODULES("../WendyGrand/modules/"),
    DICTIONARY_MODULES("../WendyGrand/configs/modulesConfigs/DictionaryModules.conf"),
    MODULES("../WendyGrand/configs/modulesConfigs/Modules.conf"),
    VOICEOVER_MODULES("../WendyGrand/configs/modulesConfigs/DictionaryModules.conf"),

    // Для озвучки
    SOUNDS("../WendyGrand/main/Resources/Audio/new/");

    
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