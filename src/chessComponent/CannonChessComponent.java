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
        int[][] range = getRange(thisPoint, chessboard);
        int[] destinationChessXY = {destination.getX(), destination.getY()};
        boolean inRange = checkRange(destinationChessXY, range);
        boolean canMove = canEat[this.style][destinationChess.style];
        return canMove && inRange;
    }

    public int[][] getRange(ChessboardPoint cannonPoint, SquareComponent[][] chessboard) {
        int[][] range = new int[4][2];
        int x0 = cannonPoint.getX(), y0 = cannonPoint.getY();
        final int[][] DIRECTIONS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int direction = 0; direction < 4; direction++) {
            int x = x0, y = y0;
            boolean flag = false;
            x += DIRECTIONS[direction][0];
            y += DIRECTIONS[direction][1];
            while (x >= 0 && x <= 7 && y >= 0 && y <= 3) {
                SquareComponent component = chessboard[x][y];
                if (!(component instanceof EmptySlotComponent) && flag) {
                    range[direction][0] = x;
                    range[direction][1] = y;
                    break;
                } else if (!(component instanceof EmptySlotComponent)) {
                    flag = true;
                }
                x += DIRECTIONS[direction][0];
                y += DIRECTIONS[direction][1];
            }
        }
        return range;
    }
}
