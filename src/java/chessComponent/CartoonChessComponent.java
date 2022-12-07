package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;
import view.Chessboard;

import java.awt.*;

public class CartoonChessComponent extends SquareComponent implements Runnable {
    private int x0, y0, x1, y1;
    private int x, y;
    public String name;
    private boolean flag = false;
    private Chessboard chessboard;
    private SquareComponent chess1, chess2;
    private int size;


    public CartoonChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor,
                                 ClickController clickController, int size, int style, int x0, int y0, int x1, int y1, String name) {
        super(chessboardPoint, location, chessColor, clickController, size, style);
        //this.haveBackGround = false;
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
        this.size = size;
        this.name = name;
        this.setSquareColor(new Color(128, 128, 128, 0));
        x = x0;
        y = y0;
    }

    public boolean getFlag() {
        return flag;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //绘制棋子填充色
        g.setColor(Color.ORANGE);
        g.fillOval(spacingLength, spacingLength, this.getWidth() - 2 * spacingLength, this.getHeight() - 2 * spacingLength);
        //绘制棋子边框
        g.setColor(Color.DARK_GRAY);
        g.drawOval(spacingLength, spacingLength, this.getWidth() - 2 * spacingLength, getHeight() - 2 * spacingLength);

        //绘制棋子文字
        g.setColor(this.getChessColor().getColor());
        g.setFont(getCHESS_FONT());
        g.drawString(this.name, this.getWidth() / 4, this.getHeight() * 2 / 3);
    }

    public void run() {
        setVisible(true);
        for (int i = 0; i <= 100; i++) {
            x = x0 + i * (x1 - x0) / 100;
            y = y0 + i * (y1 - y0) / 100;
            this.setLocation(x, y);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            paintImmediately(x, y, size, size);
        }
    }
}