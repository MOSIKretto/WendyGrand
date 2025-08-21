package src.main.logic.helpers.enums;


public enum ConstPaths 
{
    // ПУТИ
    DIRECTORY_MODULES_PATH("../WendyGrand/modules/"),
    DICTIONARY_SOUNDS_PATH("../WendyGrand/main/Resources/Audio/new/"), // Пока не используется
    
    // КОНФИГИ
    DICTIONARY_CONF("../WendyGrand/configs/mainConfigs/Dictionary.conf"),
    APPS_CONF("../WendyGrand/configs/mainConfigs/Apps.conf"),
    VOICEOVER_CONF("../WendyGrand/configs/mainConfigs/Voiceover.conf"),
    DICTIONARY_MODULES_CONF("../WendyGrand/configs/modulesConfigs/DictionaryModules.conf"),
    MODULES_CONF("../WendyGrand/configs/modulesConfigs/Modules.conf"),
    VOICEOVER_MODULES_CONF("../WendyGrand/configs/modulesConfigs/DictionaryModules.conf"),

    // VOICEOVER
    VOICEOVER("../WendyGrand/src/main/voiceover/main/Voiceover.py"),
    VOICEOVERVENV("../WendyGrand/src/main/voiceover/resources/venv/bin/python");


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