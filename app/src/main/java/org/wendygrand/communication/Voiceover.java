package org.wendygrand.communication;

import javax.sound.sampled.*;
import java.io.File;
import java.util.List;
import java.util.Random;


public class Voiceover 
{
    public static void playRandomFromList(List<String> soundOptions) 
    {
        if (soundOptions == null || soundOptions.isEmpty()) 
            return;
        
        String sound = soundOptions.get(new Random().nextInt(soundOptions.size())).replace(".wav", "");
        String fullPath = org.wendygrand.enums.Constants.DIRECTORY_SOUNDS_PATH.getConfPath() + sound + ".wav";
        
        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(fullPath))) 
        {
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            Thread.sleep(clip.getMicrosecondLength() / 1000);
            clip.close();
        } 
        catch (Exception e) { System.err.println("Ошибка воспроизведения: " + sound); }
    }
}