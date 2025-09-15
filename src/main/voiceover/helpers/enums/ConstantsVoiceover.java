package src.main.voiceover.helpers.enums;


public enum ConstantsVoiceover
{
    // ПУТИ
    DIRECTORY_SOUNDS_PATH("../WendyGrand/settings/audio/"),
    
    // КОНФИГИ
    VOICEOVER_CONF("../WendyGrand/settings/configs/Voiceover.conf");


    // ОБРАБОТКА ВОЗВРАТА ДАННЫХ
    private final String path;
    
    ConstantsVoiceover(String path) 
    {
        this.path = path;
    }
    
    public String getConfPath() 
    {
        return path;
    }
}