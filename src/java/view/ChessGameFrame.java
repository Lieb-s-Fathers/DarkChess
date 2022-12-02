package view;

import AI.AIController;
import controller.ClickController;
import controller.GameController;
import controller.ReadController;
import controller.WriteController;
import io.CountDown;
import io.Write;
import model.ErrorType;
import model.GameData;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import static io.Write.defaultOutFile;

/**
 * 这个类表示游戏窗体，窗体上包含：
 * 1 Chessboard: 棋盘
 * 2 JLabel:  标签
 * 3 JButton： 按钮
 */
public class ChessGameFrame extends FatherFrame {
    private static Winboard winboard;
    public static Write out = new Write(defaultOutFile);
    private static JLabel countLabel;
    public static CountDown countDown;
    private AIController AIFucker;
    private ReadController readController;
    private WriteController defaultWriteController;
    private WriteController writeController;
    private GameData gameData;
    public ChessGameFrame(int WIDTH, int HEIGHT, int AIPlayers) {
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
        addAIButton(AIPlayers);
    }

    public ChessGameFrame(int WIDTH, int HEIGHT, GameData gameData) {
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
        addNotCheatButton();
        notCheatButton.setVisible(false);

        addWithdrawButton();
        addSaveButton();
        addLoadButton();
        addAIButton(gameData.getAIPlayers());

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

        gameData = chessboard.getGameData();

        defaultWriteController = new WriteController(chessboard.getGameData());
        writeController = new WriteController(chessboard.getGameData());
        gameController = new GameController(chessboard);
        clickController = new ClickController(chessboard);


        AIFucker = new AIController(chessboard);
        countDown = new CountDown(chessboard);
        countDown.start();

        defaultWriteController.save();
    }

    @Override
    public void addChessboard(GameData gameData) {
        super.addChessboard(gameData);
        defaultWriteController = new WriteController(chessboard.getGameData());
        writeController = new WriteController(chessboard.getGameData());
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
            gameController.reloadChessboard(gameData, gameData.size() - 1);
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
//            String path = JOptionPane.showInputDialog(this, "Input Path here");
//            if (path != null) {
//                countDown.close();
//                ArrayList<String[][]> gameData = readController.loadGameFromFile(path);
//                gameController.reloadChessboard(gameData, gameData.size() - 1);
//            }
            String path = readController.readPath();
            ArrayList<String[][]> gameData = new ArrayList<>();
            if (path != null) {
                try {
                    readController.loadGameFromFile(path);
                } catch (Exception ex) {
                    readController.setErrors(ErrorType.ONE00);
                }
            }else {
                readController.setErrors(ErrorType.ONE00);
            }
            if (readController.getError() == ErrorType.NOError){
                countDown.close();
                gameController.reloadChessboard(gameData, gameData.size() - 1);
            }
        });
    }

    private void addAIButton(int AIPlayers) {
        if (AIPlayers >= 1){
            JButton button01 = new JButton("AI01");
            button01.addActionListener((e) -> {
                System.out.println("AIFuckyou");
                AIFucker.play(gameData.getAItype01(), gameData.getDifficulty01());
            });
            button01.setLocation(WIDTH * 3 / 5 + 10, HEIGHT / 10 + 520);
            button01.setSize(90, 80 / AIPlayers);
            button01.setFont(new Font("Rockwell", Font.BOLD, 10));
            add(button01);
        }


        if (AIPlayers == 2){
            JButton button02 = new JButton("AI02");
            button02.addActionListener((e) -> {
                System.out.println("AIFucyou");
                AIFucker.play(gameData.getAItype02(), gameData.getDifficulty02());
            });
            button02.setLocation(WIDTH * 3 / 5 + 90 + 10, HEIGHT / 10 + 520);
            button02.setSize(90, 40);
            button02.setFont(new Font("Rockwell", Font.BOLD, 10));
            add(button02);
        }
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
            StartMenuFrame firstFrame = new StartMenuFrame(1123, 767, false);
            firstFrame.setVisible(true);
        });
    }
}
