package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;

import java.awt.*;

public class CannonChessComponent extends ChessComponent {
    public CannonChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size) {
        super(chessboardPoint, location, chessColor, clickController, size, 6);
        if (this.getChessColor() == ChessColor.RED) {
            name = "炮";
        } else {
            name = "砲";
        }
    }

    @Override
    public boolean canMoveTo(SquareComponent[][] chessboard, ChessboardPoint destination) {
        SquareComponent destinationChess = chessboard[destination.getX()][destination.getY()];
        ChessboardPoint thisPoint = this.getChessboardPoint();
        int[][] range = getRange(thisPoint);
        int[] destinationChessXY = {destination.getX(), destination.getY()};
        boolean inRange = checkRange(destinationChessXY, range);
        boolean canMove = canEat[this.style][destinationChess.style]&&(destinationChess.getChessColor() != this.getChessColor());
        return canMove && inRange;
    }

    public int[][] getRange(ChessboardPoint cannonPoint) {
        int[][] range=new int[5][2];
        int x0=cannonPoint.getX(), y0= cannonPoint.getY();
        final int[][] DIRECTIONS={{1, 0}, {-1, 0},{0,1}, {0,-1}};

        return range;
    }
}
