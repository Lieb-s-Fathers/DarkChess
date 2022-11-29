package view;

import AI.AIController;
import controller.WriteController;
import io.CountDown;
import io.Write;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import static controller.ReadController.loadGameFromFile;
import static io.Write.defaultOutFilePath;


/**
 * 这个类表示游戏窗体，窗体上包含：
 * 1 Chessboard: 棋盘
 * 2 JLabel:  标签
 * 3 JButton： 按钮
 */
public class ChessGameFrame extends FatherFrame {
    private static Winboard winboard;
    public static Write out = new Write(defaultOutFilePath);
    private static JLabel countLabel;
    public static CountDown countDown;
    private AIController AIFucker;
    private WriteController defaultWriteController;
    private WriteController writeController;
    public static int AIType01, AIType02, difficulty01, difficulty02;

    public ChessGameFrame(int WIDTH, int HEIGHT) {
        super(WIDTH, HEIGHT);
        winboard = new Winboard(600, 300, this);

        addCountLabel();
        addMessageLabel();

        addChessboard();
        addEatenChesses();

        addTurnLabel();
        addRedScoreLabel();
        addBlackScoreLabel();
        addBackButton();
        addCheatButton();


        addWithdrawButton();
        addSaveButton();
        addLoadButton();
        addAIButton();
    }

    public ChessGameFrame(int WIDTH, int HEIGHT, ArrayList<String[][]> gameData) {
        super(WIDTH, HEIGHT);
        winboard = new Winboard(600, 300, this);

        addCountLabel();
        addChessboard(gameData);
        addEatenChesses();
        addMessageLabel();

        addTurnLabel();
        addRedScoreLabel();
        addBlackScoreLabel();
        addBackButton();

        addCheatButton();

        addWithdrawButton();
        addSaveButton();
        addLoadButton();
        addAIButton();

        clickController.calculateScore(this);
        clickController.winJudge();
    }

    public static Winboard getWinboard() {
        return winboard;
    }


    public static JLabel getCount() {
        return countLabel;
    }

    /**
     * 在游戏窗体中添加棋盘
     */
    @Override
    public void addChessboard() {
        super.addChessboard();
        defaultWriteController = new WriteController(chessboard);
        writeController = new WriteController(chessboard);

        AIFucker = new AIController(chessboard);
        //ai类型和难度初始化
        String[] types = JOptionPane.showInputDialog(this, "Input AIType01,AIType02  here").split(",");
        String[] difficultys = JOptionPane.showInputDialog(this, "Input difficulty01,difficulty02  here").split(",");
        AIType01 = Integer.parseInt(types[0]);
        AIType02 = Integer.parseInt(types[1]);
        difficulty01 = Integer.parseInt(difficultys[0]);
        difficulty02 = Integer.parseInt(difficultys[1]);

        countDown = new CountDown(chessboard);
        countDown.start();

        defaultWriteController.save();
    }

    @Override
    public void addChessboard(ArrayList<String[][]> gameData) {
        super.addChessboard(gameData);
        defaultWriteController = new WriteController(chessboard);
        writeController = new WriteController(chessboard);
        AIFucker = new AIController(chessboard);

        countDown = new CountDown(chessboard);
        countDown.start();

        defaultWriteController.save();
    }

    private void addCountLabel() {
        countLabel = new JLabel("20");
        countLabel.setLocation(WIDTH * 3 / 5 + 185, HEIGHT / 10);
        countLabel.setSize(200, 60);
        countLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(countLabel);
    }


    private void addWithdrawButton() {
        JButton button = new JButton("Withdraw");
        button.setLocation(WIDTH * 3 / 5 + 10, HEIGHT / 10 + 200);
        button.setSize(180, 40);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("click withdraw");
            gameController.withdraw();
            ArrayList<String[][]> gameData = gameController.getChessboardDatas();
            gameController.reloadChessboard(gameData.get(gameData.size() - 1));
            writeController.save();
            clickController.swapPlayer();
            clickController.calculateScore(this);

            clickController.setCanClick(true);
            clickController.setIsCheating(false);
            notCheatButton.setVisible(false);
            remove(notCheatButton);
            remove(cheatButton);
            addCheatButton();
        });
    }

    private void addSaveButton() {
        JButton button = new JButton("Save");
        button.setLocation(WIDTH * 3 / 5 + 10, HEIGHT / 10 + 280);
        button.setSize(180, 40);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("click save");
            String path = JOptionPane.showInputDialog(this, "Output Path here");
            if (path != null) {
                File file = new File(path);
                if (file.exists()) {
                    JOptionPane.showMessageDialog(this, path + " has exsits!");
                } else {
                    writeController.saveGame(path);
                }
            }
        });
    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(WIDTH * 3 / 5 + 10, HEIGHT / 10 + 360);
        button.setSize(180, 20);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {

            System.out.println("click load");
            String path = JOptionPane.showInputDialog(this, "Input Path here");
            if (path != null) {
                try {
                    ArrayList<String[][]> gameData = loadGameFromFile(path);
                    gameController.reloadChessboardDatas(gameData);
                    if (gameData != null) {
                        gameController.reloadChessboard(gameData.get(gameData.size() - 1));
                    } else {
                        //todo 存档不符合格式
                    }
                    countDown.close();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "请输入正确的路径!");
                }
            }
        });
    }

    private void addAIButton() {
        JButton button01 = new JButton("AI01");
        button01.addActionListener((e) -> {
            System.out.println("AIFuckyou");
            AIFucker.play(AIType01, difficulty01);
        });
        button01.setLocation(WIDTH * 3 / 5 + 10, HEIGHT / 10 + 520);
        button01.setSize(90, 40);
        button01.setFont(new Font("Rockwell", Font.BOLD, 10));
        add(button01);


        JButton button02 = new JButton("AI02");
        button02.addActionListener((e) -> {
            System.out.println("AIFucyou");
            AIFucker.play(AIType02, difficulty02);
        });
        button02.setLocation(WIDTH * 3 / 5 + 90 + 10, HEIGHT / 10 + 520);
        button02.setSize(90, 40);
        button02.setFont(new Font("Rockwell", Font.BOLD, 10));
        add(button02);

        JButton aiPlay = new JButton("AIALTOPLAY");
        aiPlay.addActionListener((e) -> {
            for (int i = 1; i <= 10; i++) {
                AIFucker.play(AIType01, difficulty01);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                AIFucker.play(AIType02, difficulty02);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        aiPlay.setLocation(WIDTH * 3 / 5 + 90 + 10, HEIGHT / 10 + 520);
        aiPlay.setSize(90, 40);
        aiPlay.setFont(new Font("Rockwell", Font.BOLD, 10));
        add(aiPlay);
    }

    protected void addBackButton() {
        JButton button = new JButton("Back");
        button.setLocation(WIDTH * 3 / 5 + 10, HEIGHT / 10 + 470);
        button.setSize(180, 20);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener((e) -> {
            System.out.println("click back");
            this.setVisible(false);
            countDown.close();
            StartMenuFrame firstFrame = new StartMenuFrame(720, 720, false);
            firstFrame.setVisible(true);
        });
    }
}
