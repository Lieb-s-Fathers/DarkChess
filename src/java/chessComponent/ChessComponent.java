package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;

import java.awt.*;

/**
 * 表示棋盘上非空棋子的格子，是所有非空棋子的父类
 */
public class ChessComponent extends SquareComponent {

    protected String name;// 棋子名字：例如 兵，卒，士等

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected ChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size, int style) {
        super(chessboardPoint, location, chessColor, clickController, size, style);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!(this instanceof DoubleEatenComponent)) {
            //绘制棋子填充色
            g.setColor(Color.ORANGE);
            g.fillOval(spacingLength, spacingLength, this.getWidth() - 2 * spacingLength, this.getHeight() - 2 * spacingLength);
            //绘制棋子边框
            g.setColor(Color.DARK_GRAY);
            g.drawOval(spacingLength, spacingLength, this.getWidth() - 2 * spacingLength, getHeight() - 2 * spacingLength);
        }

        if (isReversal) {
            //绘制棋子文字
            g.setColor(this.getChessColor().getColor());
            g.setFont(getCHESS_FONT());
            g.drawString(this.name, this.getWidth() / 4, this.getHeight() * 2 / 3);

            //绘制棋子被选中时状态
            if (isSelected()) {
                g.setColor(Color.RED);
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(4f));
                g2.drawOval(spacingLength, spacingLength, getWidth() - 2 * spacingLength, getHeight() - 2 * spacingLength);
            }
        }
        //绘制棋子可以被吃时状态
        if (getCanBeEaten()) {
            g.setColor(Color.BLACK);
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(4f));
            g2.drawOval(spacingLength, spacingLength, getWidth() - 2 * spacingLength, getHeight() - 2 * spacingLength);
        }
    }
}
