package view;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundPlayer {
    private final String path;

    public SoundPlayer(String path) {
        this.path = path;
    }

    public void playMusic(String musicName) {
        try {
            File musicPath = new File(path + musicName);

            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                System.out.println("无法找到音乐路径");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}