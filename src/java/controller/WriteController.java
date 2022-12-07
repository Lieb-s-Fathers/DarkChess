package controller;

import io.Write;
import model.GameData;

import java.util.ArrayList;

import static io.Write.defaultOutFile;

public class WriteController {
    //    private final Chessboard chessboard;
    private GameData gameData;

    public WriteController(GameData gameData) {
        this.gameData = gameData;
    }

    public void save() {
        Write out = new Write(defaultOutFile);
        out.printWriter.print(gameData.getAItype01() + " " + gameData.getAItype02() + "\n");
        out.printWriter.print(gameData.getDifficulty01() + " " + gameData.getDifficulty02() + "\n");

        ArrayList<String[][]> chessboardDatas = gameData.getChessDatas();
        int currentColor = chessboardDatas.size() % 2 == 0 ? 0 : 1;
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
        out1.printWriter.print(gameData.getAItype01() + " " + gameData.getAItype02() + "\n");
        out1.printWriter.print(gameData.getDifficulty01() + " " + gameData.getDifficulty02() + "\n");

        ArrayList<String[][]> chessboardData = gameData.getChessDatas();
        int currentColor = chessboardData.size() % 2 == 0 ? 0 : 1;
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
