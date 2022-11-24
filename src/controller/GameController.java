package controller;

import io.Read;
import model.ChessColor;
import view.ChessGameFrame;
import view.Chessboard;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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

    public ArrayList<String[][]> loadGameFromFile(String path) {
        try {
//            List<String> chessData = Files.readAllLines(Path.of(path));
//            chessboard.loadGame(chessData);
//            return chessData;
            Read in = new Read(path);
            ArrayList<String[][]> chessDatas = new ArrayList<>();
            String tempData = in.nextLine();
            while (tempData != null){
                String[][] chessData = new String[32][3];
                for (int i = 0; i < 32; i++){
                    chessData[i] = tempData.split(" ");
                    tempData = in.nextLine();
                }
                chessDatas.add(chessData);
                tempData = in.nextLine();
            }
            return chessDatas;
        } catch (IOException e) {
            //todo 不合法的输入
            e.printStackTrace();
        }
        return null;
    }

}
