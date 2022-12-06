package AI;

import chessComponent.EmptySlotComponent;
import chessComponent.SquareComponent;
import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;
import view.Chessboard;

import java.awt.*;

public class AlphaBetaAI extends AI {

    public int[][] possiblePoints = new int[32][2];
    public int originX, originY;
    public int directionX, directionY;

    public int depth;

    public EmptySlotComponent emptySlotComponent = new EmptySlotComponent(new ChessboardPoint(1,1), new Point(1,1), new ClickController(chessboard), 0);

    public AlphaBetaAI(Chessboard chessboard, int depth) {
        this.chessboard = chessboard;
        this.squareComponents = chessboard.getChessComponents();
        this.depth = depth;
    }

    public int[] move() {
        int[] canMoveTopoints = new int[4];
        dfs(depth, 0, 100);
        canMoveTopoints[0] = directionX;
        canMoveTopoints[1] = directionY;
        canMoveTopoints[2] = originX;
        canMoveTopoints[3] = originY;
        return canMoveTopoints;
    }

    //depth初始需要为偶数
    public int dfs(int depth, int alpha, int beta) {
        if (depth == 0) {
            return getScore(chessboard.getCurrentColor() == ChessColor.BLACK ? 0 : 1);
        }
        //己方
        if (depth % 2 == 0) {
            //对所有可能移动的棋子进行操作max
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 4; y++) {
                    SquareComponent thisComponent = squareComponents[x][y];
                    if (thisComponent instanceof EmptySlotComponent || thisComponent.getChessColor() != chessboard.getCurrentColor())
                        continue;
                    int[][] canMovePoints = getCanMovePoints(thisComponent);
                    int temp = 0;
                    if (!thisComponent.isReversal()) {
                        thisComponent.isReversal = true;
                        temp = dfs(depth - 1, alpha, beta);
                        if (temp > alpha) {
                            originX = -1;
                            directionX = x;
                            originY = -1;
                            directionY = y;
                            alpha = temp;
                        }
                        thisComponent.isReversal = false;
                    } else {
                        for (int i = 0; i < 4; i++) {
                            if (canMovePoints[i][0] == -1) break;
                            SquareComponent tempComponent = squareComponents[canMovePoints[i][0]][canMovePoints[i][1]];
                            squareComponents[canMovePoints[i][0]][canMovePoints[i][1]] = squareComponents[x][y];
                            squareComponents[x][y] = new EmptySlotComponent(thisComponent.getChessboardPoint(), thisComponent.getLocation(), null, 0);
                            temp = dfs(depth - 1, alpha, beta);
                            if (temp > alpha) {
                                originX = x;
                                directionX = canMovePoints[i][0];
                                originY = y;
                                directionY = canMovePoints[i][1];
                                alpha = temp;
                            }
                            squareComponents[x][y] = squareComponents[canMovePoints[i][0]][canMovePoints[i][1]];
                            squareComponents[canMovePoints[i][0]][canMovePoints[i][1]] = tempComponent;
                        }
                    }
                }
            }
            return alpha;
        }
        //对方min
        else {
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 4; y++) {
                    SquareComponent thisComponent = squareComponents[x][y];
                    if (thisComponent instanceof EmptySlotComponent || thisComponent.getChessColor() == chessboard.getCurrentColor())
                        continue;
                    int[][] canMovePoints = getCanMovePoints(thisComponent);
                    int temp = 0;
                    if (!thisComponent.isReversal()) {
                        thisComponent.isReversal = true;
                        temp = dfs(depth - 1, alpha, beta);
                        if (temp < beta) {
                            originX = -1;
                            directionX = x;
                            originY = -1;
                            directionY = y;
                            beta = temp;
                        }
                        thisComponent.isReversal = false;
                    } else {
                        for (int i = 0; i < 4; i++) {
                            if (canMovePoints[i][0] == -1) break;
                            SquareComponent tempComponent = squareComponents[canMovePoints[i][0]][canMovePoints[i][1]];
                            squareComponents[canMovePoints[i][0]][canMovePoints[i][1]] = squareComponents[x][y];
                            squareComponents[x][y] = new EmptySlotComponent(thisComponent.getChessboardPoint(), thisComponent.getLocation(), null, 0);
                            temp = dfs(depth - 1, alpha, beta);
                            if (temp < beta) {
                                originX = x;
                                directionX = canMovePoints[i][0];
                                originY = y;
                                directionY = canMovePoints[i][1];
                                beta = temp;
                            }
                            squareComponents[x][y] = squareComponents[canMovePoints[i][0]][canMovePoints[i][1]];
                            squareComponents[canMovePoints[i][0]][canMovePoints[i][1]] = tempComponent;
                        }
                    }
                }
            }
            return beta;
        }
    }


    //计算分数
    public int getScore(int color) {
        int[] scores = new int[2];
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 4; y++) {
                SquareComponent thisComponent = squareComponents[x][y];
                if (!(thisComponent instanceof EmptySlotComponent)) {
                    if (thisComponent.getChessColor() == ChessColor.BLACK) {
                        scores[0] += Chessboard.componentsScore[thisComponent.getStyle()];
                    } else if (thisComponent.getChessColor() == ChessColor.RED) {
                        scores[1] += Chessboard.componentsScore[thisComponent.getStyle()];
                    }
                }
            }
        }
        int temp = 95 - scores[0];
        scores[0] = 95 - scores[1];
        scores[1] = temp;
        if (color == 0) {
            return scores[0] - scores[1];
        } else {
            return scores[1] - scores[0];
        }
    }
}
