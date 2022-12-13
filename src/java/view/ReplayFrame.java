package view;

import model.GameData;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ReplayFrame extends ChessGameFrame {

    private int steps = 0;
    private JButton nextButton;
    private JButton lastButton;
    private JFrame frame;

    public ReplayFrame(int WIDTH, int HEIGHT, GameData gameData, JFrame frame) {
        super();
        setTitle("2022 CS109 Project Demo"); //设置标题
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.CHESSBOARD_SIZE = HEIGHT * 4 / 5;
        this.frame = frame;

        setIconImage(StartMenuFrame.icon);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        this.gameData = gameData;

        addChessboard(gameData, 0);
        countDown.close();
        addEatenChesses();

        addTurnLabel();
        addRedScoreLabel();
        addBlackScoreLabel();
        addMessageLabel();
        addBackButton();

        addNextButton();
        addLastButton();

        addNotCheatButton();
        notCheatButton.setVisible(false);
        addCheatButton();

        clickController.setCanClick(false);

        showButton();
    }

    private void addNextButton() {
        nextButton = new JButton("Next");
        nextButton.setLocation(WIDTH * 3 / 5 + 10, HEIGHT / 10 + 200);
        nextButton.setSize(180, 40);
        nextButton.setFont(new Font("Rockwell", Font.BOLD, 20));

        add(nextButton);
        nextButton.addActionListener((e) -> {
            System.out.println("click next");
            steps++;
            gameController.reloadChessboard(gameData.getChessDatas(), gameData.getStepDatas(), steps);
            clickController.calculateScore(this);
            clickController.swapPlayer();
            countDown.close();
            showButton();

            clickController.setCanClick(true);
            clickController.setIsCheating(false);
            notCheatButton.setVisible(false);
            remove(notCheatButton);
            remove(cheatButton);
            addCheatButton();
        });

    }

    private void addLastButton() {
        lastButton = new JButton("Last");
        lastButton.setLocation(WIDTH * 3 / 5 + 10, HEIGHT / 10 + 280);
        lastButton.setSize(180, 40);
        lastButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(lastButton);

        lastButton.addActionListener((e) -> {
            System.out.println("click last");
            steps--;
            ArrayList<String[][]> chessboardDatas = gameData.getChessDatas();
            ArrayList<int[][]> stepDatas = gameData.getStepDatas();
            gameController.reloadChessboard(chessboardDatas, stepDatas, steps);
            clickController.calculateScore(this);
            clickController.swapPlayer();
            countDown.close();
            showButton();

            clickController.setCanClick(true);
            clickController.setIsCheating(false);
            notCheatButton.setVisible(false);
            remove(notCheatButton);
            remove(cheatButton);
            addCheatButton();
        });
    }

    @Override
    protected void addNotCheatButton() {
        notCheatButton = new JButton("NotCheat");
        notCheatButton.setLocation(WIDTH * 3 / 5 + 10, HEIGHT / 10 + 420);
        notCheatButton.setSize(180, 20);
        notCheatButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        notCheatButton.setVisible(true);
        add(notCheatButton);

        notCheatButton.addActionListener((e) -> {
            System.out.println("click notCheat");
            clickController.setCanClick(true);
            clickController.setIsCheating(false);
            gameController.reloadChessboard(gameData.getChessDatas(), gameData.getStepDatas(), steps);
            notCheatButton.setVisible(false);
//            countDown.resumeThread();
//            我是大傻逼--tyk
            addCheatButton();
            remove(notCheatButton);
        });
    }

    @Override
    public void addBackButton() {
        JButton button = new JButton("Back");
        button.addActionListener((e) -> {
            System.out.println("click back");
            this.setVisible(false);
            frame.setVisible(true);
        });
        button.setLocation(WIDTH * 3 / 5 + 10, HEIGHT / 10 + 440);
        button.setSize(180, 40);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void showButton() {
        lastButton.setVisible(steps != 0);
        nextButton.setVisible(steps != gameData.getChessDatas().size() - 1);
    }
}
