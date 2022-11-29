package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;

import java.awt.*;

public class DoubleEatenComponent extends ChessComponent {
    public DoubleEatenComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size, int numbers) {
        super(chessboardPoint, location, chessColor, clickController, size, 8);
        name = "x" + numbers;
    }
}
