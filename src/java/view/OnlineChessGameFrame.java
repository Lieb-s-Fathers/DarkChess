package view;

//import AI.AIController;
import controller.*;
import io.CountDown;
//import io.Write;
import model.ChessColor;
//import model.ErrorType;
import model.GameData;
import web.Client;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

//import static io.Write.defaultOutFile;

/**
 * 这个类表示游戏窗体，窗体上包含：
 * 1 Chessboard: 棋盘
 * 2 JLabel:  标签
 * 3 JButton： 按钮
 */
public class OnlineChessGameFrame extends ChessGameFrame implements Runnable {
//    public static Write out = new Write(defaultOutFile);
//    public static CountDown countDown;
//    protected static JButton cheatButton;
//    protected static JButton notCheatButton;
//    private static JLabel statusLabel;
//    private static JLabel redScoreLabel;
//    private static JLabel blackScoreLabel;
//    private static JLabel messageLabel;
    private static OnlineWinboard onlineWinboard;
//    private static JLabel countLabel;
//    public int CHESSBOARD_SIZE;
//    protected EatenChesses eatenBlackChesses;
//    protected EatenChesses eatenRedChesses;
//    protected int WIDTH;
//    protected int HEIGHT;
//    protected Chessboard chessboard;
//    protected GameController gameController;
//    protected ClickController clickController;
//    protected GameData gameData;
//    private AIController AIFucker;
//    private ReadController readController;
//    private WriteController defaultWriteController;
//    private WriteController writeController;
    private final ChessColor myChessColor;
    public static Client client;
    public static boolean isOnline = true;


    public OnlineChessGameFrame(int WIDTH, int HEIGHT, ChessColor myChessColor, Client client) {
        super(WIDTH, HEIGHT, 0,0,0,0,0);
        setTitle("2022 CS109 Project Demo"); //设置标题
//        this.WIDTH = WIDTH;
//        this.HEIGHT = HEIGHT;
//        this.CHESSBOARD_SIZE = HEIGHT * 4 / 5;
        this.myChessColor = myChessColor;
        OnlineChessGameFrame.client = client;

        setIconImage(StartMenuFrame.icon);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        onlineWinboard = new OnlineWinboard(600, 300, this);

        addCountLabel();
        addMessageLabel();

        addChessboard();
        addEatenChesses();

        addTurnLabel();
        addRedScoreLabel();
        addBlackScoreLabel();
        addBackButton();
        addCheatButton();
        addNotCheatButton();
        notCheatButton.setVisible(false);


        addWithdrawButton();
        addSaveButton();
//        addLoadButton();

        new Thread((this)).start();
    }


//    public OnlineChessGameFrame(int WIDTH, int HEIGHT, GameData gameData, boolean isReplay) {
//        setTitle("2022 CS109 Project Demo"); //设置标题
//        this.WIDTH = WIDTH;
//        this.HEIGHT = HEIGHT;
//        this.CHESSBOARD_SIZE = HEIGHT * 4 / 5;
//        this.gameData = gameData;
//
//        setIconImage(StartMenuFrame.icon);
//        setSize(WIDTH, HEIGHT);
//        setLocationRelativeTo(null); // Center the window.
//        getContentPane().setBackground(Color.WHITE);
//        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
//        setLayout(null);
//        onlineWinboard = new OnlineWinboard(600, 300, this);
//        addChessboard(gameData,0, true);
//
//        addCountLabel();
//        addEatenChesses();
//        addMessageLabel();
//
//        addTurnLabel();
//        addRedScoreLabel();
//        addBlackScoreLabel();
//        addBackButton();
//
//        addCheatButton();
//        addNotCheatButton();
//        notCheatButton.setVisible(false);
//
//        addWithdrawButton();
//        addSaveButton();
//        addLoadButton();
//        addAIButton(gameData.getAIPlayers());
//
//
//
//        new Thread(()->{
//            clickController.hideCursor();
//            PressController pressController = new PressController(chessboard);
//            int steps = 1;
//            int finalStep = gameData.getStepDatas().size();
//            GameData formerGameData = new GameData(gameData);
//
//            while (steps <= finalStep) {
//                int[][] stepData = gameData.getStepDatas().get(steps);
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                pressController.pressComponent(stepData[0][0], stepData[0][1]);
//
//
//                if (!(stepData[1][0] == -1 && stepData[1][1] == -1)) {
//                    try {
//                        Thread.sleep(200);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                    pressController.pressComponent(stepData[1][0], stepData[1][1]);
//                }
//                steps++;
//            }
//
//            clickController.calculateScore(this);
//            clickController.winJudge();
//            clickController.showCursor();
//            remove(chessboard);
//
//            addChessboard(formerGameData);
//
//            new Thread((this)).start();
//        }).start();
//    }


    public static OnlineWinboard getOnlineWinboard() {
        return onlineWinboard;
    }


    /**
     * 在游戏窗体中添加棋盘
     */
    public void addChessboard() {
        chessboard = new Chessboard(CHESSBOARD_SIZE / 2, CHESSBOARD_SIZE, 0,0,0,0);
        chessboard.setLocation(HEIGHT / 10, HEIGHT / 10);
        add(chessboard);

        gameData = chessboard.getGameData();

        defaultWriteController = new WriteController(chessboard.getGameData());
        writeController = new WriteController(chessboard.getGameData());
        gameController = new GameController(chessboard);
        clickController = new ClickController(chessboard);

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
//        AIFucker = new AIController(chessboard);

        defaultWriteController.save();
    }

//    public void addChessboard(GameData gameData, int steps, boolean isReplay) {
//        chessboard = new Chessboard(CHESSBOARD_SIZE / 2, CHESSBOARD_SIZE, gameData, steps);
//        gameController = new GameController(chessboard);
//        clickController = new ClickController(chessboard);
//        chessboard.setLocation(HEIGHT / 10, HEIGHT / 10);
//        add(chessboard);
//
//        defaultWriteController = new WriteController(chessboard.getGameData());
//        writeController = new WriteController(chessboard.getGameData());
//        AIFucker = new AIController(chessboard);
//
//    }

    public void addChessboard(GameData gameData) {
        int steps = gameData.getChessDatas().size() - 1;
        chessboard = new Chessboard(CHESSBOARD_SIZE / 2, CHESSBOARD_SIZE, gameData, steps);
        gameController = new GameController(chessboard);
        clickController = new ClickController(chessboard);
        chessboard.setLocation(HEIGHT / 10, HEIGHT / 10);
        add(chessboard);

        defaultWriteController = new WriteController(chessboard.getGameData());
        writeController = new WriteController(chessboard.getGameData());
//        AIFucker = new AIController(chessboard);

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
        Thread t1 = new Thread(eatenBlackChesses);
        t1.start();
        eatenRedChesses = new EatenChesses(CHESSBOARD_SIZE / 8, CHESSBOARD_SIZE / 16 * 7, chessboard, ChessColor.RED);
        Thread t2 = new Thread(eatenRedChesses);
        t2.start();
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
        redScoreLabel.setLocation(WIDTH * 3 / 5 + 10, HEIGHT / 10 + 50);
        redScoreLabel.setSize(200, 60);
        redScoreLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(redScoreLabel);
    }

    protected void addBlackScoreLabel() {
        blackScoreLabel = new JLabel("Black Score:   0");
        blackScoreLabel.setLocation(WIDTH * 3 / 5 + 10, HEIGHT / 10 + 100);
        blackScoreLabel.setSize(200, 60);
        blackScoreLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(blackScoreLabel);
    }

    protected void addMessageLabel() {
        messageLabel = new JLabel("");
        messageLabel.setLocation(WIDTH * 3 / 5 + 10, HEIGHT / 10 + 150);
        messageLabel.setSize(200, 60);
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
            ArrayList<String[][]> chessboardDatas = gameController.getChessboardDatas();
            ArrayList<int[][]> stepDatas = gameController.getStepDatas();
            gameController.reloadChessboard(chessboardDatas, stepDatas, chessboardDatas.size() - 1);
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
        cheatButton.setLocation(WIDTH * 3 / 5 + 10, HEIGHT / 10 + 420);
        cheatButton.setSize(180, 20);
        cheatButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(cheatButton);

        cheatButton.addActionListener((e) -> {
            //         System.out.println("click cheat");
            countDown.pauseThread();
            clickController.setCanClick(false);
            clickController.setIsCheating(true);
            clickController.setIsReversal(true);
            cheatButton.setVisible(false);
            remove(cheatButton);
            addNotCheatButton();
        });
    }

    protected void addNotCheatButton() {
        notCheatButton = new JButton("NotCheat");
        notCheatButton.setLocation(WIDTH * 3 / 5 + 10, HEIGHT / 10 + 420);
        notCheatButton.setSize(180, 20);
        notCheatButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        notCheatButton.setVisible(true);
        add(notCheatButton);

        notCheatButton.addActionListener((e) -> {
            //      System.out.println("click notcheat");
            clickController.setCanClick(true);
            clickController.setIsCheating(false);
            ArrayList<String[][]> chessBoardDatas = chessboard.getChessBoardDatas();
            ArrayList<int[][]> stepDatas = chessboard.getStepDatas();
            gameController.reloadChessboard(chessBoardDatas, stepDatas, chessBoardDatas.size() - 1);
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

//    private void addLoadButton() {
//        JButton button = new JButton("Load");
//        button.setLocation(WIDTH * 3 / 5 + 10, HEIGHT / 10 + 360);
//        button.setSize(180, 20);
//        button.setFont(new Font("Rockwell", Font.BOLD, 20));
//        add(button);
//
//        button.addActionListener(e -> {
//            System.out.println("click load");
//            readController = new ReadController();
//            String path = readController.readPath(this);
//            if (path != null) {
//                readController.loadGameFromFile(path);
//            } else {
//                readController.setErrors(ErrorType.ONE00);
//            }
//            if (readController.getError(this) == ErrorType.NOError) {
//                countDown.close();
//                mainFrame = new ChessGameFrame(720, 720, readController.getGameData());
//                mainFrame.setVisible(true);
//                this.dispose();
//            }
//        });
//    }

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

    public void run(){
        PressController pressController =  new PressController(chessboard);
        while (true) {
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (myChessColor != chessboard.getCurrentColor()){
                chessboard.setCanClick(false);
                try {
                    String tempData = client.getStep();
                    String[] tempStepData = tempData.split(" ");
                    int[][] stepData = new int[2][2];
                    stepData[0][0] = Integer.parseInt(tempStepData[0]);
                    stepData[0][1] = Integer.parseInt(tempStepData[1]);
                    stepData[1][0] = Integer.parseInt(tempStepData[2]);
                    stepData[1][1] = Integer.parseInt(tempStepData[3]);

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    pressController.pressComponent(stepData[0][0], stepData[0][1]);

                    if (!(stepData[1][0] == -1 && stepData[1][1] == -1)) {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        pressController.pressComponent(stepData[1][0], stepData[1][1]);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                chessboard.setCanClick(true);
            }
        }
    }
}

