package controller;

import model.ChessColor;
import view.ChessGameFrame;
import view.Chessboard;

import java.util.ArrayList;

/**
 * 这个类主要完成由窗体上组件触发的动作。
 * 例如点击button等
 * ChessGameFrame中组件调用本类的对象，在本类中的方法里完成逻辑运算，将运算的结果传递至chessboard中绘制
 */
public class GameController {
    private Chessboard chessboard;

    public GameController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public void reloadChessboardDatas(ArrayList<String[][]> chessboardDatas){
        chessboard.setChessBoardDatas(chessboardDatas);
    }

    public void reloadChessboard(String[][] chessBoardData){
        chessboard.loadGame(chessBoardData);
    }

    public void withdraw(){
        chessboard.deleteLastStep();
    }

    public ArrayList<String[][]> getChessboardDatas() {
        return chessboard.getChessBoardDatas();
    }

    public void timeOut(){
        chessboard.setCurrentColor(chessboard.getCurrentColor() == ChessColor.BLACK ? ChessColor.RED : ChessColor.BLACK);
        ChessGameFrame.getStatusLabel().setText(String.format("%s's TURN", chessboard.getCurrentColor().getName()));
    }

}
