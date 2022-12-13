package view;


import chessComponent.*;
import controller.ClickController;
import controller.GameController;
import model.ChessColor;
import model.ChessboardPoint;
import model.GameData;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static view.StartMenuFrame.mainFrame;

/**
 * 这个类表示棋盘组建，其包含：
 * SquareComponent[][]: 4*8个方块格子组件
 */
public class Chessboard extends JComponent implements Runnable {
    public static final int[] componentsScore = {30, 10, 5, 5, 5, 1, 5, 0};
    private static final int ROW_SIZE = 8;
    private static final int COL_SIZE = 4;
    private final SquareComponent[][] squareComponents = new SquareComponent[ROW_SIZE][COL_SIZE];
    private final int[][] componentList = {{1, 2, 2, 2, 2, 5, 2}, {1, 2, 2, 2, 2, 5, 2}};
    public int CHESS_SIZE = 0;
    private boolean canClick = true;
    private boolean isCheating = false;
    //all chessComponents in this chessboard are shared only one model controller
    private ClickController clickController;
    private GameController gameController;
    private ChessColor currentColor;
    private GameData gameData;
    private ArrayList<String[][]> chessBoardDatas;
    private ArrayList<int[][]> stepDatas;
    private CartoonMoveChessComponent cartoonChess1;

    public Chessboard(int width, int height, int AIType01, int AIType02, int AIDifficulty01, int AIDifficulty02) {
        synchronized (Chessboard.class) {
            if (!(this instanceof EatenChesses)) {
                setLayout(null); // Use absolute layout.
                currentColor = ChessColor.RED;
                setSize(width + 2, height);
                CHESS_SIZE = (height - 6) / 8;
                SquareComponent.setSpacingLength(CHESS_SIZE / 12);
                System.out.printf("chessboard [%d * %d], chess size = %d\n", width, height, CHESS_SIZE);

//            String[] types = JOptionPane.showInputDialog(this, "Input AIType01,AIType02  here").split(",");
//            String[] difficultys = JOptionPane.showInputDialog(this, "Input difficulty01,difficulty02  here").split(",");
//            gameData = new GameData(Integer.parseInt(types[0]), Integer.parseInt(types[1]), Integer.parseInt(difficultys[0]), Integer.parseInt(difficultys[1]));

                gameData = new GameData(AIType01, AIType02, AIDifficulty01, AIDifficulty02);

                clickController = new ClickController(this);
                gameController = new GameController(this);
                chessBoardDatas = gameData.getChessDatas();
                stepDatas = gameData.getStepDatas();

                initAllChessOnBoard();
                addChessBoardData();
                Thread t = new Thread(this);
                t.start();
            }
        }

    }

    public Chessboard(int width, int height, GameData gameData, int steps) {
        synchronized (Chessboard.class) {
            if (!(this instanceof EatenChesses)) {
                this.gameData = gameData;
                chessBoardDatas = gameData.getChessDatas();
                stepDatas = gameData.getStepDatas();
                clickController = new ClickController(this);
                gameController = new GameController(this);
                setLayout(null); // Use absolute layout.
                setSize(width + 2, height);
                CHESS_SIZE = (height - 6) / 8;
                SquareComponent.setSpacingLength(CHESS_SIZE / 12);
                System.out.printf("chessboard [%d * %d], chess size = %d\n", width, height, CHESS_SIZE);
                gameController.reloadChessboard(gameData.getChessDatas(), gameData.getStepDatas(), steps);
                if (steps % 2 == 0) {
                    currentColor = ChessColor.RED;
                } else {
                    currentColor = ChessColor.BLACK;
                }
                Thread t = new Thread(this);
                t.start();
            }
        }

    }

    public int[][] getkilledComponents() {
        SquareComponent[][] chessBoard = getChessComponents();
        int[][] killedComponents = {{1, 2, 2, 2, 2, 5, 2}, {1, 2, 2, 2, 2, 5, 2}};

        for (SquareComponent[] squareComponents : chessBoard) {
            for (SquareComponent squareComponent : squareComponents) {
                if (!(squareComponent instanceof EmptySlotComponent) && squareComponent != null) {
                    int color = squareComponent.getChessColor() == ChessColor.BLACK ? 0 : 1;
                    killedComponents[color][squareComponent.getStyle()]--;
                }
            }
        }
        return killedComponents;
    }

    public void printKilledComponents() {
        System.out.print("Black: ");
        int[][] killedComponents = getkilledComponents();
        for (int i = 0; i < 7; i++) System.out.printf("%d ", killedComponents[0][i]);
        System.out.println();
        System.out.print("Red: ");
        for (int i = 0; i < 7; i++) System.out.printf("%d ", killedComponents[1][i]);
        System.out.println();
    }

    public boolean getCanClick() {
        return canClick;
    }

    public void setCanClick(boolean canClick) {
        this.canClick = canClick;
    }

    public boolean getIsCheating() {
        return isCheating;
    }

    public void setIsCheating(boolean isCheating) {
        this.isCheating = isCheating;
    }

    public GameData getGameData() {
        return gameData;
    }

    public void setGameData(GameData gameData) {
        this.gameData = gameData;
    }

    public int getRedScore() {
        int redScore = 0;
        int[][] killedComponents = getkilledComponents();
        for (int i = 0; i < 7; i++) {
            redScore += killedComponents[0][i] * this.componentsScore[i];
        }
        return redScore;
    }

    public int getBlackScore() {
        int blackScore = 0;
        int[][] killedComponents = getkilledComponents();
        for (int i = 0; i < 7; i++) {
            blackScore += killedComponents[1][i] * this.componentsScore[i];
        }
        return blackScore;
    }

    public SquareComponent[][] getChessComponents() {
        return squareComponents;
    }


    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(ChessColor currentColor) {
        this.currentColor = currentColor;
    }

    public void addChessBoardData() {
        String[][] chessBoardData = new String[32][3];
        int num = 0;
        for (SquareComponent[] chessComponents : squareComponents) {
            for (SquareComponent chessComponent : chessComponents) {
                chessBoardData[num][0] = chessComponent.getChessColor().getName();
                chessBoardData[num][1] = String.valueOf(chessComponent.getStyle());
                chessBoardData[num][2] = String.valueOf(chessComponent.isReversal());
                num++;
            }
        }
        chessBoardDatas.add(chessBoardData);
    }

    public void addStepFirstClick(int x, int y){
        int[][] tempStepData = {{-1,-1},{-1,-1}};
        tempStepData[0][0] = x;
        tempStepData[0][1] = y;
        stepDatas.add(tempStepData);
    }

    public void addStepSecondClick(int x0, int y0, int x1, int y1) {
        int[][] tempStepData = {{-1, -1},{-1,-1}};
        tempStepData[0][0] = x0;;
        tempStepData[0][1] = y0;
        tempStepData[1][0] = x1;
        tempStepData[1][1] = y1;
        stepDatas.add(tempStepData);
    }

    public void deleteLastStep() {
        if (chessBoardDatas.size() > 1) {
            chessBoardDatas.remove(chessBoardDatas.size() - 1);
            stepDatas.remove(chessBoardDatas.size() - 1);
        } else {
            JOptionPane.showMessageDialog(this, "This is the first step");
            clickController.swapPlayer();
        }
    }

    public ArrayList<String[][]> getChessBoardDatas() {
        return chessBoardDatas;
    }

    public ArrayList<int[][]> getStepDatas() {
        return stepDatas;
    }

    /**
     * 将SquareComponent 放置在 ChessBoard上。里面包含移除原有的component及放置新的component
     *
     * @param squareComponent
     */
    public void putChessOnBoard(SquareComponent squareComponent) {
        int row = squareComponent.getChessboardPoint().getX(), col = squareComponent.getChessboardPoint().getY();
        if (squareComponents[row][col] != null) {
            remove(squareComponents[row][col]);
        }
        add(squareComponents[row][col] = squareComponent);
    }

    /**
     * 交换chess1 chess2的位置
     *
     * @param chess1
     * @param chess2
     */
    public void swapChessComponents(SquareComponent chess1, SquareComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        synchronized (getChessBoardDatas()) {
            SquareComponent chess0 = new EmptySlotComponent(chess1.getChessboardPoint(), chess1.getLocation(), clickController, CHESS_SIZE);
            chess0.setVisible(true);
//            Thread t0 = new Thread(chess0);
            add(chess0, 1);
//            t0.start();

            cartoonChess1 = new CartoonMoveChessComponent(chess1.getChessboardPoint(),
                    chess1.getLocation(), chess1.getChessColor(), new ClickController(this), chess1.getWidth(),
                    chess1.getStyle(), chess1.getX(), chess1.getY(), chess2.getX(), chess2.getY(), chess1.getName());
            add(cartoonChess1, 0);
            Thread t = new Thread(cartoonChess1);
            t.start();

            new Thread(() -> {
                while (t.isAlive()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                try {
                    Thread.sleep(1);
                    cartoonOver(chess1, chess2, chess0);
                    cartoonChess1.setVisible(false);
                    remove(cartoonChess1);
                    clickController.calculateScore(mainFrame);
                    clickController.winJudge();
                    addChessBoardData();
                    clickController.writeController.save();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
        }


    }

    private void cartoonOver(SquareComponent chess1, SquareComponent chess2, SquareComponent chess0) {
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        squareComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        squareComponents[row2][col2] = chess2;
        chess0.setVisible(false);
        remove(chess0);
    }

    //初始化棋盘
    private void initAllChessOnBoard() {
        Random random = new Random();
        for (int i = 0; i < squareComponents.length; i++) {
            for (int j = 0; j < squareComponents[i].length; j++) {
                int colorID = random.nextInt(2);
                int temp = random.nextInt(16);
                int tempstyle = 0;
                if (temp == 0) tempstyle = 0;
                else if (0 < temp && temp <= 2) tempstyle = 1;
                else if (temp > 2 && temp <= 4) tempstyle = 2;
                else if (temp > 4 && temp <= 6) tempstyle = 3;
                else if (temp > 6 && temp <= 8) tempstyle = 4;
                else if (temp > 8 && temp <= 13) tempstyle = 5;
                else tempstyle = 6;

                while (componentList[colorID][tempstyle] == 0) {
                    temp = random.nextInt(16);
                    colorID = random.nextInt(2);
                    if (temp == 0) tempstyle = 0;
                    else if (0 < temp && temp <= 2) tempstyle = 1;
                    else if (temp > 2 && temp <= 4) tempstyle = 2;
                    else if (temp > 4 && temp <= 6) tempstyle = 3;
                    else if (temp > 6 && temp <= 8) tempstyle = 4;
                    else if (temp > 8 && temp <= 13) tempstyle = 5;
                    else tempstyle = 6;
                }
                ChessColor color = colorID == 0 ? ChessColor.RED : ChessColor.BLACK;
                SquareComponent squareComponent;
                int style = 0;
                if (temp == 0) {
                    style = 0;
                    squareComponent = new GeneralChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                } else if (0 < temp && temp <= 2) {
                    style = 1;
                    squareComponent = new AdvisorChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                } else if (temp > 2 && temp <= 4) {
                    style = 2;
                    squareComponent = new MinisterChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                } else if (temp > 4 && temp <= 6) {
                    style = 3;
                    squareComponent = new ChariotChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                } else if (temp > 6 && temp <= 8) {
                    style = 4;
                    squareComponent = new HorseChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                } else if (temp > 8 && temp <= 13) {
                    style = 5;
                    squareComponent = new SoldierChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                } else {
                    style = 6;
                    squareComponent = new CannonChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                }
                componentList[colorID][style]--;
                squareComponent.setVisible(true);
                putChessOnBoard(squareComponent);
            }
        }

    }

    /**
     * 绘制棋盘格子
     *
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    /**
     * 将棋盘上行列坐标映射成Swing组件的Point
     *
     * @param row 棋盘上的行
     * @param col 棋盘上的列
     * @return
     */
    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE + 3, row * CHESS_SIZE + 3);
    }


    public void loadGameData(ArrayList<String[][]> chessBoardDatas) {
        this.chessBoardDatas = chessBoardDatas;
    }

    public void loadStepData(ArrayList<int[][]> stepDatas){
        this.stepDatas = stepDatas;
    }


    /**
     * 通过GameController调用该方法
     */
    public void loadGame(String[][] chessBoardData) {
        int num = 0;
        for (int i = 0; i < squareComponents.length; i++) {
            for (int j = 0; j < squareComponents[i].length; j++) {
                String colorName = chessBoardData[num][0];
                String temp = chessBoardData[num][1];
                boolean isReversal = Boolean.parseBoolean(chessBoardData[num][2]);
                ChessColor color = ChessColor.RED.getName().equals(colorName) ? ChessColor.RED : ChessColor.BLACK;
                SquareComponent squareComponent = switch (temp) {
                    case "0" ->
                            new GeneralChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                    case "1" ->
                            new AdvisorChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                    case "2" ->
                            new MinisterChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                    case "3" ->
                            new ChariotChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                    case "4" ->
                            new HorseChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                    case "5" ->
                            new SoldierChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                    case "6" ->
                            new CannonChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                    default ->
                            new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE);
                };
                squareComponent.setReversal(isReversal);
                putChessOnBoard(squareComponent);
                squareComponent.setVisible(true);
//                squareComponent.repaint();
                num++;
            }
        }
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(200);
                synchronized (Chessboard.class){
                    paintImmediately(this.getX()-CHESS_SIZE, this.getY()-CHESS_SIZE-2, this.getWidth(), this.getHeight());
                }
            } catch(InterruptedException e){
                throw new RuntimeException(e);
            }
        }
    }
}
