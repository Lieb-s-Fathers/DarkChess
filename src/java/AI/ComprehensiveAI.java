package AI;

import chessComponent.EmptySlotComponent;
import chessComponent.SquareComponent;
import model.ChessColor;
import view.Chessboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ComprehensiveAI extends AI {
    public static final int[] componentsScore = {30, 10, 5, 5, 5, 1, 5, 0};
    public int originX, originY;
    public int directionX, directionY;
    public int deepth;
    public int ABcut = 0;
    int intenal = 0;
    SquareComponent[][] squareComponents = new SquareComponent[10][10];


    public ComprehensiveAI(Chessboard chessboard, int depth) {
        this.chessboard = chessboard;
        for (int x = 0; x < 8; x++)
            for (int y = 0; y < 4; y++)
                squareComponents[x][y] = chessboard.getChessComponents()[x][y];
        this.deepth = depth;
    }

    public int[] move() {
        int[] canMoveTopoints = new int[4];

        deepth += (32 - counter()) / 10;
        dfs(0, -100, 100);
        canMoveTopoints[0] = directionX;
        canMoveTopoints[1] = directionY;
        canMoveTopoints[2] = originX;
        canMoveTopoints[3] = originY;
        if (squareComponents[directionX][directionY].getStyle() == 0)
            return canMoveTopoints;
        //优先吃对方的将
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 4; col++) {
                SquareComponent thisComponent = squareComponents[row][col];
                //找到对方的将
                if (thisComponent.getStyle() == 0 && thisComponent.getChessColor() != chessboard.getCurrentColor()) {
                    //查看对方的将附近有无兵
                    int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
                    for (int i = 0; i < 4; i++) {
                        if (row + directions[i][0] >= 0 && col + directions[i][1] >= 0 && row + directions[i][0] <= 7 && col + directions[i][1] <= 3) {
                            if (squareComponents[row + directions[i][0]][col + directions[i][1]].getStyle() == 5 && squareComponents[row + directions[i][0]][col + directions[i][1]].getChessColor() == chessboard.getCurrentColor()) {
                                SquareComponent goodSoldier = squareComponents[row + directions[i][0]][col + directions[i][1]];
                                if (!thisComponent.isReversal()) {
                                    System.out.println("翻出对方将");
                                    canMoveTopoints[0] = row;
                                    canMoveTopoints[1] = col;
                                    canMoveTopoints[2] = -1;
                                    canMoveTopoints[3] = -1;
                                    return canMoveTopoints;
                                } else if (goodSoldier.isReversal) {
                                    canMoveTopoints[0] = row;
                                    canMoveTopoints[1] = col;
                                    canMoveTopoints[2] = row + directions[i][0];
                                    canMoveTopoints[3] = col + directions[i][1];
                                    return canMoveTopoints;
                                } else if (!goodSoldier.isReversal) {
                                    canMoveTopoints[0] = row + directions[i][0];
                                    canMoveTopoints[1] = col + directions[i][1];
                                    canMoveTopoints[2] = -1;
                                    canMoveTopoints[3] = -1;
                                    return canMoveTopoints;
                                }
                            }
                        }
                    }
                }
            }
        }
        return canMoveTopoints;
    }

    //depth初始需要为偶数
    //depth初始需要为偶数
    public int dfs(int depth, int alpha, int beta) {
        if (depth == deepth) {
            return getScore(chessboard.getCurrentColor() == ChessColor.BLACK ? 0 : 1);
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
                    if (thisComponent.isReversal()) {
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
                                    originX = canMovePoints[i][2];
                                    directionX = canMovePoints[i][0];
                                    originY = canMovePoints[i][3];
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

        else {
            //尝试翻开
            Random random = new Random();
            List<String> UnreveraledComponents = countUnreversaledComponents();
            if (UnreveraledComponents.size() != 0) {
                int r = random.nextInt(UnreveraledComponents.size());
                int x = Integer.parseInt(String.valueOf(UnreveraledComponents.get(r).charAt(0)));
                int y = Integer.parseInt(String.valueOf(UnreveraledComponents.get(r).charAt(2)));
                SquareComponent thisComponent = squareComponents[x][y];
                int temp = 0;
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
            //再操作可以行走的棋子
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 4; y++) {
                    SquareComponent thisComponent = squareComponents[x][y];
                    if (thisComponent instanceof EmptySlotComponent) continue;
                    int[][] canMovePoints = dsgetCanMovePoints(thisComponent);
                    int temp = 0;
                    if (thisComponent.isReversal()) {
                        if (thisComponent.getChessColor() == chessboard.getCurrentColor()) continue;
                        for (int i = 0; i < 4; i++) {
                            if (canMovePoints[i][0] == -1) break;
                            if (thisComponent.getStyle() == 6 && !squareComponents[canMovePoints[i][0]][canMovePoints[i][1]].isReversal)
                                continue;
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

    //统计剩余棋子个数
    public int counter() {
        int count = 0;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 4; y++) {
                SquareComponent thisComponent = squareComponents[x][y];
                if (!(thisComponent instanceof EmptySlotComponent)) {
                    count++;
                }
            }
        }
        return count;
    }

    public List<String> countUnreversaledComponents() {
        List<String> UnreveraledComponents = new ArrayList<>();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 4; y++) {
                SquareComponent thisComponent = squareComponents[x][y];
                if (!(thisComponent instanceof EmptySlotComponent) && !thisComponent.isReversal()) {
                    UnreveraledComponents.add(String.format("%d %d", x, y));
                }
            }
        }
        return UnreveraledComponents;
    }

    public int[][] dsgetCanMovePoints(SquareComponent squareComponent) {
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
}
