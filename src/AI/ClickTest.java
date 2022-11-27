package AI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Scanner;


public class ClickTest extends JFrame {
    private static Robot robot;
    private static Scanner in = new Scanner(System.in);

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try {
            AIplay(1000, 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // 获取当前的鼠标位置
    public static Point getNowpoint() {
        return MouseInfo.getPointerInfo().getLocation();
    }

    public static void press() {
        robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
    }

    public static void moveTo(Point point) {
        robot.mouseMove(point.x, point.y);
    }

    public static void AIplay(int x, int y) throws InterruptedException {
        Point nowPoint = getNowpoint();
        Point directionPoint = new Point();
        directionPoint.x = x;
        directionPoint.y = y;
        moveTo(directionPoint);
        Thread.sleep(1000);
        press();
        Thread.sleep(1000);
        moveTo(nowPoint);
    }
}
