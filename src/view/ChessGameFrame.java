package view;

import controller.ClickController;
import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * 这个类表示游戏窗体，窗体上包含：
 * 1 Chessboard: 棋盘
 * 2 JLabel:  标签
 * 3 JButton： 按钮
 */
public class ChessGameFrame extends JFrame {
    private final int WIDTH;
    private final int HEIGHT;
    public final int CHESSBOARD_SIZE;
    private GameController gameController;
    private static JLabel statusLabel;
    private static JLabel countLabel;
    private static JLabel redScoreLabel;
    private static JLabel blackScoreLabel;
    private static JLabel messageLabel;

    public static Winboard winboard;

    public ChessGameFrame(int width, int height) {
        setTitle("2022 CS109 Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGHT = height;
        this.CHESSBOARD_SIZE = HEIGHT * 4 / 5;
        this.winboard = new Winboard(600, 300, this);

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        addChessboard();
        addTurnLabel();
        addCountLabel();
        addRedScoreLabel();
        addBlackScoreLabel();
        addMessageLabel();
//        addHelloButton();
        addLoadButton();
        addBackButton();
    }

    public ChessGameFrame(int width, int height, String[][] chessBoardData) {
        setTitle("2022 CS109 Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGHT = height;
        this.CHESSBOARD_SIZE = HEIGHT * 4 / 5;
        this.winboard = new Winboard(600, 300, this);

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        addChessboard(chessBoardData);
        addTurnLabel();
        addCountLabel();
        addRedScoreLabel();
        addBlackScoreLabel();
        addMessageLabel();
//        addHelloButton();
        addLoadButton();
        addBackButton();
    }

    /**
     * 在游戏窗体中添加棋盘
     */
    private void addChessboard() {
        Chessboard chessboard = new Chessboard(CHESSBOARD_SIZE / 2, CHESSBOARD_SIZE);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGHT / 10, HEIGHT / 10);
        add(chessboard);
    }
    private void addChessboard(String[][] chessBoardData) {
        Chessboard chessboard = new Chessboard(CHESSBOARD_SIZE / 2, CHESSBOARD_SIZE, chessBoardData);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGHT / 10, HEIGHT / 10);
        add(chessboard);
    }


    /**
     * 在游戏窗体中添加标签
     */
    private void addTurnLabel() {
        statusLabel = new JLabel("BLACK's TURN");
        statusLabel.setLocation(WIDTH * 3 / 5, HEIGHT / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }

    private void addCountLabel() {
        countLabel = new JLabel("20");
        countLabel.setLocation(WIDTH * 3 / 5 + 175, HEIGHT / 10);
        countLabel.setSize(200, 60);
        countLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(countLabel);
    }

    private void addRedScoreLabel() {
        redScoreLabel = new JLabel("Red Score:     0");
        redScoreLabel.setLocation(WIDTH * 3 / 5, HEIGHT / 10 + 50);
        redScoreLabel.setSize(200, 60);
        redScoreLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(redScoreLabel);
    }

    private void addBlackScoreLabel() {
        blackScoreLabel = new JLabel("Black Score:   0");
        blackScoreLabel.setLocation(WIDTH * 3 / 5, HEIGHT / 10 + 100);
        blackScoreLabel.setSize(200, 60);
        blackScoreLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(blackScoreLabel);
    }

    private void addMessageLabel() {
        messageLabel = new JLabel("");
        messageLabel.setLocation(WIDTH * 3 / 5, HEIGHT / 10 + 150);
        messageLabel.setSize(200, 60);
        messageLabel.setFont(new Font("黑体", Font.BOLD, 20));
        messageLabel.setForeground(Color.RED);
        add(messageLabel);
    }

    public static JLabel getStatusLabel() {
        return statusLabel;
    }
    public static JLabel getCount(){
        return countLabel;
    }
    public static JLabel getRedScoreLabel() {
        return redScoreLabel;
    }

    public static JLabel getBlackScoreLabel() {
        return blackScoreLabel;
    }

    public static JLabel getMessageLabel() {
        return messageLabel;
    }

    /**
     * 在游戏窗体中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addHelloButton() {
        JButton button = new JButton("Show Hello Here");
        button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Hello, world!"));
        button.setLocation(WIDTH * 3 / 5, HEIGHT / 10 + 120);
        button.setSize(180, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }


    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(WIDTH * 3 / 5, HEIGHT / 10 + 280);
        button.setSize(180, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBackground(Color.LIGHT_GRAY);
        add(button);

        button.addActionListener(e -> {
            ClickController.out.close();

            System.out.println("click load");
            String path = JOptionPane.showInputDialog(this, "Input Path here");



            this.dispose();
            ArrayList<String[][]> gameData = gameController.loadGameFromFile(path);
            ChessGameFrame mainFrame = new ChessGameFrame(720, 720, gameData.get(gameData.size() - 1));
            mainFrame.setVisible(true);
        });


    }

    private void addBackButton() {
        JButton button = new JButton("Back");
        button.addActionListener((e) -> {
            System.out.println("click back");
            this.setVisible(false);
            StartMenuFrame firstFrame = new StartMenuFrame(720, 720, false);
            firstFrame.setVisible(true);
        });
        button.setLocation(WIDTH * 3 / 5, HEIGHT / 10 + 400);
        button.setSize(180, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
}
