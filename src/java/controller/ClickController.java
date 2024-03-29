package controller;


import AI.AIController;
import chessComponent.CannonChessComponent;
import chessComponent.EmptySlotComponent;
import chessComponent.SquareComponent;
import model.ChessColor;
import model.ChessboardPoint;
import view.ChessGameFrame;
import view.Chessboard;
import view.FatherFrame;
import view.Winboard;

import javax.swing.*;
import java.util.ArrayList;

import static view.ChessGameFrame.countDown;
import static view.StartMenuFrame.mainFrame;

public class ClickController {
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
        if (chessboard.getCanClick()) {
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
                            } catch (ArrayIndexOutOfBoundsException ignored) {
                            }
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
                                } catch (ArrayIndexOutOfBoundsException ignored) {
                                }
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
                    next.forEach((c) -> {
                        c.setCanBeEaten(false);
                        c.repaint();
                    });
                    next = new ArrayList<>();

                    //repaint in swap chess method.
                    chessboard.swapChessComponents(first, squareComponent);
                    swapPlayer();
                    if (squareComponent instanceof EmptySlotComponent) {
                        printMessage(first.getChessColor().getName(), first.getName());
                    } else {
                        chessboard.printKilledComponents();
                        printMessage(first.getChessColor().getName(), first.getName(), squareComponent.getChessColor().getName(), squareComponent.getName());
                        calculateScore(mainFrame);
                        winJudge();
                    }
                    chessboard.addChessBoardData();
                    writeController.save();

                    first.setSelected(false);
                    first = null;
                }
            }
        } else if (chessboard.getIsCheating()) {
            JOptionPane.showMessageDialog(mainFrame, "请退出作弊模式后再点击！");
        }
    }

    public void setCanClick(boolean canClick) {
        chessboard.setCanClick(canClick);
    }

    public void setIsCheating(boolean isCheating) {
        chessboard.setIsCheating(isCheating);
    }

    public void setIsReversal(boolean isReversal) {
        for (SquareComponent[] chessComponents : chessboard.getChessComponents()) {
            for (SquareComponent chesssComponent : chessComponents) {
                chesssComponent.setReversal(true);
                chesssComponent.repaint();
            }
        }
    }


    /**
     * @param squareComponent 目标选取的棋子
     * @return 目标选取的棋子是否与棋盘记录的当前行棋方颜色相同
     */

    private boolean handleFirst(SquareComponent squareComponent) {
        if (!squareComponent.isReversal() && !(squareComponent instanceof EmptySlotComponent)) {
            squareComponent.setReversal(true);
            System.out.printf("onClick to reverse a chess [%d,%d]\n", squareComponent.getChessboardPoint().getX(), squareComponent.getChessboardPoint().getY());
            chessboard.addChessBoardData();
            writeController.save();
            squareComponent.repaint();
            swapPlayer();
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

    public void swapPlayer() {
        chessboard.setCurrentColor(chessboard.getCurrentColor() == ChessColor.BLACK ? ChessColor.RED : ChessColor.BLACK);
        ChessGameFrame.getStatusLabel().setText(String.format("%s's TURN", chessboard.getCurrentColor().getName()));
        countDown.restart();
    }

    //计算分数
    public void calculateScore(FatherFrame thisFrame) {
        thisFrame.getRedScoreLabel().setText("Red Score:     " + chessboard.getRedScore());
        thisFrame.getBlackScoreLabel().setText("Black Score:   " + chessboard.getBlackScore());

        int[][] killedComponents = chessboard.getkilledComponents();

        for (int i = 0; i < 7; i++) {
            if (killedComponents[0][i] != 0) {
                SquareComponent chessComponent = thisFrame.getEatenBlackChesses().getChessComponents()[i][0];
                chessComponent.setVisible(true);
                chessComponent.repaint();
            } else {
                SquareComponent chessComponent = thisFrame.getEatenBlackChesses().getChessComponents()[i][0];
                chessComponent.setVisible(false);
                chessComponent.repaint();
            }

            if (killedComponents[0][i] >= 2) {
                SquareComponent chessComponent = thisFrame.getEatenBlackChesses().getChessComponents()[i][1];
                chessComponent.setVisible(true);
                chessComponent.setName("x" + killedComponents[0][i]);
                chessComponent.repaint();
            } else {
                SquareComponent chessComponent = thisFrame.getEatenBlackChesses().getChessComponents()[i][1];
                chessComponent.setVisible(false);
                chessComponent.repaint();
            }


            if (killedComponents[1][i] != 0) {
                SquareComponent chessComponent = thisFrame.getEatenRedChesses().getChessComponents()[i][0];
                chessComponent.setVisible(true);
                chessComponent.repaint();
            } else {
                SquareComponent chessComponent = thisFrame.getEatenRedChesses().getChessComponents()[i][0];
                chessComponent.setVisible(false);
                chessComponent.repaint();
            }

            if (killedComponents[1][i] >= 2) {
                SquareComponent chessComponent = thisFrame.getEatenRedChesses().getChessComponents()[i][1];
                chessComponent.setVisible(true);
                chessComponent.setName("x" + killedComponents[1][i]);
                chessComponent.repaint();
            } else {
                SquareComponent chessComponent = thisFrame.getEatenRedChesses().getChessComponents()[i][1];
                chessComponent.setVisible(false);
                chessComponent.repaint();
            }
        }
    }

    private void printMessage(String color1, String component1) {
        ChessGameFrame.getMessageLabel().setText("move " + color1 + " " + component1);
    }


    private void printMessage(String color1, String component1, String color2, String component2) {
        ChessGameFrame.getMessageLabel().setText(color1 + " " + component1 + " eats " + color2 + " " + component2);
    }

    public void winJudge() {
        if (chessboard.getRedScore() >= 60) {
            Winboard.setWinText("Red");
            ChessGameFrame.getWinboard().showWinboard(chessboard.getChessBoardDatas());
            countDown.close();
        }

        if (chessboard.getBlackScore() >= 60) {
            Winboard.setWinText("Black");
            ChessGameFrame.getWinboard().showWinboard(chessboard.getChessBoardDatas());
            countDown.close();
        }
    }
}
