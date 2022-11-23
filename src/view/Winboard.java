package view;

import javax.swing.*;
import java.awt.*;

public class Winboard extends JFrame{
    private static int WIDTH;
    private static int HEIGHT;
    private ChessGameFrame chessGameFrame;
    static JLabel WinText;
    static JButton replayBtn;


//    //todo replay
//    static ReplayFrame replayFrame;


    public Winboard(int WIDGH, int HEIGHT, ChessGameFrame chessGameFrame) {
        this.WIDTH = WIDGH;
        this.HEIGHT = HEIGHT;
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setVisible(false);
//        replayFrame=new ReplayFrame(1000,700);
//        replayFrame.setVisible(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.chessGameFrame = chessGameFrame;
        addLabel();
        addRestart();
        addExit();
        addReplay();
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
        replayBtn = new JButton("Replay");
        replayBtn.addActionListener(e -> {
//            replayFrame.setVisible(true);
        });
        replayBtn.setLocation(WIDTH / 2 - 70, HEIGHT / 10 + 50);
        replayBtn.setSize(WIDTH / 5, 30);
        replayBtn.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(replayBtn);
    }
    public static void setReplayList(){
//        replayFrame.setReplayList();
    }

    private void restart() {
        chessGameFrame.dispose();
        this.dispose();
        ChessGameFrame mainFrame = new ChessGameFrame(720, 720);
        System.out.println("click restart");
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

    private void addLabel() {
        WinText = new JLabel("WHITE WIN");
        WinText.setLocation(WIDTH / 2 - 70, HEIGHT / 10 );
        WinText.setSize(WIDTH / 5, 40);
        WinText.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(WinText);
    }

//    public static void setWinText(ChessColor color) {
//        if (color == ChessColor.WHITE) {
//            WinText.setText("Black Win");
//        } else if (color == ChessColor.BLACK) {
//            WinText.setText("White Win");
//        }
//    }

    private void addExit() {
        JButton button = new JButton("Exit");
        button.addActionListener(e -> Runtime.getRuntime().halt(0));
        button.setLocation(WIDTH / 2 - 70, HEIGHT / 10 + 150);
        button.setSize(WIDTH / 5, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        setLayout(null);
        add(button);
    }

}
