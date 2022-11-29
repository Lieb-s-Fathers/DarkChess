package controller;

import io.Read;
import view.ChessGameFrame;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ReadController {
    public static ArrayList<String[][]> loadGameFromFile(String path) {
        try {
            Read in = new Read(path);
            String tempData = in.nextLine();
            String[] types = tempData.split(" ");
            tempData = in.nextLine();
            String[] difficultys = tempData.split(" ");

            System.out.println(Arrays.toString(types));
            System.out.println(Arrays.toString(difficultys));

            ChessGameFrame.AIType01 = Integer.parseInt(types[0]);
            ChessGameFrame.AIType02 = Integer.parseInt(types[1]);
            ChessGameFrame.difficulty01 = Integer.parseInt(difficultys[0]);
            ChessGameFrame.difficulty02 = Integer.parseInt(difficultys[1]);
            in.nextLine();
            tempData = in.nextLine();
            ArrayList<String[][]> chessDatas = new ArrayList<>();
            while (tempData != null) {
                String[][] chessData = new String[32][3];
                for (int i = 0; i < 32; i++) {
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
