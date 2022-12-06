package view;

import javax.swing.*;
import java.awt.*;

import static view.StartMenuFrame.icon;
import static view.StartMenuFrame.mainFrame;

public class ModeSelection extends JFrame{
    private final int WIDTH;
    private final int HEIGHT;

    public ModeSelection (int width, int height) {
        setTitle("DarkChess");
        this.WIDTH = width;
        this.HEIGHT = height;

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        setIconImage(icon);

        addP2PButton();
        addP2CButton();
        addC2CButton();
    }

    private void addP2PButton() {
        JButton button = new JButton("P2P");
        button.setLocation(WIDTH/2 - 90, HEIGHT * 2 / 5);
        button.setSize(180, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener((e) -> {
            mainFrame = new ChessGameFrame(720, 720, 0);
            mainFrame.setVisible(true);
            this.dispose();
        });

    }

    private void addP2CButton() {
        JButton button = new JButton("P2C");
        button.setLocation(WIDTH/2 -90, HEIGHT / 2);
        button.setSize(180, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener((e) -> {
            mainFrame = new ChessGameFrame(720, 720, 1);
            mainFrame.setVisible(true);
            this.dispose();
        });

    }

    private void addC2CButton() {
        JButton button = new JButton("C2C");
        button.setLocation(WIDTH/2 -90, HEIGHT * 3 / 5);
        button.setSize(180, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener((e) -> {
            mainFrame = new ChessGameFrame(720, 720, 2);
            mainFrame.setVisible(true);
            this.dispose();
        });
    }
}
