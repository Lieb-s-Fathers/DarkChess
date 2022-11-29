package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;

import java.awt.*;

/**
 * 表示黑红卒
 */
public class SoldierChessComponent extends ChessComponent {
    public SoldierChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size) {
        super(chessboardPoint, location, chessColor, clickController, size, 5);
        if (this.getChessColor() == ChessColor.RED) {
            name = "兵";
        } else {
            name = "卒";
        }
    }
}
