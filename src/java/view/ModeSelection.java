package view;

import javax.swing.*;
import java.awt.*;

import static view.StartMenuFrame.icon;

public class ModeSelection extends JFrame {
    private final int WIDTH;
    private final int HEIGHT;
    private final JFrame frame;
    public static OnlineWaitFrame onlineWaitFrame;

    public ModeSelection(int width, int height, JFrame frame) {
        setTitle("DarkChess");
        this.WIDTH = width;
        this.HEIGHT = height;
        this.frame = frame;

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        setIconImage(icon);

        addLabel();

        addOnlineButton();
        addP2PButton();
        addP2CButton();
        addC2CButton();
        addBackButton();
    }

    private void addLabel() {
        JLabel label = new JLabel("Mode Selection");
        label.setLocation(WIDTH / 2 - 150, HEIGHT / 5);
        label.setSize(320, 40);
        label.setFont(new Font("Rockwell", Font.BOLD, 40));
        label.setForeground(Color.BLACK);
        label.setVisible(true);
        add(label);
    }

    private void addOnlineButton() {
        JButton button = new JButton("Online");
        button.setLocation(WIDTH / 2 - 90, HEIGHT * 3 / 10);
        button.setSize(180, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener((e) -> {
//            this.setVisible(false);
            onlineWaitFrame = new OnlineWaitFrame(this);
            onlineWaitFrame.setVisible(true);
        });
    }

    private void addP2PButton() {
        JButton button = new JButton("P2P");
        button.setLocation(WIDTH / 2 - 90, HEIGHT * 4 / 10);
        button.setSize(180, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener((e) -> {
            this.setVisible(false);
            DifficultySelection difficultySelection = new DifficultySelection(720, 720, 0, this);
            difficultySelection.setVisible(true);
        });

    }

    private void addP2CButton() {
        JButton button = new JButton("P2C");
        button.setLocation(WIDTH / 2 - 90, HEIGHT / 2);
        button.setSize(180, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener((e) -> {
            this.setVisible(false);
            DifficultySelection difficultySelection = new DifficultySelection(720, 720, 1, this);
            difficultySelection.setVisible(true);
        });

    }

    private void addC2CButton() {
        JButton button = new JButton("C2C");
        button.setLocation(WIDTH / 2 - 90, HEIGHT * 3 / 5);
        button.setSize(180, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener((e) -> {

            this.setVisible(false);
            DifficultySelection difficultySelection = new DifficultySelection(720, 720, 2, this);
            difficultySelection.setVisible(true);


//            mainFrame = new ChessGameFrame(720, 720, 2,0,0,0,0);
//            mainFrame.setVisible(true);
//            frame.dispose();
//            this.dispose();
        });
    }

    private void addBackButton() {
        JButton button = new JButton("Back");
        button.setLocation(WIDTH / 2 - 90, HEIGHT * 7 / 10);
        button.setSize(180, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener((e) -> {
            this.dispose();
            frame.setVisible(true);
        });
    }
}
