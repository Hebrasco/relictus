package utils;

import javax.sound.sampled.*;
import java.io.IOException;

public class AudioPlayer {
    private static AudioPlayer ourInstance = new AudioPlayer();

    public static AudioPlayer getInstance() {
        return ourInstance;
    }

    private AudioPlayer() {
    }

    public synchronized void play(String soundFilePath) {
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                        getClass().getResource("/assets/sounds/" + soundFilePath)
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
