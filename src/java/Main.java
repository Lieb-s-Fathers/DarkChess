import view.SoundPlayer;
import view.StartMenuFrame;

import javax.swing.*;

public class Main {

    public static String inPath = "save/";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SoundPlayer audioPlayer = new SoundPlayer("src/resources/music/");
            StartMenuFrame Homepage = new StartMenuFrame(1123, 767, true);
            Homepage.setVisible(true);
//            audioPlayer.playMusic("小旭音乐 - 斗地主-大厅1.wav");
//            audioPlayer.playMusic("小八斗 - 欢乐斗地主 (原版一).wav");
        });
    }
}