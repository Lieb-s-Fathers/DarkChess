package controller;

import io.Write;
import model.GameData;
import view.Chessboard;

import java.util.ArrayList;

import static io.Write.defaultOutFile;

public class WriteController {
    //    private final Chessboard chessboard;
    private GameData gameData;

    public WriteController(GameData gameData) {
        this.gameData = gameData;
    }

    public void save() {
        synchronized (Chessboard.class){
            Write out = new Write(defaultOutFile);
            out.printWriter.print(gameData.getAItype01() + " " + gameData.getAItype02() + "\n");
            out.printWriter.print(gameData.getDifficulty01() + " " + gameData.getDifficulty02() + "\n");

            ArrayList<String[][]> chessboardDatas = gameData.getChessDatas();
            ArrayList<int[][]> stepDatas = gameData.getStepDatas();

            int currentColor = chessboardDatas.size() % 2 == 0 ? 0 : 1;
            out.printWriter.println(currentColor);
            out.printWriter.println();

            for (int i = 0; i < chessboardDatas.size(); i++) {
                String[][] chessboardData = chessboardDatas.get(i);
                int[][] stepData = stepDatas.get(i);

                out.printWriter.println(stepData[0][0]+" "+stepData[0][1]+" "+stepData[1][0] + " "+stepData[1][1]);
                for (int j = 0; j < 32; j++) {
                    out.printWriter.println(chessboardData[j][0] + " " + chessboardData[j][1] + " " + chessboardData[j][2]);
                }
                out.printWriter.println();
                out.flush();
            }
            out.close();
        }
    }

    public void saveGame(String outFilePath) {
        synchronized (Chessboard.class){
            Write out1 = new Write(outFilePath);
            out1.printWriter.print(gameData.getAItype01() + " " + gameData.getAItype02() + "\n");
            out1.printWriter.print(gameData.getDifficulty01() + " " + gameData.getDifficulty02() + "\n");

            ArrayList<String[][]> chessboardDatas = gameData.getChessDatas();
            ArrayList<int[][]> stepDatas = gameData.getStepDatas();

            int currentColor = chessboardDatas.size() % 2 == 0 ? 0 : 1;
            out1.printWriter.println(currentColor);
            out1.printWriter.println();

            for (int i = 0; i < chessboardDatas.size(); i++) {
                String[][] chessboardData = chessboardDatas.get(i);
                int[][] stepData = stepDatas.get(i);

                out1.printWriter.println(stepData[0][0]+" "+stepData[0][1]+" "+stepData[1][0] + " "+stepData[1][1]);
                for (int j = 0; j < 32; j++) {
                    out1.printWriter.println(chessboardData[j][0] + " " + chessboardData[j][1] + " " + chessboardData[j][2]);
                }
                out1.printWriter.println();
                out1.flush();
            }
            out1.close();
        }
    }
}
