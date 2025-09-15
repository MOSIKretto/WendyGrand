package src.main.voiceover.main;

import src.main.voiceover.helpers.ConfigReaderVoiceover;
import src.main.voiceover.helpers.enums.ConstantsVoiceover;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.AudioInputStream;
import java.util.concurrent.CountDownLatch;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.IOException;
import java.util.Random;
import java.util.List;
import java.io.File;


public class Voiceover
{

    public static void startVoice(String key) throws
    IOException
    {
        List<String> options = ConfigReaderVoiceover.readConfig(ConstantsVoiceover.VOICEOVER_CONF.getConfPath(), key);
        
        Random random = new Random();
        int randomIndex = random.nextInt(options.size());
        String sound = options.get(randomIndex);
        File audioFile = new File(ConstantsVoiceover.DIRECTORY_SOUNDS_PATH.getConfPath() + sound + ".wav");

        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile)) 
        {
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            
            CountDownLatch soundLatch = new CountDownLatch(1);
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP)
                {
                    clip.close();
                    soundLatch.countDown();
                }
            });
            
            clip.start();
            soundLatch.await();
        } 
        catch (Exception e)
        {
            System.err.println("Ошибка воспроизведения звука: " + sound);
            e.printStackTrace();
        }
    }
}