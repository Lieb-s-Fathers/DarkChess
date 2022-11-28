package AI;

import chessComponent.SquareComponent;
import model.ChessColor;
import view.Chessboard;

public class AI {
    static SquareComponent[][] squareComponents;
    Chessboard chessboard;


    //获取当前所有可以移动的棋子 (利用 chessboard)
    public int[][] getCanMovePoints(SquareComponent squareComponent) {
        int i = 0;
        int[][] canMovePoints = {{-1, -1, -1, -1}, {-1, -1, -1, -1}, {-1, -1, -1, -1}, {-1, -1, -1, -1}, {-1, -1, -1, -1}};
        SquareComponent[][] squareComponents = chessboard.getChessComponents();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 4; y++) {
                if (squareComponent.canMoveTo(squareComponents, squareComponents[x][y].getChessboardPoint()) && squareComponents[x][y].getChessColor() != squareComponent.getChessColor()) {
                    canMovePoints[i][0] = x;
                    canMovePoints[i++][1] = y;
                }
            }
        }
        for (int j = 0; j < 4; j++) {
            canMovePoints[j][2] = squareComponent.getChessboardPoint().getX();
            canMovePoints[j][3] = squareComponent.getChessboardPoint().getY();
        }
        return canMovePoints;
    }


    //获取ai将要操作的点坐标
    public int[] move() {
        int[] canMoveTopoints=new int[10];
        return canMoveTopoints;
    }
    //获取当前所有可以移动的棋子 (利用 components 和 colors)

//    public int[][] getCanMovePoints() {
//        int i = 0;
//        int[][] canMovePoints = {{-1, -1, -1, -1}, {-1, -1, -1, -1}, {-1, -1, -1, -1}, {-1, -1, -1, -1}, {-1, -1, -1, -1}};
//        SquareComponent[][] squareComponents = chessboard.getChessComponents();
//        for (int x = 0; x < 7; x++) {
//            for (int y = 0; y < 4; y++) {
//                if (squareComponent.canMoveTo(squareComponents, squareComponents[x][y].getChessboardPoint())) {
//                    canMovePoints[i][0] = x;
//                    canMovePoints[i++][1] = y;
//                }
//            }
//        }
//        if (!squareComponent.isReversal()) {
//            canMovePoints[0][0] = squareComponent.getChessboardPoint().getX();
//            canMovePoints[0][1] = squareComponent.getChessboardPoint().getY();
//        }
//        return canMovePoints;
//    }

    //
}
