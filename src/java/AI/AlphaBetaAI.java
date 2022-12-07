package AI;

import chessComponent.EmptySlotComponent;
import chessComponent.SquareComponent;
import model.ChessColor;
import view.Chessboard;

public class AlphaBetaAI extends AI {
    public static final int[] componentsScore = {30, 10, 5, 5, 5, 1, 5, 0};
    public int originX, originY;
    public int directionX, directionY;
    public int deepth;
    public static int ABcut = 1000;
    SquareComponent[][] squareComponents = new SquareComponent[10][10];


    public AlphaBetaAI(Chessboard chessboard, int depth) {
        this.chessboard = chessboard;
        for (int x = 0; x < 8; x++)
            for (int y = 0; y < 4; y++)
                squareComponents[x][y] = chessboard.getChessComponents()[x][y];
        this.deepth = depth;
    }

    public int[] move() {
        int[] canMoveTopoints = new int[4];
        if (ABcut < 5000) {
            deepth++;
        }
        if (ABcut < 1000) {
            deepth++;
        }
        ABcut=0;
        dfs(0, -1000, 1000);
        canMoveTopoints[0] = directionX;
        canMoveTopoints[1] = directionY;
        canMoveTopoints[2] = originX;
        canMoveTopoints[3] = originY;
        return canMoveTopoints;
    }

    //depth初始需要为偶数
    public int dfs(int depth, int alpha, int beta) {
        if (depth == deepth) {
            int temp = getScore(chessboard.getCurrentColor() == ChessColor.BLACK ? 0 : 1);
            if (temp >= 0) return temp;
            else return -60;
        }
        //己方
        //再操作可以行走的棋子
        if (depth % 2 == 0) {
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 4; y++) {
                    SquareComponent thisComponent = squareComponents[x][y];
                    if (thisComponent instanceof EmptySlotComponent) continue;
                    int[][] canMovePoints = getCanMovePoints(thisComponent);
                    int temp = 0;
                    if (thisComponent.isReversal) {
                        if (thisComponent.getChessColor() != chessboard.getCurrentColor()) continue;
                        for (int i = 0; i < 4; i++) {
                            if (canMovePoints[i][0] == -1) break;
                            SquareComponent tempComponent = squareComponents[canMovePoints[i][0]][canMovePoints[i][1]];
                            squareComponents[canMovePoints[i][0]][canMovePoints[i][1]] = squareComponents[x][y];
                            squareComponents[x][y] = new EmptySlotComponent(thisComponent.getChessboardPoint(), thisComponent.getLocation(), null, 0);
                            temp = dfs(depth + 1, alpha, beta);
                            squareComponents[x][y] = squareComponents[canMovePoints[i][0]][canMovePoints[i][1]];
                            squareComponents[canMovePoints[i][0]][canMovePoints[i][1]] = tempComponent;
                            if (temp > alpha) {
                                alpha = temp;
                                if (depth == 0) {
                                    originX = x;
                                    directionX = canMovePoints[i][0];
                                    originY = y;
                                    directionY = canMovePoints[i][1];
                                }
                            }
                            if (alpha >= beta) {
                                ABcut++;
                                return alpha;
                            }
                        }
                    }
                }
            }
            //先尝试翻开新的棋子
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 4; y++) {
                    SquareComponent thisComponent = squareComponents[x][y];
                    if (thisComponent instanceof EmptySlotComponent) continue;
                    int temp = 0;
                    if (!thisComponent.isReversal()) {
                        thisComponent.isReversal = true;
                        temp = dfs(depth + 1, alpha, beta);
                        thisComponent.isReversal = false;
                        if (temp > alpha) {
                            alpha = temp;
                            if (depth == 0) {
                                originX = -1;
                                directionX = x;
                                originY = -1;
                                directionY = y;
                            }
                        }
                        if (alpha >= beta) {
                            ABcut++;
                            return alpha;
                        }
                    }
                }
            }
            if (alpha == 0) {
                for (int x = 0; x < 8; x++) {
                    for (int y = 0; y < 4; y++) {
                        SquareComponent thisComponent = squareComponents[x][y];
                        if (thisComponent instanceof EmptySlotComponent) continue;
                        int[][] canMovePoints = getCanMovePoints(thisComponent);
                        int temp = 0;
                        if (thisComponent.isReversal) {
                            if (thisComponent.getChessColor() != chessboard.getCurrentColor()) continue;
                            for (int i = 0; i < 4; i++) {
                                if (canMovePoints[i][0] == -1) break;
                                SquareComponent tempComponent = squareComponents[canMovePoints[i][0]][canMovePoints[i][1]];
                                squareComponents[canMovePoints[i][0]][canMovePoints[i][1]] = squareComponents[x][y];
                                squareComponents[x][y] = new EmptySlotComponent(thisComponent.getChessboardPoint(), thisComponent.getLocation(), null, 0);
                                temp = dfs(depth + 1, alpha, beta);
                                squareComponents[x][y] = squareComponents[canMovePoints[i][0]][canMovePoints[i][1]];
                                squareComponents[canMovePoints[i][0]][canMovePoints[i][1]] = tempComponent;
                                if (temp >= alpha) {
                                    alpha = temp;
                                    if (depth == 0) {
                                        originX = x;
                                        directionX = canMovePoints[i][0];
                                        originY = y;
                                        directionY = canMovePoints[i][1];
                                    }
                                }
                                if (alpha >= beta) {
                                    ABcut++;
                                    return alpha;
                                }
                            }
                        }
                    }
                }
            }

            return alpha;
        }
        //对方
        //先尝试翻开新的棋子
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 4; y++) {
                SquareComponent thisComponent = squareComponents[x][y];
                if (thisComponent instanceof EmptySlotComponent) continue;
                int temp = 0;
                if (!thisComponent.isReversal()) {
                    thisComponent.isReversal = true;
                    temp = dfs(depth + 1, alpha, beta);
                    thisComponent.isReversal = false;
                    if (temp < beta) {
                        beta = temp;
                    }
                    if (alpha >= beta) {
                        ABcut++;
                        return beta;
                    }
                }
            }
        }
        //再操作可以行走的棋子
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 4; y++) {
                SquareComponent thisComponent = squareComponents[x][y];
                if (thisComponent instanceof EmptySlotComponent) continue;
                int[][] canMovePoints = getCanMovePoints(thisComponent);
                int temp = 0;
                if (thisComponent.isReversal()) {
                    if (thisComponent.getChessColor() == chessboard.getCurrentColor()) continue;
                    for (int i = 0; i < 4; i++) {
                        if (canMovePoints[i][0] == -1) break;
                        SquareComponent tempComponent = squareComponents[canMovePoints[i][0]][canMovePoints[i][1]];
                        squareComponents[canMovePoints[i][0]][canMovePoints[i][1]] = squareComponents[x][y];
                        squareComponents[x][y] = new EmptySlotComponent(thisComponent.getChessboardPoint(), thisComponent.getLocation(), null, 0);
                        temp = dfs(depth + 1, alpha, beta);
                        squareComponents[x][y] = squareComponents[canMovePoints[i][0]][canMovePoints[i][1]];
                        squareComponents[canMovePoints[i][0]][canMovePoints[i][1]] = tempComponent;
                        if (temp < beta) {
                            beta = temp;
                        }
                        if (alpha >= beta) {
                            ABcut++;
                            return beta;
                        }
                    }
                }
            }
        }
        return beta;
    }


    //计算分数
    public int getScore(int color) {
        int[] scores = new int[2];
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 4; y++) {
                SquareComponent thisComponent = squareComponents[x][y];
                if (!(thisComponent instanceof EmptySlotComponent)) {
                    if (thisComponent.getChessColor() == ChessColor.BLACK) {
                        scores[0] += componentsScore[thisComponent.getStyle()];
                    } else if (thisComponent.getChessColor() == ChessColor.RED) {
                        scores[1] += componentsScore[thisComponent.getStyle()];
                    }
                }
            }
        }
        int temp = 100 - scores[0];
        scores[0] = 100 - scores[1];
        scores[1] = temp;
        if (color == 0) {
            return scores[0] - scores[1];
        } else {
            return scores[1] - scores[0];
        }
    }
}
