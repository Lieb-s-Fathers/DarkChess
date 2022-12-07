package view;

import AI.AIController;
import controller.ClickController;
import controller.GameController;
import controller.ReadController;
import controller.WriteController;
import io.CountDown;
import io.Write;
import model.ChessColor;
import model.ErrorType;
import model.GameData;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import static io.Write.defaultOutFile;
import static view.StartMenuFrame.mainFrame;

/**
 * 这个类表示游戏窗体，窗体上包含：
 * 1 Chessboard: 棋盘
 * 2 JLabel:  标签
 * 3 JButton： 按钮
 */
public class ChessGameFrame extends JFrame {
    private static JLabel statusLabel;
    private static JLabel redScoreLabel;
    private static JLabel blackScoreLabel;
    private static JLabel messageLabel;
    protected static JButton cheatButton;
    protected static JButton notCheatButton;

    protected EatenChesses eatenBlackChesses;
    protected EatenChesses eatenRedChesses;
    public int CHESSBOARD_SIZE;
    protected int WIDTH;
    protected int HEIGHT;
    protected Chessboard chessboard;
    protected GameController gameController;
    protected ClickController clickController;
    private static Winboard winboard;
    public static Write out = new Write(defaultOutFile);
    private static JLabel countLabel;
    public static CountDown countDown;
    private AIController AIFucker;
    private ReadController readController;
    private WriteController defaultWriteController;
    private WriteController writeController;
    protected GameData gameData;
    public ChessGameFrame() {
    }
    public ChessGameFrame(int WIDTH, int HEIGHT, int AIPlayers, int AIType01, int AIType02, int AIDifficulty01, int AIDifficulty02) {
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
        winboard = new Winboard(600, 300, this);

        addCountLabel();
        addMessageLabel();

        addChessboard(AIType01, AIType02, AIDifficulty01, AIDifficulty02);
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
        setTitle("2022 CS109 Project Demo"); //设置标题
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.CHESSBOARD_SIZE = HEIGHT * 4 / 5;
        this.gameData = gameData;

        setIconImage(StartMenuFrame.icon);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
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

    public static Winboard getWinboard() {
        return winboard;
    }

    public static JLabel getCount() {
        return countLabel;
    }

    /**
     * 在游戏窗体中添加棋盘
     */
    public void addChessboard(int AIType01, int AIType02, int AIDifficulty01, int AIDifficulty02) {
        chessboard = new Chessboard(CHESSBOARD_SIZE / 2, CHESSBOARD_SIZE, AIType01, AIType02, AIDifficulty01, AIDifficulty02);
        chessboard.setLocation(HEIGHT / 10, HEIGHT / 10);
        add(chessboard);

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

    public void addChessboard(GameData gameData, int steps) {
        chessboard = new Chessboard(CHESSBOARD_SIZE / 2, CHESSBOARD_SIZE, gameData, steps);
        gameController = new GameController(chessboard);
        clickController = new ClickController(chessboard);
        chessboard.setLocation(HEIGHT / 10, HEIGHT / 10);
        add(chessboard);

        defaultWriteController = new WriteController(chessboard.getGameData());
        writeController = new WriteController(chessboard.getGameData());
        AIFucker = new AIController(chessboard);

        defaultWriteController.save();
    }

    public void addChessboard(GameData gameData) {
        int steps = gameData.getChessDatas().size()-1;
        chessboard = new Chessboard(CHESSBOARD_SIZE / 2, CHESSBOARD_SIZE, gameData, steps);
        gameController = new GameController(chessboard);
        clickController = new ClickController(chessboard);
        chessboard.setLocation(HEIGHT / 10, HEIGHT / 10);
        add(chessboard);

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

    protected void addEatenChesses() {
        eatenBlackChesses = new EatenChesses(CHESSBOARD_SIZE / 8, CHESSBOARD_SIZE / 16 * 7, chessboard, ChessColor.BLACK);
        eatenRedChesses = new EatenChesses(CHESSBOARD_SIZE / 8, CHESSBOARD_SIZE / 16 * 7, chessboard, ChessColor.RED);
        eatenBlackChesses.setLocation(HEIGHT / 10 - 6 - chessboard.CHESS_SIZE, HEIGHT / 10 + chessboard.CHESS_SIZE);
        eatenRedChesses.setLocation(HEIGHT / 10 + 8 + chessboard.CHESS_SIZE * 4, HEIGHT / 10 + chessboard.CHESS_SIZE);
        add(eatenBlackChesses);
        add(eatenRedChesses);
    }

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
        messageLabel.setSize(400, 60);
        messageLabel.setFont(new Font("黑体", Font.BOLD, 20));
        messageLabel.setForeground(Color.RED);
        add(messageLabel);
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

    protected void addCheatButton() {
        cheatButton = new JButton("Cheat");
        cheatButton.setLocation(WIDTH * 3 / 5+10, HEIGHT / 10 + 420);
        cheatButton.setSize(180, 20);
        cheatButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(cheatButton);

        cheatButton.addActionListener((e) -> {
            System.out.println("click cheat");
            countDown.pauseThread();
            clickController.setCanClick(false);
            clickController.setIsCheating(true);
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
            countDown.resumeThread();
            remove(notCheatButton);
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
            readController = new ReadController();
            String path = readController.readPath(this);
            if (path != null) {
                readController.loadGameFromFile(path);
            }else {
                readController.setErrors(ErrorType.ONE00);
            }
            if (readController.getError(this) == ErrorType.NOError){
                countDown.close();
                mainFrame = new ChessGameFrame(720, 720, readController.getGameData());
                mainFrame.setVisible(true);
                this.dispose();}
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
            button01.setSize(180 / AIPlayers, 40);
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
