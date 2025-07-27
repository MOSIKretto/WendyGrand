package main.Resources.enums;


public enum ConstPaths 
{
    // ПУТИ

    // Для основной логики
    DICTIONARY("../WendyGrand/Configs/Logics/Dictionary.conf"),
    APPS("../WendyGrand/Configs/Logics/Apps.conf"),
    VOICEOVER("../WendyGrand/Configs/Logics/Voiceover.conf"),

    // Для модулей
    DICTIONARY_MODULES("../WendyGrand/Configs/Logics/forModules/DictionaryModules.conf"),
    MODULES("../WendyGrand/Configs/Logics/forModules/Modules.conf"),
    VOICEOVER_MODULES("../WendyGrand/Configs/Logics/forModules/DictionaryModules.conf"),

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