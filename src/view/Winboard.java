package view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Winboard extends JFrame {
    private final int WIDTH;
    private final int HEIGHT;
    private ChessGameFrame chessGameFrame;
    private ReplayFrame replayFrame;
    private static JLabel WinText;


//    //todo replay
//    static ReplayFrame replayFrame;


    public Winboard(int WIDGH, int HEIGHT, ChessGameFrame chessGameFrame) {
        this.WIDTH = WIDGH;
        this.HEIGHT = HEIGHT;
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setVisible(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.chessGameFrame = chessGameFrame;
        addLabel();
        addRestart();
        addBack();
        addExit();
        addReplay();
    }

    public void showWinboard(ArrayList<String[][]> gameData) {
        replayFrame = new ReplayFrame(720, 720, gameData);
        replayFrame.setVisible(false);
        chessGameFrame.dispose();
        ChessGameFrame.winboard.setAlwaysOnTop(true);
        ChessGameFrame.winboard.setVisible(true);
    }

    private void addRestart() {
        JButton button = new JButton("Restart");
        button.addActionListener(e -> restart());
        button.setLocation(WIDTH / 2 - 70, HEIGHT / 10 + 100);
        button.setSize(WIDTH / 5, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        setLayout(null);
        add(button);
    }

    public void addReplay() {
        JButton replayBtn = new JButton("Replay");
        replayBtn.addActionListener(e -> {
            chessGameFrame.dispose();
            replayFrame.setVisible(true);
        });
        replayBtn.setLocation(WIDTH / 2 - 70, HEIGHT / 10 + 50);
        replayBtn.setSize(WIDTH / 5, 30);
        replayBtn.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(replayBtn);
    }

    private void addLabel() {
        WinText = new JLabel("WHITE WIN");
        WinText.setLocation(WIDTH / 2 - 70, HEIGHT / 10);
        WinText.setSize(WIDTH / 5, 40);
        WinText.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(WinText);
    }

    public static void setWinText(String colorName) {
        WinText.setText(colorName + " Win!");
    }

    private void restart() {
        System.out.println("click restart");
        chessGameFrame.dispose();
        this.dispose();
        ChessGameFrame mainFrame = new ChessGameFrame(720, 720);
        mainFrame.setVisible(true);

//        if (chessGameFrame.getChessboard() != null) {
//            chessGameFrame.remove(chessGameFrame.getChessboard());
//        }
//        StepSaver.initiate();
//        chessGameFrame.addChessboard();
//        chessGameFrame.addBackground();
//        chessGameFrame.checkLabel.setVisible(false);
//        chessGameFrame.setStatusLabel(ChessColor.WHITE);
//        repaint();
//        Countdown.restart();
//        chessGameFrame.setVisible(true);
//        chessGameFrame.winboard.setVisible(false);
    }

    private void addBack() {
        JButton button = new JButton("Back");
        button.addActionListener(e -> {
            System.out.println("click back");
            this.dispose();
            chessGameFrame.dispose();
            StartMenuFrame Homepage = new StartMenuFrame(720, 720, true);
            Homepage.setVisible(true);
        });
        button.setLocation(WIDTH / 2 - 70, HEIGHT / 10 + 150);
        button.setSize(WIDTH / 5, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        setLayout(null);
        add(button);
    }

    private void addExit() {
        JButton button = new JButton("Exit");
        button.addActionListener(e -> {
            System.out.println("click exit");
            Runtime.getRuntime().halt(0);
        });
        button.setLocation(WIDTH / 2 - 70, HEIGHT / 10 + 200);
        button.setSize(WIDTH / 5, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        setLayout(null);
        add(button);
    }

}
