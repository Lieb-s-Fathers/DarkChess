package controller;


import AI.AIController;
import chessComponent.CannonChessComponent;
import chessComponent.SquareComponent;
import chessComponent.EmptySlotComponent;
import model.ChessColor;
import model.ChessboardPoint;
import view.ChessGameFrame;
import view.Chessboard;
import view.Winboard;

import java.util.ArrayList;

public class ClickController {
    private int steps = 0;
    private final int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private final Chessboard chessboard;
    private SquareComponent first;
    private ArrayList<SquareComponent> next = new ArrayList<>();
    private WriteController writeController;

    private AIController aiFucker;


    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
        writeController = new WriteController(chessboard);
        aiFucker = new AIController(chessboard);
    }

    public void onClick(SquareComponent squareComponent) {
        //判断第一次点击
        if (first == null) {
            if (handleFirst(squareComponent)) {
                squareComponent.setSelected(true);
                first = squareComponent;
                first.repaint();
                if (!(first instanceof CannonChessComponent)) {
                    ChessboardPoint firstPoint = first.getChessboardPoint();
                    int xx = firstPoint.getX();
                    int yy = firstPoint.getY();
                    for (int i = 0; i < 4; i++) {
                        try {
                            ChessboardPoint chessboardPoint = new ChessboardPoint(xx + directions[i][0], yy + directions[i][1]);
                            if (first.canMoveTo(chessboard.getChessComponents(), chessboardPoint)) {
                                next.add(chessboard.getChessComponents()[chessboardPoint.getX()][chessboardPoint.getY()]);
                            }
                        } catch (ArrayIndexOutOfBoundsException ignored) {}
                    }
                    next.forEach((c) -> {
                        c.setCanBeEaten(true);
                        c.repaint();
                    });
                } else {
                    for (SquareComponent[] chessComponents : chessboard.getChessComponents()) {
                        for (SquareComponent chessComponent : chessComponents) {
                            try {
                                ChessboardPoint chessboardPoint = chessComponent.getChessboardPoint();
                                if (first.canMoveTo(chessboard.getChessComponents(), chessboardPoint)) {
                                    next.add(chessboard.getChessComponents()[chessboardPoint.getX()][chessboardPoint.getY()]);
                                }
                                next.forEach((c) -> {
                                    c.setCanBeEaten(true);
                                    c.repaint();
                                });
                            } catch (ArrayIndexOutOfBoundsException ignored) {}
                        }
                    }
                }

            }
        } else {
            if (first == squareComponent) { // 再次点击取消选取
                squareComponent.setSelected(false);
                first.repaint();
                first = null;

                next.forEach((c) -> {
                    c.setCanBeEaten(false);
                    c.repaint();
                });
                next = new ArrayList<>();
            } else if (handleSecond(squareComponent)) {
                next.forEach((c) ->{
                    c.setCanBeEaten(false);
                    c.repaint();
                });
                next = new ArrayList<>();

                //repaint in swap chess method.
                chessboard.swapChessComponents(first, squareComponent);
                chessboard.clickController.swapPlayer();
                //todo 添加定时功能
                if (squareComponent instanceof EmptySlotComponent) {
                    chessboard.clickController.printMessage(first.getChessColor().getName(), first.getName());
                } else {
                    chessboard.printKilledComponents();
                    chessboard.clickController.printMessage(first.getChessColor().getName(), first.getName(), squareComponent.getChessColor().getName(), squareComponent.getName());
                    chessboard.clickController.calculateScore();
                    steps++;
                    chessboard.addChessBoardData();
                    writeController.save();
                    winJudge();
                }
                first.setSelected(false);
                first = null;
//                AIPlay aiPlay=new AIPlay(chessboard);
//                aiPlay.start();
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
            }
        }
    }



    /**
     * @param squareComponent 目标选取的棋子
     * @return 目标选取的棋子是否与棋盘记录的当前行棋方颜色相同
     */

    private boolean handleFirst(SquareComponent squareComponent) {
        if (!squareComponent.isReversal()&&!(squareComponent instanceof EmptySlotComponent)) {
            squareComponent.setReversal(true);
            System.out.printf("onClick to reverse a chess [%d,%d]\n", squareComponent.getChessboardPoint().getX(), squareComponent.getChessboardPoint().getY());
            writeController.save();
            chessboard.addChessBoardData();
            squareComponent.repaint();
            chessboard.clickController.swapPlayer();
//            AIPlay aiPlay=new AIPlay(chessboard);
//            aiPlay.start();
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
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
                if (first.canMoveTo(squareComponents, squareComponents[x][y].getChessboardPoint())) {
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
            return first.canMoveTo(chessboard.getChessComponents(), squareComponent.getChessboardPoint());
        } else {
            //没翻开或空棋子，进入if
            if (!squareComponent.isReversal()) {
                //没翻开且非空棋子不能走
                if (!(squareComponent instanceof EmptySlotComponent)) {
                    return false;
                }
            }
            return first.canMoveTo(chessboard.getChessComponents(), squareComponent.getChessboardPoint());
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

    private void printMessage(String color1, String component1) {
        ChessGameFrame.getMessageLabel().setText("move " + color1 + " " + component1);
    }


    private void printMessage(String color1, String component1, String color2, String component2) {
        ChessGameFrame.getMessageLabel().setText(color1 + " " + component1 + " eats " + color2 + " " + component2);
    }

    public void winJudge(){
        if (chessboard.getRedScore() >= 60){
            Winboard.setWinText("Red");
            ChessGameFrame.winboard.setAlwaysOnTop(true);
            ChessGameFrame.winboard.setVisible(true);
        }

        if (chessboard.getBlackScore() >= 60){
            Winboard.setWinText("Black");
            ChessGameFrame.winboard.setAlwaysOnTop(true);
            ChessGameFrame.winboard.setVisible(true);
        }
    }
}
