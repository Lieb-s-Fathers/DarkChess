package AI;

//优先吃子的AI

import chessComponent.EmptySlotComponent;
import chessComponent.SquareComponent;
import view.Chessboard;

import java.util.Random;

public class GreedyAI extends AI {

    protected SquareComponent decideWhichToMove() {
        Random random = new Random();
        int row = random.nextInt(8), col = random.nextInt(4);
        boolean can = false;
        SquareComponent thisComponent = squareComponents[row][col];

        //优先挑选出已经翻开的棋子

        int k = 0;
        while (k++ < 100000) {
            row = random.nextInt(8);
            col = random.nextInt(4);
            thisComponent = squareComponents[row][col];
            if (thisComponent instanceof EmptySlotComponent && !thisComponent.isReversal()) continue;
            int[][] canMoveTopoints = getCanMovePoints(thisComponent);
            for (int i = 0; i < 4; i++) {
                if (canMoveTopoints[i][0] != -1 || canMoveTopoints[i][1] != -1) {
                    can = true;
                    break;
                }
            }
            if (can) break;
        }
        boolean temp = thisComponent.isReversal() && thisComponent.getChessColor() == chessboard.getCurrentColor() && !(thisComponent instanceof EmptySlotComponent) && can;
        if (temp)
            return thisComponent;

        //优先翻出炮
        if (random.nextInt(2) == 0) {
            for (row = 0; row < 8; row++) {
                for (col = 0; col < 4; col++) {
                    thisComponent = squareComponents[row][col];
                    if (!thisComponent.isReversal() && thisComponent.getStyle() == 6)
                        return thisComponent;
                }
            }
        }
        row = random.nextInt(8);
        col = random.nextInt(4);
        thisComponent = squareComponents[row][col];
        //否则任意挑选棋子
        while (thisComponent.isReversal() && ((thisComponent instanceof EmptySlotComponent) || thisComponent.getChessColor() != chessboard.getCurrentColor() || !can)) {
            row = random.nextInt(8);
            col = random.nextInt(4);
            can = false;
            thisComponent = squareComponents[row][col];
            if (thisComponent instanceof EmptySlotComponent) continue;
            int[][] canMoveTopoints = getCanMovePoints(thisComponent);
            for (int i = 0; i < 4; i++) {
                if (canMoveTopoints[i][0] != -1 || canMoveTopoints[i][1] != -1) {
                    can = true;
                    break;
                }
            }
        }
        return thisComponent;
    }

    public int[] move(Chessboard chessboard) {
        this.chessboard = chessboard;
        loadChessboard(chessboard);
        SquareComponent componentToMove = decideWhichToMove();
        while (componentToMove instanceof EmptySlotComponent) componentToMove = decideWhichToMove();
        if (componentToMove.isReversal()) {
            int[][] canMoveTopoints = getCanMovePoints(componentToMove);
            if (componentToMove.isReversal()) {
                System.out.println("fuck you");
            }
            int count = 1;
            while (canMoveTopoints[count][0] != -1) count++;
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
