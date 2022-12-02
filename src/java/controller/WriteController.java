package controller;

import io.Write;
import view.ChessGameFrame;
import view.Chessboard;

import java.util.ArrayList;

import static io.Write.defaultOutFile;

public class WriteController {
    private final Chessboard chessboard;

    public WriteController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public void save() {
        Write out = new Write(defaultOutFile);
        out.printWriter.print(ChessGameFrame.AIType01 + " " + ChessGameFrame.AIType02 + "\n");
        out.printWriter.print(ChessGameFrame.difficulty01 + " " + ChessGameFrame.difficulty02 + "\n");

        ArrayList<String[][]> chessboardDatas = chessboard.getChessBoardDatas();
        int currentColor = chessboardDatas.size()%2 == 0 ? 0 : 1;
        out.printWriter.println(currentColor);
        out.printWriter.println();

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
        out1.printWriter.print(ChessGameFrame.AIType01 + " " + ChessGameFrame.AIType02 + "\n");
        out1.printWriter.print(ChessGameFrame.difficulty01 + " " + ChessGameFrame.difficulty02 + "\n");
        ArrayList<String[][]> chessboardData = chessboard.getChessBoardDatas();
        int currentColor = chessboardData.size()%2 == 0 ? 0 : 1;
        out1.printWriter.println(currentColor);
        out1.printWriter.println();
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
