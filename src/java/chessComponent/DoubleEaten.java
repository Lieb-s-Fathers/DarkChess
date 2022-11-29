package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;

import java.awt.*;

public class DoubleEaten extends ChessComponent {
    public DoubleEaten(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size, int numbers) {
        super(chessboardPoint, location, chessColor, clickController, size, 8);
        name = "x" + numbers;
    }
}
