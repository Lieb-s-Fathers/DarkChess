package view;

import javax.swing.*;
import java.awt.*;

import static view.StartMenuFrame.icon;
import static view.StartMenuFrame.mainFrame;

public class DifficultySelection extends JFrame {
    private final int WIDTH;
    private final int HEIGHT;
    private final int AIPlayers;
    private int[] AIType = {0, 0};
    private int[] AIDifficulties = {0, 0};
    private JLabel label1;
    private JLabel label2;
    private JLabel label0;
    private JFrame frame;
    private JButton[][] difficultyButtons = new JButton[2][5];
    private JButton startButton, backButton;


    public DifficultySelection(int width, int height, int AIPlayers, JFrame frame) {
        setTitle("DarkChess");
        this.WIDTH = width;
        this.HEIGHT = height;
        this.AIPlayers = AIPlayers;
        this.frame = frame;

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        setIconImage(icon);
        addLabel();

        if (AIPlayers == 1) {
            label2.setText("AIPlayer01");
            addAIConfiguration(0);
        }

        if (AIPlayers == 2) {
            label1.setText("AIPlayer01");
            label2.setText("AIPlayer02");
            addAIConfiguration(0);
            addAIConfiguration(1);
        }

        addStartButton();
        addBackButton();

        new Thread(()-> {
            while (AIDifficulties[AIPlayers-1] == 0) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            startButton.setVisible(true);
            backButton.setVisible(true);
        }).start();
    }

    private void addLabel() {
        label1 = new JLabel("Player1");
        label2 = new JLabel("Player2");
        label0 = new JLabel("VS");

        label0.setLocation(WIDTH / 2 - 20, HEIGHT / 5);
        label0.setSize(40, 40);
        label0.setFont(new Font("Rockwell", Font.BOLD, 30));
        label0.setForeground(Color.RED);
        label0.setVisible(true);
        add(label0);

        label1.setLocation(WIDTH / 2 - 140, HEIGHT / 5);
        label1.setSize(180, 40);
        label1.setFont(new Font("Rockwell", Font.BOLD, 20));
        label1.setForeground(Color.BLACK);
        label1.setVisible(true);
        add(label1);

        label2.setLocation(WIDTH / 2 + 50, HEIGHT / 5);
        label2.setSize(180, 40);
        label2.setFont(new Font("Rockwell", Font.BOLD, 20));
        label2.setForeground(Color.BLACK);
        label2.setVisible(true);
        add(label2);
    }

    private void addAIConfiguration(int number) {
        JButton randomButton = new JButton("Random");
        randomButton.setSize(120, 40);
        randomButton.setFont(new Font("Rockwell", Font.BOLD, 10));
        randomButton.setLocation(WIDTH * 1 / 9 - 35, HEIGHT * (number + 1) / 5 + 90);
        add(randomButton);

        JButton greedyButton = new JButton("Greedy");
        greedyButton.setSize(120, 40);
        greedyButton.setFont(new Font("Rockwell", Font.BOLD, 10));
        greedyButton.setLocation(WIDTH * 3 / 9 - 35, HEIGHT * (number + 1) / 5 + 90);
        add(greedyButton);

        JButton DFSButton = new JButton("DeepFirstSearch");
        DFSButton.setSize(120, 40);
        DFSButton.setFont(new Font("Rockwell", Font.BOLD, 10));
        DFSButton.setLocation(WIDTH * 5 / 9 - 35, HEIGHT * (number + 1) / 5 + 90);
        add(DFSButton);

        JButton comprehensiveButton = new JButton("Comprehensive");
        comprehensiveButton.setSize(120, 40);
        comprehensiveButton.setFont(new Font("Rockwell", Font.BOLD, 10));
        comprehensiveButton.setLocation(WIDTH * 7 / 9 - 35, HEIGHT * (number + 1) / 5 + 90);
        add(comprehensiveButton);

        addDifficultyButton(number);

        randomButton.addActionListener((e) -> {
            closeDifficultyButtons(number);
            AIType[number] = 1;
            randomButton.setBackground(Color.CYAN);
            greedyButton.setBackground(Color.WHITE);
            DFSButton.setBackground(Color.WHITE);
            comprehensiveButton.setBackground(Color.WHITE);
        });

        greedyButton.addActionListener((e) -> {
            showDifficultyButtons(number);
            AIType[number] = 2;
            randomButton.setBackground(Color.WHITE);
            greedyButton.setBackground(Color.CYAN);
            DFSButton.setBackground(Color.WHITE);
            comprehensiveButton.setBackground(Color.WHITE);
        });

        DFSButton.addActionListener((e) -> {
            showDifficultyButtons(number);
            AIType[number] = 3;
            randomButton.setBackground(Color.WHITE);
            greedyButton.setBackground(Color.WHITE);
            DFSButton.setBackground(Color.CYAN);
            comprehensiveButton.setBackground(Color.WHITE);
        });

        comprehensiveButton.addActionListener((e) -> {
            showDifficultyButtons(number);
            AIType[number] = 4;
            randomButton.setBackground(Color.WHITE);
            greedyButton.setBackground(Color.WHITE);
            DFSButton.setBackground(Color.WHITE);
            comprehensiveButton.setBackground(Color.CYAN);
        });
    }

    private void addDifficultyButton(int number){
        for (int i = 0; i < 5; i++) {
            difficultyButtons[number][i] = new JButton("Difficulty0" + (i + 1));
            difficultyButtons[number][i].setSize(120, 40);
            difficultyButtons[number][i].setFont(new Font("Rockwell", Font.BOLD, 10));
            difficultyButtons[number][i].setLocation(WIDTH * (2 * i + 1) / 11 - 30, HEIGHT * (number + 1) / 5 + 140);
            add(difficultyButtons[number][i]);
            difficultyButtons[number][i].setVisible(false);
        }
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            difficultyButtons[number][i].addActionListener((e) -> {
                AIDifficulties[number] = finalI + 1;

                for (int j = 0; j < 5; j++) {
                    if (j != finalI) {
                        difficultyButtons[number][j].setBackground(Color.WHITE);
                    } else {
                        difficultyButtons[number][j].setBackground(Color.CYAN);
                    }
                }
            });
        }
    }

    private void showDifficultyButtons(int number){
        for (int i = 0; i < 5; i++) {
            difficultyButtons[number][i].setVisible(true);
        }
    }
    private void closeDifficultyButtons(int number) {
        for (int i = 0; i < 5; i++) {
            difficultyButtons[number][i].setVisible(false);
        }
    }

    private void addStartButton() {
        startButton = new JButton("Start");
        startButton.setLocation(WIDTH / 2 + 90, HEIGHT * (AIPlayers + 2) / 5 - 30);
        startButton.setSize(140, 50);
        startButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        startButton.setVisible(false);
        add(startButton);

        startButton.addActionListener((e) -> {
            mainFrame = new ChessGameFrame(720, 720, AIPlayers, AIType[0], AIType[1], AIDifficulties[0], AIDifficulties[1]);
            mainFrame.setVisible(true);
            frame.dispose();
            this.dispose();
        });
    }

    private void addBackButton() {
        backButton = new JButton("Back");
        backButton.setLocation(WIDTH / 2 - 90, HEIGHT * (AIPlayers + 2) / 5 - 30);
        backButton.setSize(140, 50);
        backButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        backButton.setVisible(false);
        add(backButton);

        backButton.addActionListener((e) -> {
            this.dispose();
            frame.setVisible(true);
        });
    }
}
