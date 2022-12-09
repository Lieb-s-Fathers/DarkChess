package view;


import chessComponent.*;
import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;

import java.awt.*;

/**
 * 这个类表示棋盘组建，其包含：
 * SquareComponent[][]: 4*8个方块格子组件
 */
public class EatenChesses extends Chessboard {

    private boolean canClick = true;
    private boolean isCheating = false;
    private static final int ROW_SIZE = 7;
    private static final int COL_SIZE = 2;

    private final int CHESS_SIZE;

    private final SquareComponent[][] squareComponents = new SquareComponent[ROW_SIZE][COL_SIZE];
    private Chessboard chessboard;
    private ClickController clickController = new ClickController(this);


    public EatenChesses(int width, int height, Chessboard chessboard, ChessColor chessColor) {
        super(width, height, 0, 0, 0, 0);
        setLayout(null); // Use absolute layout.
        setSize(width + 2, height);
        this.chessboard = chessboard;
        CHESS_SIZE = (height - 6) / 7;
        SquareComponent.setSpacingLength(CHESS_SIZE / 12);
        System.out.printf("chessboard [%d * %d], chess size = %d\n", width, height, CHESS_SIZE);
        initAllChessOnBoard(chessColor);
        clickController.setCanClick(false);
        clickController.setIsCheating(false);
    }

    public SquareComponent[][] getChessComponents() {
        return squareComponents;
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

    //初始化棋盘
    private void initAllChessOnBoard(ChessColor chessColor) {
        for (int i = 0; i < squareComponents.length; i++) {
            ChessColor color = chessColor;
            SquareComponent squareComponent;
            int temp = i;
            if (temp == 0) {
                squareComponent = new GeneralChessComponent(new ChessboardPoint(i, 0), calculatePoint(i, 0), color, clickController, CHESS_SIZE);
            } else if (temp == 1) {
                squareComponent = new AdvisorChessComponent(new ChessboardPoint(i, 0), calculatePoint(i, 0), color, clickController, CHESS_SIZE);
            } else if (temp == 2) {
                squareComponent = new MinisterChessComponent(new ChessboardPoint(i, 0), calculatePoint(i, 0), color, clickController, CHESS_SIZE);
            } else if (temp == 3) {
                squareComponent = new ChariotChessComponent(new ChessboardPoint(i, 0), calculatePoint(i, 0), color, clickController, CHESS_SIZE);
            } else if (temp == 4) {
                squareComponent = new HorseChessComponent(new ChessboardPoint(i, 0), calculatePoint(i, 0), color, clickController, CHESS_SIZE);
            } else if (temp == 5) {
                squareComponent = new SoldierChessComponent(new ChessboardPoint(i, 0), calculatePoint(i, 0), color, clickController, CHESS_SIZE);
            } else {
                squareComponent = new CannonChessComponent(new ChessboardPoint(i, 0), calculatePoint(i, 0), color, clickController, CHESS_SIZE);
            }
            squareComponent.setCHESS_FONT(new Font("宋体", Font.BOLD, 18));
            squareComponent.setReversal(true);
            squareComponent.setSquareColor(Color.WHITE);
            squareComponent.setVisible(false);
            putChessOnBoard(squareComponent);


            squareComponent = new DoubleEatenComponent(new ChessboardPoint(i, 1), calculatePoint(i, 1), color, clickController, CHESS_SIZE, 2);
            squareComponent.setCHESS_FONT(new Font("宋体", Font.BOLD, 18));
            squareComponent.setReversal(true);
            squareComponent.setSquareColor(Color.WHITE);
            squareComponent.setVisible(false);
            putChessOnBoard(squareComponent);
        }
    }

    /**
     * 绘制棋盘格子
     *
     * @param g
     */
    //todo: 重写这里的继承关系
    @Override
    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        g.fillRect(0, 0, this.getWidth(), this.getHeight());
//        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
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

    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
                synchronized (SquareComponent.class){
                    paintImmediately(this.getX()-1, this.getY()-1, this.getWidth()+1, this.getHeight() + 1);
                }

            } catch(InterruptedException e){
                throw new RuntimeException(e);
            }
        }
    }
}
