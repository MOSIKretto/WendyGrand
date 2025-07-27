package main.Java.Handlers;

import javax.sound.sampled.AudioInputStream;
import java.util.concurrent.CountDownLatch;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineEvent;
import main.Resources.GeneralHelper;
import main.Resources.enums.ConstPaths;

import javax.sound.sampled.Clip;
import java.io.IOException;
import java.util.Random;
import java.util.List;
import java.io.File;


public class VoiceoverHandler
{

    private static final String CONFIG = ConstPaths.VOICEOVER.getConfPath();
    private static final String SOUNDS = ConstPaths.SOUNDS.getConfPath();

    public static void voiceover(String key) throws
    IOException
    {
        List<String> options = GeneralHelper.readConfig(CONFIG, key);
        
        Random random = new Random();
        int randomIndex = random.nextInt(options.size());
        String chosenOption = options.get(randomIndex);
        String[] phonemes = chosenOption.split("-");
        
        for (String phoneme : phonemes) 
        {
            String sound = phoneme.trim().replace("\"", "");

            File audioFile = new File(SOUNDS + sound + ".wav");

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

    public static void main(String[] args) throws 
    IOException
    {
        voiceover("hello"); //Пример
    }
}