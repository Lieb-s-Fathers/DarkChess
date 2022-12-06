package view;

import model.GameData;

import javax.swing.*;
import java.awt.*;

public class Winboard extends JFrame {
    private final int WIDTH;
    private final int HEIGHT;
    private ChessGameFrame chessGameFrame;
    private ReplayFrame replayFrame;
    private static JLabel WinText;

//    //todo replay
//    static ReplayFrame replayFrame;


    public Winboard(int WIDGH, int HEIGHT, ChessGameFrame chessGameFrame) {
        setIconImage(StartMenuFrame.icon);

        this.WIDTH = WIDGH;
        this.HEIGHT = HEIGHT;
        setSize(WIDTH, HEIGHT);
        setAlwaysOnTop(true);
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

    public void showWinboard(GameData gameData) {
        replayFrame = new ReplayFrame(720, 720, gameData, this);
        replayFrame.setVisible(false);
        chessGameFrame.setVisible(false);
        setVisible(true);
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
            setVisible(false);
            chessGameFrame.setVisible(false);
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
//        ChessGameFrame mainFrame = new ChessGameFrame(720, 720);
//        mainFrame.setVisible(true);
        ModeSelection modeSelection = new ModeSelection(720, 720, this);
        modeSelection.setVisible(true);
    }

    private void addBack() {
        JButton button = new JButton("Back");
        button.addActionListener(e -> {
            System.out.println("click back");
            this.dispose();
            chessGameFrame.dispose();
            StartMenuFrame Homepage = new StartMenuFrame(1123, 767, true);
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
