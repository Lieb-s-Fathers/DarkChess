package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;
import view.Chessboard;

import java.awt.*;

public class CartoonShowChessComponent extends SquareComponent implements Runnable {
    private int x, y;
    public String name;
    private boolean flag = false;
    private ChessColor formerChessColor;
    private Chessboard chessboard;
    private SquareComponent chess1, chess2;
    private int size;
    private Color color = new Color(0,0,0,0);


    public CartoonShowChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor,
                                     ClickController clickController, int size, int style, int x, int y, String name) {
        super(chessboardPoint, location, chessColor, clickController, size, style);
        this.x = x;
        this.y = y;
        this.formerChessColor = chessColor;
        this.size = size;
        this.name = name;
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
        g.setColor(this.color);
        g.setFont(getCHESS_FONT());
        g.drawString(this.name, this.getWidth() / 4, this.getHeight() * 2 / 3);
    }

    public void run() {
//        synchronized (Chessboard.class) {
            this.setLocation(x, y);

            setVisible(true);
            for (int i = 0; i <= 255; i += 10) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                float[] tempColors = new float[3];
                formerChessColor.getColor().getRGBColorComponents(tempColors);
                for (int j = 0; j < 3; j++) {
                    tempColors[j] = tempColors[j] * 255;
                }
                color = new Color((int) tempColors[0], (int) tempColors[1], (int) tempColors[2], i);
//                this.paintImmediately(x, y, size, size);
//                System.out.println(Arrays.toString(tempColors) + i);
            }
        }
//    }
}