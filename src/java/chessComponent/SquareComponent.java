package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * 这个类是一个抽象类，主要表示8*4棋盘上每个格子的棋子情况。
 * 有两个子类：
 * 1. EmptySlotComponent: 空棋子
 * 2. ChessComponent: 表示非空棋子
 */
public abstract class SquareComponent extends JComponent implements Runnable {
    private Font CHESS_FONT = new Font("宋体", Font.BOLD, 36);
    private Color squareColor = new Color(250, 220, 190);
    protected static int spacingLength;
    protected final ChessColor chessColor;
    /**
     * handle click event
     */
    private final ClickController clickController;
    protected int style;
    public boolean isReversal;
    protected boolean[][] canEat = {
            {true, true, true, true, true, false, true, true},
            {false, true, true, true, true, true, true, true},
            {false, false, true, true, true, true, true, true},
            {false, false, false, true, true, true, true, true},
            {false, false, false, false, true, true, true, true},
            {true, false, false, false, false, true, false, true},
            {true, true, true, true, true, true, true, true},
            {false, false, false, false, false, false, false, false}
    };
    /**
     * chessboardPoint: 表示8*4棋盘中，当前棋子在棋格对应的位置，如(0, 0), (1, 0)等等
     * chessColor: 表示这个棋子的颜色，有红色，黑色，无色三种
     * isReversal: 表示是否翻转
     * selected: 表示这个棋子是否被选中
     */
    private ChessboardPoint chessboardPoint;
    private boolean selected;
    private boolean canBeEaten;

    protected SquareComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size, int style) {
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        setLocation(location);
        setSize(size, size);
        this.chessboardPoint = chessboardPoint;
        this.chessColor = chessColor;
        this.selected = false;
        this.clickController = clickController;
        this.isReversal = false;
        this.style = style;
    }

    public static void setSpacingLength(int spacingLength) {
        SquareComponent.spacingLength = spacingLength;
    }

    public boolean isReversal() {
        return isReversal;
    }

    public void setReversal(boolean reversal) {
        isReversal = reversal;
    }

    public void setCHESS_FONT(Font CHESS_FONT) {
        this.CHESS_FONT = CHESS_FONT;
    }

    public Font getCHESS_FONT() {
        return CHESS_FONT;
    }

    public void setSquareColor(Color color) {
        this.squareColor = color;
    }

    public ChessboardPoint getChessboardPoint() {
        return chessboardPoint;
    }

    public void setChessboardPoint(ChessboardPoint chessboardPoint) {
        this.chessboardPoint = chessboardPoint;
    }

    public ChessColor getChessColor() {
        return chessColor;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean getCanBeEaten() {
        return canBeEaten;
    }

    public void setCanBeEaten(boolean canBeEaten) {
        this.canBeEaten = canBeEaten;
    }

    public int getStyle() {
        return style;
    }

    /**
     * @param another 主要用于和另外一个棋子交换位置
     *                <br>
     *                调用时机是在移动棋子的时候，将操控的棋子和对应的空位置棋子(EmptySlotComponent)做交换
     */
    public void swapLocation(SquareComponent another) {
        ChessboardPoint chessboardPoint1 = getChessboardPoint(), chessboardPoint2 = another.getChessboardPoint();
        Point point1 = getLocation(), point2 = another.getLocation();
        setChessboardPoint(chessboardPoint2);
        setLocation(point2);
        another.setChessboardPoint(chessboardPoint1);
        another.setLocation(point1);
    }

    /**
     * @param e 响应鼠标监听事件
     *          <br>
     *          当接收到鼠标动作的时候，这个方法就会自动被调用，调用监听者的onClick方法，处理棋子的选中，移动等等行为。
     */
    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            System.out.printf("Click [%d,%d]\n", chessboardPoint.getX(), chessboardPoint.getY());
            clickController.onClick(this);
        }
    }

    /**
     * @param chessboard  棋盘
     * @param destination 目标位置，如(0, 0), (0, 1)等等
     * @return this棋子对象的移动规则和当前位置(chessboardPoint)能否到达目标位置
     * <br>
     * 这个方法主要是检查移动的合法性，如果合法就返回true，反之是false。
     */
    public boolean canMoveTo(SquareComponent[][] chessboard, ChessboardPoint destination) {
        SquareComponent destinationChess = chessboard[destination.getX()][destination.getY()];
        ChessboardPoint thisPoint = this.getChessboardPoint();
        int[][] range = getRange(thisPoint, chessboard);
        int[] destinationChessXY = {destination.getX(), destination.getY()};
        boolean inRange = checkRange(destinationChessXY, range);
        boolean canMove = ((destinationChess.isReversal && canEat[this.style][destinationChess.style]) || destinationChess instanceof EmptySlotComponent) && this.getChessColor() != destinationChess.getChessColor();
        return canMove && inRange;
    }

    public int[][] getRange(ChessboardPoint thisPoint, SquareComponent[][] chessboard) {
        return new int[][]{{thisPoint.getX() + 1, thisPoint.getY()}, {thisPoint.getX() - 1, thisPoint.getY()}, {thisPoint.getX(), thisPoint.getY() + 1}, {thisPoint.getX(), thisPoint.getY() - 1}};
    }

    public boolean checkRange(int[] destinationChessXY, int[][] range) {
        boolean isInRange = false;
        for (int[] point : range) {
            if (point[0] == destinationChessXY[0] && point[1] == destinationChessXY[1]) {
                isInRange = true;
                break;
            }
        }
        return isInRange;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        //System.out.printf("repaint chess [%d,%d]\n", chessboardPoint.getX(), chessboardPoint.getY());
        g.setColor(squareColor);
        g.fillRect(1, 1, this.getWidth() - 2, this.getHeight() - 2);
    }

    @Override
    public String toString() {
        return this.getChessColor().getName() + " " + this.getStyle() + " " + this.isReversal();
    }

    public void run() {
        synchronized (SquareComponent.class) {
            while (true) {
                paintImmediately(getX(), getY(), getWidth(), getHeight());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
