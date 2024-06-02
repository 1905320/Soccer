import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundPlayer {
   private Clip clip;
   
    public void playSound(String filePath) {
        try {
            // Load the sound file
            File soundFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            
            // Get a clip resource
            Clip clip = AudioSystem.getClip();
            
            // Open the audio stream
            clip.open(audioStream);
            
            // Start playing the clip
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}

