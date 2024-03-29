package view;

import controller.ClickController;
import controller.GameController;
import model.ChessColor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * 这个类表示游戏窗体，窗体上包含：
 * 1 Chessboard: 棋盘
 * 2 JLabel:  标签
 * 3 JButton： 按钮
 */
public abstract class FatherFrame extends JFrame {
    private static JLabel statusLabel;
    private static JLabel redScoreLabel;
    private static JLabel blackScoreLabel;
    private static JLabel messageLabel;
    protected static JButton cheatButton;
    protected static JButton notCheatButton;

    protected EatenChesses eatenBlackChesses;
    protected EatenChesses eatenRedChesses;
    public final int CHESSBOARD_SIZE;
    protected final int WIDTH;
    protected final int HEIGHT;
    protected Chessboard chessboard;
    protected GameController gameController;
    protected ClickController clickController;
    protected ClickController noClickController;

    public FatherFrame(int WIDTH, int HEIGHT) {
        setTitle("2022 CS109 Project Demo"); //设置标题
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.CHESSBOARD_SIZE = HEIGHT * 4 / 5;

        setIconImage(StartMenuFrame.icon);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
    }

    public static JLabel getStatusLabel() {
        return statusLabel;
    }

    public JLabel getRedScoreLabel() {
        return redScoreLabel;
    }

    public JLabel getBlackScoreLabel() {
        return blackScoreLabel;
    }

    public static JLabel getMessageLabel() {
        return messageLabel;
    }

    public EatenChesses getEatenRedChesses(){
        return eatenRedChesses;
    }

    public EatenChesses getEatenBlackChesses(){
        return eatenBlackChesses;
    }

    /**
     * 在游戏窗体中添加棋盘
     */
    protected void addChessboard() {
        chessboard = new Chessboard(CHESSBOARD_SIZE / 2, CHESSBOARD_SIZE);
        gameController = new GameController(chessboard);
        clickController = new ClickController(chessboard);
        noClickController = new ClickController(eatenBlackChesses);
        chessboard.setLocation(HEIGHT / 10, HEIGHT / 10);
        add(chessboard);
    }

    protected void addChessboard(ArrayList<String[][]> gameData) {
        chessboard = new Chessboard(CHESSBOARD_SIZE / 2, CHESSBOARD_SIZE, gameData);
        gameController = new GameController(chessboard);
        clickController = new ClickController(chessboard);
        chessboard.setLocation(HEIGHT / 10, HEIGHT / 10);
        add(chessboard);
    }

    protected void addEatenChesses() {
        eatenBlackChesses = new EatenChesses(CHESSBOARD_SIZE / 8, CHESSBOARD_SIZE / 16 * 7, chessboard, ChessColor.BLACK);
        eatenRedChesses = new EatenChesses(CHESSBOARD_SIZE / 8, CHESSBOARD_SIZE / 16 * 7, chessboard, ChessColor.RED);
        eatenBlackChesses.setLocation(HEIGHT / 10 - 6 - chessboard.CHESS_SIZE, HEIGHT / 10 + chessboard.CHESS_SIZE);
        eatenRedChesses.setLocation(HEIGHT / 10 + 8 + chessboard.CHESS_SIZE * 4, HEIGHT / 10 + chessboard.CHESS_SIZE);
        add(eatenBlackChesses);
        add(eatenRedChesses);
    }

    /**
     * 在游戏窗体中添加标签
     */
    protected void addTurnLabel() {
        statusLabel = new JLabel(gameController.getCurrentColor() + "'s TURN");
        statusLabel.setLocation(WIDTH * 3 / 5 + 10, HEIGHT / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }

    protected void addRedScoreLabel() {
        redScoreLabel = new JLabel("Red Score:     0");
        redScoreLabel.setLocation(WIDTH * 3 / 5+10, HEIGHT / 10 + 50);
        redScoreLabel.setSize(200, 60);
        redScoreLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(redScoreLabel);
    }

    protected void addBlackScoreLabel() {
        blackScoreLabel = new JLabel("Black Score:   0");
        blackScoreLabel.setLocation(WIDTH * 3 / 5+10, HEIGHT / 10 + 100);
        blackScoreLabel.setSize(200, 60);
        blackScoreLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(blackScoreLabel);
    }

    protected void addMessageLabel() {
        messageLabel = new JLabel("");
        messageLabel.setLocation(WIDTH * 3 / 5+10, HEIGHT / 10 + 150);
        messageLabel.setSize(200, 60);
        messageLabel.setFont(new Font("黑体", Font.BOLD, 20));
        messageLabel.setForeground(Color.RED);
        add(messageLabel);
    }

    /**
     * 在游戏窗体中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addHelloButton() {
        JButton button = new JButton("Show Hello Here");
        button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Hello, world!"));
        button.setLocation(WIDTH * 3 / 5+10, HEIGHT / 10 + 120);
        button.setSize(180, 40);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    protected void addCheatButton() {
        cheatButton = new JButton("Cheat");
        cheatButton.setLocation(WIDTH * 3 / 5+10, HEIGHT / 10 + 420);
        cheatButton.setSize(180, 20);
        cheatButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(cheatButton);

        cheatButton.addActionListener((e) -> {
            System.out.println("click cheat");
            clickController.setCanClick(false);
            if (this instanceof ChessGameFrame){
                clickController.setIsCheating(true);
            }
            clickController.setIsReversal(true);
            cheatButton.setVisible(false);
            remove(cheatButton);
            addNotCheatButton();
        });
    }

    protected void addNotCheatButton(){
        notCheatButton = new JButton("NotCheat");
        notCheatButton.setLocation(WIDTH * 3 / 5+10, HEIGHT / 10 + 420);
        notCheatButton.setSize(180, 20);
        notCheatButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        notCheatButton.setVisible(true);
        add(notCheatButton);

        notCheatButton.addActionListener((e) -> {
            System.out.println("click notcheat");
            clickController.setCanClick(true);
            clickController.setIsCheating(false);
            ArrayList<String[][]> gameData = chessboard.getChessBoardDatas();
            gameController.reloadChessboard(gameData, gameData.size()-1);
            notCheatButton.setVisible(false);
            remove(notCheatButton);
            addCheatButton();
        });
    }

    protected abstract void addBackButton();
}
