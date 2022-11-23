package controller;


import chessComponent.SquareComponent;
import chessComponent.EmptySlotComponent;
import model.ChessColor;

import view.ChessGameFrame;
import view.Chessboard;



public class ClickController {
    private final Chessboard chessboard;
    private SquareComponent first;

    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public void onClick(SquareComponent squareComponent) {
        //判断第一次点击
        if (first == null) {
            if (handleFirst(squareComponent)) {
                squareComponent.setSelected(true);
                first = squareComponent;
                first.repaint();
            }
        } else {
            if (first == squareComponent) { // 再次点击取消选取
                squareComponent.setSelected(false);
                SquareComponent recordFirst = first;
                first = null;
                recordFirst.repaint();
            } else if (handleSecond(squareComponent)) {
                //repaint in swap chess method.
                chessboard.swapChessComponents(first, squareComponent);
                chessboard.clickController.swapPlayer();    //todo 添加定时功能
                if (squareComponent instanceof EmptySlotComponent) {
                    chessboard.clickController.printMessage(first.getChessColor().getName(), first.getName());
                } else {
                    int[][] killedComponent = chessboard.getKilledComponents();
                    int color = squareComponent.getChessColor() == ChessColor.BLACK ? 0 : 1;
                    printKilledComponents(killedComponent);
                    killedComponent[squareComponent.getStyle()][color]++;
                    chessboard.clickController.calculateScore();
                    chessboard.clickController.printMessage(first.getChessColor().getName(), first.getName(), squareComponent.getChessColor().getName(), squareComponent.getName());
                }
                first.setSelected(false);
                first = null;
            }
        }
    }

    private void printKilledComponents(int[][] killedComponent) {
        System.out.print("Black: ");
        for (int i = 0; i < 7; i++) System.out.printf("%d ", killedComponent[i][0]);
        System.out.println();
        System.out.print("Red: ");
        for (int i = 0; i < 7; i++) System.out.printf("%d ", killedComponent[i][1]);
        System.out.println();
    }

    /**
     * @param squareComponent 目标选取的棋子
     * @return 目标选取的棋子是否与棋盘记录的当前行棋方颜色相同
     */

    private boolean handleFirst(SquareComponent squareComponent) {
        if (!squareComponent.isReversal()) {
            squareComponent.setReversal(true);
            System.out.printf("onClick to reverse a chess [%d,%d]\n", squareComponent.getChessboardPoint().getX(), squareComponent.getChessboardPoint().getY());
            squareComponent.repaint();
            chessboard.clickController.swapPlayer();
            return false;
        }
        return squareComponent.getChessColor() == chessboard.getCurrentColor();
    }


    //显示合法走位的功能
    private int[][] getCanMovePoints() {
        int i = 0;
        int[][] canMovePoints = {{-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}};
        SquareComponent[][] squareComponents = chessboard.getChessComponents();
        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 4; y++) {
                if (first.canMoveTo(squareComponents, squareComponents[x][y].getChessboardPoint()) && squareComponents[x][y].getChessColor() != first.getChessColor()) {
                    canMovePoints[i][0] = x;
                    canMovePoints[i++][1] = y;
                }
            }
        }
        return canMovePoints;
    }

    /**
     * @param squareComponent first棋子目标移动到的棋子second
     * @return first棋子是否能够移动到second棋子位置
     */
    private boolean handleSecond(SquareComponent squareComponent) {
        if (first.getStyle() == 6) {
            return first.canMoveTo(chessboard.getChessComponents(), squareComponent.getChessboardPoint()) && ((!squareComponent.isReversal() && !(squareComponent instanceof EmptySlotComponent)) || (squareComponent.isReversal() && squareComponent.getChessColor() != chessboard.getCurrentColor()));
        } else {
            //没翻开或空棋子，进入if
            if (!squareComponent.isReversal()) {
                //没翻开且非空棋子不能走
                if (!(squareComponent instanceof EmptySlotComponent)) {
                    return false;
                }
            }
            return (squareComponent.getChessColor() != chessboard.getCurrentColor()) && first.canMoveTo(chessboard.getChessComponents(), squareComponent.getChessboardPoint());
        }
    }

    //todo 添加定时功能
    public void swapPlayer() {
        chessboard.setCurrentColor(chessboard.getCurrentColor() == ChessColor.BLACK ? ChessColor.RED : ChessColor.BLACK);
        ChessGameFrame.getStatusLabel().setText(String.format("%s's TURN", chessboard.getCurrentColor().getName()));
    }

    //计算分数
    public void calculateScore() {
        ChessGameFrame.getRedScoreLabel().setText("Red Score:    " + chessboard.getRedScore());
        ChessGameFrame.getBlackScoreLabel().setText("Black Score:    " + chessboard.getBlackScore());
    }

    public void printMessage(String color1, String component1) {
        ChessGameFrame.getMessageLabel().setText("move " + color1 + " " + component1);
    }


    public void printMessage(String color1, String component1, String color2, String component2) {
        ChessGameFrame.getMessageLabel().setText(color1 + " " + component1 + " eats " + color2 + " " + component2);
    }
}
