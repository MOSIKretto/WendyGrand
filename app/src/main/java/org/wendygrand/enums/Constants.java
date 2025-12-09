package org.wendygrand.enums;


public enum Constants 
{
    DIRECTORY_MODULES_PATH("~/.config/WendyGrand/modules/"),
    DIRECTORY_SOUNDS_PATH("~/.config/WendyGrand/audio/"),
    DICTIONARY_CONF("~/.config/WendyGrand/configs/wgc.conf");

    private final String path;
    
    Constants(String path) 
    {
        this.path = path;
    }
    
    public String getConfPath() 
    {
        return path.startsWith("~") ? path.replaceFirst("^~", System.getProperty("user.home")) : path;
    }
}