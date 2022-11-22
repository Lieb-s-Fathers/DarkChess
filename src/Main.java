import view.StartMenuFrame;

import javax.swing.*;

public class Main {


    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            ChessGameFrame mainFrame = new ChessGameFrame(720, 720);
//            mainFrame.setVisible(true);
//        });

        SwingUtilities.invokeLater(() -> {
            StartMenuFrame firstFrame = new StartMenuFrame(720, 720, true);
            firstFrame.setVisible(true);
        });
    }
}