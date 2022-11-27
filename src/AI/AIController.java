package AI;

import chessComponent.EmptySlotComponent;
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

    public void play() {
        RandomAI randomAI = new RandomAI();
        int[] xy = randomAI.move(chessboard);
        if (xy[2] >= 0 && xy[3] >= 0) {
            if (chessboard.getChessComponents()[xy[2]][xy[3]] instanceof EmptySlotComponent)
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }
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
        Point directionPoint = findDirectionPoint(x, y);
        directionPoint.x = x;
        directionPoint.y = y;
        moveTo(directionPoint);
        Thread.sleep(1000);
        press();
               Thread.sleep(1000);
        moveTo(nowPoint);
        Thread.sleep(1000);
    }

    //获取棋盘上特定棋子的相对坐标
    private Point calculatePoint(int row, int col) {
        return new Point(col * (600) / 8 + 3, row * (600) / 8 + 3);
    }

    private Point findDirectionPoint(int row, int col) {
        //获取屏幕中心点坐标
        Dimension dim = getToolkit().getScreenSize();

        int x0 = dim.width / 2;
        int y0 = dim.height / 2;
        //初始位置窗口左上角坐标
        int x1 = x0 - 360;
        int y1 = y0 - 360;
        //获取窗口内相对坐标
        Point point1 = calculatePoint(row, col);
        return new Point(point1.x + x1 + 100, point1.y + y1 + 100);
    }

}
