package AI;

//优先吃子的AI

import chessComponent.EmptySlotComponent;
import chessComponent.SquareComponent;
import view.Chessboard;

import java.util.Random;

public class GreedyAI extends AI {
    private int difficulty = 5;

    public GreedyAI(Chessboard chessboard, int difficulty) {
        this.difficulty = difficulty;
        this.chessboard = chessboard;
        this.squareComponents = chessboard.getChessComponents();
    }

    public GreedyAI() {

    }

    protected SquareComponent decideWhichToMove() {
        Random random = new Random();
        int row = random.nextInt(8), col = random.nextInt(4);
        boolean can = false;
        SquareComponent thisComponent = squareComponents[row][col];

        if (difficulty == 10) {

            //优先吃对方的将
            for (row = 0; row < 8; row++) {
                for (col = 0; col < 4; col++) {
                    thisComponent = squareComponents[row][col];
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
                                        return thisComponent;
                                    } else {
                                        return goodSoldier;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


        //操纵已翻开的棋子吃子
        for (row = 0; row < 8; row++) {
            for (col = 0; col < 4; col++) {
                thisComponent = squareComponents[row][col];
                if (thisComponent.isReversal() &&
                        thisComponent.getChessColor() == chessboard.getCurrentColor() &&
                        !(thisComponent instanceof EmptySlotComponent)) {
                    int[][] canMoveTopoints = getCanMovePoints(thisComponent);
                    for (int i = 0; i < 4; i++) {
                        if ((canMoveTopoints[i][0] != -1 || canMoveTopoints[i][1] != -1) &&
                                !(squareComponents[canMoveTopoints[i][0]][canMoveTopoints[i][1]] instanceof EmptySlotComponent)) {
                            can = true;
                            break;
                        }
                    }
                    if (can) {
                        System.out.println("吃子");
                        return thisComponent;
                    }
                }
            }
        }


        //优先翻出炮
        if (random.nextInt(10) < difficulty) {
            for (row = 0; row < 8; row++) {
                for (col = 0; col < 4; col++) {
                    thisComponent = squareComponents[row][col];
                    if (!thisComponent.isReversal() && thisComponent.getStyle() == 6 &&
                            thisComponent.getChessColor() == chessboard.getCurrentColor()) {
                        System.out.println("翻出炮");
                        return thisComponent;
                    }
                }
            }
        }


        //随机翻开棋子
        int k = 0;
        while (k++ < 1000) {
            row = random.nextInt(8);
            col = random.nextInt(4);
            thisComponent = squareComponents[row][col];
            if (!(thisComponent instanceof EmptySlotComponent)) {
                if (difficulty >= 8) {
                    if (!thisComponent.isReversal() && thisComponent.getChessColor() == chessboard.getCurrentColor()) {
                        System.out.println("随机翻开棋子");
                        return thisComponent;
                    }
                } else {
                    if (random.nextInt(10) < difficulty) {
                        if (!thisComponent.isReversal() && thisComponent.getChessColor() == chessboard.getCurrentColor()) {
                            System.out.println("随机翻开棋子");
                            return thisComponent;
                        }
                    } else {
                        if (!thisComponent.isReversal()) {
                            System.out.println("随机翻开棋子");
                            return thisComponent;
                        }
                    }
                }
            }
        }


        //移动已经翻开的棋子
        for (row = 0; row < 8; row++) {
            for (col = 0; col < 4; col++) {
                thisComponent = squareComponents[row][col];
                if (thisComponent.isReversal() &&
                        thisComponent.getChessColor() == chessboard.getCurrentColor() &&
                        !(thisComponent instanceof EmptySlotComponent)) {
                    int[][] canMoveTopoints = getCanMovePoints(thisComponent);
                    for (int i = 0; i < 4; i++) {
                        if (canMoveTopoints[i][0] != -1 || canMoveTopoints[i][1] != -1) {
                            can = true;
                            break;
                        }
                    }
                    if (can) {
                        System.out.println("移动");
                        return thisComponent;
                    }
                }
            }
        }

        return thisComponent;
//        int k = 0;
//        while (k++ < 100000) {
//            row = random.nextInt(8);
//            col = random.nextInt(4);
//            thisComponent = squareComponents[row][col];
//            if (thisComponent instanceof EmptySlotComponent &&
//            !thisComponent.isReversal()) continue;
//            int[][] canMoveTopoints = getCanMovePoints(thisComponent);
//            for (int i = 0; i < 4; i++) {
//                if (canMoveTopoints[i][0] != -1 || canMoveTopoints[i][1] != -1) {
//                    can = true;
//                    break;
//                }
//            }
//            if (can) break;
//        }
//        boolean temp = thisComponent.isReversal() && thisComponent.getChessColor() == chessboard.getCurrentColor() && !(thisComponent instanceof EmptySlotComponent) && can;
//        if (temp)
//            return thisComponent;


    }

    public int[] move() {
        SquareComponent componentToMove = decideWhichToMove();
        while (componentToMove instanceof EmptySlotComponent) componentToMove = decideWhichToMove();
        if (componentToMove.isReversal()) {
            int[][] canMoveTopoints = getCanMovePoints(componentToMove);
            if (componentToMove.isReversal()) {
                System.out.println("fuck you");
            }
            for (int iTomove = 0; iTomove < 4; iTomove++) {
                if ((canMoveTopoints[iTomove][0] != -1 || canMoveTopoints[iTomove][1] != -1) &&
                        !(squareComponents[canMoveTopoints[iTomove][0]][canMoveTopoints[iTomove][1]] instanceof EmptySlotComponent)) {
                    return canMoveTopoints[iTomove];
                }
            }
            int count = 1;
            while (canMoveTopoints[count][0] != -1) count++;
            int maxx = 10, maxxi = 0;
            for (int i = 0; i < count; i++) {
                if (squareComponents[canMoveTopoints[i][0]][canMoveTopoints[i][1]].getStyle() < maxx) {
                    maxx = squareComponents[canMoveTopoints[i][0]][canMoveTopoints[i][1]].getStyle();
                    maxxi = i;
                }
            }
            Random random = new Random();
            int iTomove = random.nextInt(count);
            return canMoveTopoints[iTomove];
        } else {
            int[] canMoveTopoints = new int[10];
            canMoveTopoints[2] = componentToMove.getChessboardPoint().getX();
            canMoveTopoints[3] = componentToMove.getChessboardPoint().getY();
            canMoveTopoints[0] = canMoveTopoints[1] = -1;
            return canMoveTopoints;
        }
    }
}
