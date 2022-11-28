package view;

import AI.AIController;
import controller.WriteController;
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
    public static Winboard winboard;
    public static Write out = new Write(defaultOutFilePath);
    private static JLabel statusLabel;
    private static JLabel countLabel;
    public AIController AIFucker;
    private WriteController defaultWriteController;
    private WriteController writeController;

    public ChessGameFrame(int WIDTH, int HEIGHT) {
        super(WIDTH, HEIGHT);
        winboard = new Winboard(600, 300, this);

        addChessboard();

        addTurnLabel();
        addRedScoreLabel();
        addBlackScoreLabel();
        addMessageLabel();
        addBackButton();

        addCountLabel();
        addWithdrawButton();
        addSaveButton();
        addLoadButton();
        addAIButton();
    }

    public ChessGameFrame(int WIDTH, int HEIGHT, ArrayList<String[][]> gameData) {
        super(WIDTH, HEIGHT);
        winboard = new Winboard(600, 300, this);

        addChessboard(gameData);

        addTurnLabel();
        addRedScoreLabel();
        addBlackScoreLabel();
        addMessageLabel();
        addBackButton();

        addCountLabel();
        addWithdrawButton();
        addSaveButton();
        addLoadButton();
        addAIButton();
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
        defaultWriteController.save();
    }

    @Override
    public void addChessboard(ArrayList<String[][]> gameData) {
        super.addChessboard(gameData);
        defaultWriteController = new WriteController(chessboard);
        writeController = new WriteController(chessboard);
        AIFucker = new AIController(chessboard);
        defaultWriteController.save();
        clickController.calculateScore(this);
        clickController.winJudge();
    }

    private void addCountLabel() {
        countLabel = new JLabel("20");
        countLabel.setLocation(WIDTH * 3 / 5 + 175, HEIGHT / 10);
        countLabel.setSize(200, 60);
        countLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(countLabel);
    }


    private void addWithdrawButton() {
        JButton button = new JButton("Withdraw");
        button.setLocation(WIDTH * 3 / 5, HEIGHT / 10 + 200);
        button.setSize(180, 40);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBackground(Color.LIGHT_GRAY);
        add(button);

        button.addActionListener(e -> {
            System.out.println("click withdraw");
            gameController.withdraw();
            ArrayList<String[][]> gameData = gameController.getChessboardDatas();
            gameController.reloadChessboard(gameData.get(gameData.size() - 1));
            writeController.save();
            clickController.swapPlayer();
            clickController.calculateScore(this);
        });
    }

    private void addSaveButton() {
        JButton button = new JButton("Save");
        button.setLocation(WIDTH * 3 / 5, HEIGHT / 10 + 280);
        button.setSize(180, 40);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBackground(Color.LIGHT_GRAY);
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
        button.setLocation(WIDTH * 3 / 5, HEIGHT / 10 + 360);
        button.setSize(180, 40);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBackground(Color.LIGHT_GRAY);
        add(button);

        button.addActionListener(e -> {

            System.out.println("click load");
            String path = JOptionPane.showInputDialog(this, "Input Path here");
            if (path != null) {
                try {
//                    writeController.close();
                    ArrayList<String[][]> gameData = loadGameFromFile(path);
//                    ChessGameFrame mainFrame = new ChessGameFrame(720, 720, gameData.get(gameData.size() - 1));
//                    mainFrame.setVisible(true);
//                    this.dispose();
                    gameController.reloadChessboardDatas(gameData);
                    if (gameData != null) {
                        gameController.reloadChessboard(gameData.get(gameData.size() - 1));
                    } else {
                        //todo 存档不符合格式
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "请输入正确的路径!");
                }
            }
        });
    }

    private void addAIButton() {
        JButton button = new JButton("AIFuckyou");
        button.addActionListener((e) -> {
            System.out.println("AIFuckyou");
            AIFucker.play();

        });
        button.setLocation(WIDTH * 3 / 5, HEIGHT / 10 + 520);
        button.setSize(180, 40);
        button.setFont(new Font("Rockwell", Font.BOLD, 10));
        add(button);
    }
}
