import java.util.concurrent.CountDownLatch;
import javax.sound.sampled.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Random;
import java.util.List;
import java.util.Map;
import java.io.File;

public class Voiceover_prototype_
{
    static Random random = new Random();

    public static void main(String[] args) throws 
    UnsupportedAudioFileException, 
    LineUnavailableException, 
    InterruptedException, 
    IOException 
    {
        List<List<String>> sounds = soundDictionary.get("HelloVoiceover");
        List<String> soundList = sounds.get(random.nextInt(sounds.size()));
        CountDownLatch latch = new CountDownLatch(soundList.size());

        playSound(soundList, 0, latch);

        latch.await();
    }

    private static final Map<String, List<List<String>>> soundDictionary = new HashMap<>() {
        {
            put("HelloVoiceover", Arrays.asList(
                Arrays.asList("п", "рь", "и", "вь", "э", "т"),
                Arrays.asList("т", "рь", "э", "п")
            ));

            put("ByeVoiceover", Arrays.asList(
                Arrays.asList("ByeBye.mp3", "GladToHelp.mp3"),
                Arrays.asList("Goodbye.mp3", "SeeYouLater.mp3")
            ));

            put("StandardModule_StandardResponse", Arrays.asList(
                Arrays.asList("OneMoment.mp3", "Doing.mp3"),
                Arrays.asList("Done.mp3", "AlwaysAPleasure.mp3")
            ));
        }
    };

    private static void playSound(List<String> soundFiles, int index, CountDownLatch latch) throws 
    UnsupportedAudioFileException, 
    LineUnavailableException, 
    IOException 
    {
        String soundFile = soundFiles.get(index);
        Clip clip = AudioSystem.getClip();

        clip.open(
            AudioSystem.getAudioInputStream(
                new File("../WendyGrand/Audio/new/" + soundFile + ".wav")
            )
        );

        clip.addLineListener(event -> {
            if (event.getType() == LineEvent.Type.STOP) 
            {
                clip.close();
                latch.countDown();

                try {
                    playSound(soundFiles, index + 1, latch);} 
                catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                    e.printStackTrace();}
            }
        });

        clip.start();
    }
}