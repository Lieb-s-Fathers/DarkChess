package controller;

import io.Write;
import view.ChessGameFrame;
import view.Chessboard;

import java.util.ArrayList;

import static io.Write.defaultOutFilePath;

public class WriteController {
    private final Chessboard chessboard;
//    Write out = new Write(defaultOutFilePath);

    public WriteController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public void save() {
//        for (SquareComponent[] squareComponents : chessboard.getChessComponents()){
//            for (SquareComponent squareComponent : squareComponents){
//                out.printWriter.println(squareComponent.toString());
//                out.flush();
//            }
//        }
//        out.printWriter.println();
//        out.flush();
        Write out = new Write(defaultOutFilePath);
        out.printWriter.print(ChessGameFrame.AIType01 + " " + ChessGameFrame.AIType02 + "\n");
        out.printWriter.println(ChessGameFrame.difficulty01 + " " + ChessGameFrame.difficulty02 + "\n");
        ArrayList<String[][]> chessboardDatas = chessboard.getChessBoardDatas();
        for (String[][] chessboardData : chessboardDatas) {
            for (int j = 0; j < 32; j++) {
                out.printWriter.println(chessboardData[j][0] + " " + chessboardData[j][1] + " " + chessboardData[j][2]);
            }
            out.printWriter.println();
            out.flush();
        }
        out.close();
    }

    public void saveGame(String outFilePath) {
        Write out1 = new Write(outFilePath);
        ArrayList<String[][]> chessboardData = chessboard.getChessBoardDatas();
        for (String[][] chessboardDatum : chessboardData) {
            for (int j = 0; j < 32; j++) {
                out1.printWriter.println(chessboardDatum[j][0] + " " + chessboardDatum[j][1] + " " + chessboardDatum[j][2]);
            }
            out1.printWriter.println();
            out1.flush();
        }
        out1.close();
    }
}
