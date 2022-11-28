import view.SoundPlayer;
import view.StartMenuFrame;

import javax.swing.*;

public class Main {

    public static String inPath = "save/";

    public static void main(String[] args) {


//        SwingUtilities.invokeLater(() -> {
//            ChessGameFrame mainFrame = new ChessGameFrame(720, 720);
//            mainFrame.setVisible(true);
//        });


        //todo 写一个容器 包含所有窗口
        SwingUtilities.invokeLater(() -> {
            SoundPlayer audioPlayer = new SoundPlayer("src/music/");
            StartMenuFrame Homepage = new StartMenuFrame(720, 720, true);
            Homepage.setVisible(true);
            //audioPlayer.playMusic("小旭音乐 - 斗地主-大厅1.wav");

//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
            //audioPlayer.playMusic("小八斗 - 欢乐斗地主 (原版一).wav");
        });
    }
}