package controller;

import view.Chessboard;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PressController {
    private final Robot robot;
    private Chessboard chessboard;

    public PressController(Chessboard chessboard){
        this.chessboard = chessboard;try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }

    }

    public void pressComponent(int row, int col) {
        if (row >= 0 && col >= 0) {
            System.out.printf("AI Click %d %d\n", row, col);
            Point directionPoint = this.findDirectionPoint(row, col);
            try {
                this.AIPress(directionPoint.x, directionPoint.y);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // 获取当前的鼠标位置
    public Point getNowPoint() {
        return MouseInfo.getPointerInfo().getLocation();
    }

    public void press() {
        robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
    }

    public void moveTo(Point point) {
        robot.mouseMove(point.x, point.y);
    }

    public void AIPress(int x, int y) throws InterruptedException {
        Point nowPoint = getNowPoint();
        Point directionPoint = new Point();
        directionPoint.x = x;
        directionPoint.y = y;
        moveTo(directionPoint);
        Thread.sleep(5);
        press();
        Thread.sleep(5);
        moveTo(nowPoint);
    }

    private Point findDirectionPoint(int row, int col) {
        Point chessPoint = chessboard.getChessComponents()[row][col].getLocationOnScreen();
        return new Point(chessPoint.x + chessboard.CHESS_SIZE / 2, chessPoint.y + chessboard.CHESS_SIZE / 2);
    }

}
