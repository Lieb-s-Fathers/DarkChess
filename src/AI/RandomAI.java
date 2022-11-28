package AI;

//随机行走的AI

import chessComponent.EmptySlotComponent;
import chessComponent.SquareComponent;
import view.Chessboard;

import java.util.Random;

public class RandomAI extends AI {

    protected SquareComponent decideWhichToMove() {
        Random random = new Random();
        int row = random.nextInt(8), col = random.nextInt(4);
        boolean can = false;
        SquareComponent thisComponent = squareComponents[row][col];
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
        while (componentToMove instanceof EmptySlotComponent)
            componentToMove = decideWhichToMove();
        int[][] canMoveTopoints = getCanMovePoints(componentToMove);
        if (componentToMove.isReversal()) {
            System.out.println("fuck you");
        }
        int count = 1;
        while (canMoveTopoints[count][0] != -1) count++;
        Random random = new Random();
        if (componentToMove instanceof EmptySlotComponent)
            System.out.println("???????????????????");
        int iTomove = random.nextInt(count);
        return canMoveTopoints[iTomove];
    }
}
