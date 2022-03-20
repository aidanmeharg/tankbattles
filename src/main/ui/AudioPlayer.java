package ui;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/*
 * Audio player to handle music and sound effects
 * Credit to Max O'Didily: How to Play, Loop, Pause and Stop Music in Java
 */

public class AudioPlayer {

    private Clip clip;

    // MODIFIES: this
    // EFFECTS: plays the given audio file (on loop if loop = true)
    public void playSound(String musicLocation, boolean loop) {
        try {
            File musicPath = new File(musicLocation);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                if (loop) {
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                }
            } else {
                System.out.println("Audio file could not be found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: stops this clip
    public void stopMusic() {
        try {
            clip.stop();
        } catch (Exception e) {
            System.out.println("No music was playing.");
        }
    }
}
