package AI;

import view.Chessboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class AIController extends JFrame {


    private final Robot robot;
    private final Chessboard chessboard;


    public AIController(Chessboard chessboard) {
        this.chessboard = chessboard;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    // 使用 AI 传入 xy 数组, 其中 xy[0, 1] 表示目标点的xy坐标, xy[2, 3] 表示起始点的坐标,
    // 如果起始点没有被翻开, 那么 xy[0, 1] 为要翻开的棋子坐标, xy[2, 3] 为-1
    public void play() {
        RandomAI randomAI = new RandomAI();
        GreedyAI greedyAI = new GreedyAI();
        int[] xy = greedyAI.move(chessboard);
        this.pressComponent(xy[2], xy[3]);
        this.pressComponent(xy[0], xy[1]);
    }


    public void pressComponent(int row, int col) {
        if (row >= 0 && col >= 0) {
            Point directionPoint = this.findDirectionPoint(row, col);
            try {
                this.AIpress(directionPoint.x, directionPoint.y);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // 获取当前的鼠标位置
    public Point getNowpoint() {
        return MouseInfo.getPointerInfo().getLocation();
    }

    public void press() {
        robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
    }

    public void moveTo(Point point) {
        robot.mouseMove(point.x, point.y);
    }

    public void AIpress(int x, int y) throws InterruptedException {
        Point nowPoint = getNowpoint();
        Point directionPoint = new Point();
        directionPoint.x = x;
        directionPoint.y = y;
        moveTo(directionPoint);
        Thread.sleep(50);
        press();
        Thread.sleep(50);
        moveTo(nowPoint);
    }

    private Point findDirectionPoint(int row, int col) {
        Point chessPoint = chessboard.getChessComponents()[row][col].getLocationOnScreen();
        return new Point(chessPoint.x + chessboard.CHESS_SIZE / 2, chessPoint.y + chessboard.CHESS_SIZE / 2);
    }

}
