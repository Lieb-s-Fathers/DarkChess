package view;


import chessComponent.*;
import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 这个类表示棋盘组建，其包含：
 * SquareComponent[][]: 4*8个方块格子组件
 */
public class Chessboard extends JComponent {

    private static final int ROW_SIZE = 8;
    private static final int COL_SIZE = 4;
    //all chessComponents in this chessboard are shared only one model controller
    public final ClickController clickController = new ClickController(this);
    private final SquareComponent[][] squareComponents = new SquareComponent[ROW_SIZE][COL_SIZE];
    private final int CHESS_SIZE;
    //todo: you can change the initial player
    private ChessColor currentColor = ChessColor.BLACK;

    private int[][] killedComponents = new int[8][2];
    private int[] componentsScore = {30, 10, 5, 5, 5, 1, 5};


    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width + 2, height);
        CHESS_SIZE = (height - 6) / 8;
        SquareComponent.setSpacingLength(CHESS_SIZE / 12);
        System.out.printf("chessboard [%d * %d], chess size = %d\n", width, height, CHESS_SIZE);

        initAllChessOnBoard();
    }

    public int[][] getKilledComponents() {
        return killedComponents;
    }

    public int getRedScore() {
        int redScore = 0;
        for (int i = 0; i < 7; i++) {
            redScore += this.killedComponents[i][0] * this.componentsScore[i];
        }
        return redScore;
    }

    public int getBlackScore() {
        int blackScore = 0;
        for (int i = 0; i < 7; i++) {
            blackScore += this.killedComponents[i][1] * this.componentsScore[i];
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
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE, -1));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        squareComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        squareComponents[row2][col2] = chess2;

        //只重新绘制chess1 chess2，其他不变
        chess1.repaint();
        chess2.repaint();
    }

    //初始化棋盘
    private void initAllChessOnBoard() {
        Random random = new Random();
        int[][] componentlist = {{1, 2, 2, 2, 2, 5, 2}, {1, 2, 2, 2, 2, 5, 2}};
        for (int i = 0; i < squareComponents.length; i++) {
            for (int j = 0; j < squareComponents[i].length; j++) {
                int colorID = random.nextInt(2);
                int temp = random.nextInt(7);
                while (componentlist[colorID][temp] == 0) {
                    temp = random.nextInt(7);
                    colorID = random.nextInt(2);
                }

                ChessColor color = colorID == 0 ? ChessColor.RED : ChessColor.BLACK;
                SquareComponent squareComponent;

                if (temp == 0) {
                    squareComponent = new GeneralChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                } else if (temp == 1) {
                    squareComponent = new AdvisorChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                } else if (temp == 2) {
                    squareComponent = new MinisterChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                } else if (temp == 3) {
                    squareComponent = new ChariotChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                } else if (temp == 4) {
                    squareComponent = new HorseChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                } else if (temp == 5) {
                    squareComponent = new SoldierChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                } else {
                    squareComponent = new CannonChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                }
                componentlist[colorID][temp]--;
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

    //todo: 存取游戏

    /**
     * 通过GameController调用该方法
     *
     * @param chessData
     */
    public void loadGame(List<String> chessData) {
        chessData.forEach(System.out::println);
    }

    public void saveGame() {

    }

    //todo: 将棋盘状态转换成List<string> chesssData

    public List<String> toChessData() {
        List<String> chessData = new ArrayList<String>();
        //todo: 完善这个方法
        StringBuffer componentStyle = null;
        StringBuffer componentColor = null;
        for (int i = 0; i < 28; i++) {

        }
        return chessData;
    }
}
