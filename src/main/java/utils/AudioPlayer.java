package utils;

import javax.sound.sampled.*;
import java.io.IOException;

/**
 * @author Daniel Bedrich
 */
public class AudioPlayer {
    public static synchronized void play(String soundFilePath) {
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                        AudioPlayer.class.getResource("/assets/sounds/" + soundFilePath)
                );
                clip.open(inputStream);
                clip.start();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            } catch (LineUnavailableException e) {
                System.err.println(e.getMessage());
            } catch (UnsupportedAudioFileException e) {
                System.err.println(e.getMessage());
            }
        }).start();
    }
}
