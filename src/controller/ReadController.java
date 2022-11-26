package controller;

import io.Read;

import java.io.IOException;
import java.util.ArrayList;

public class ReadController {
    public static ArrayList<String[][]> loadGameFromFile(String path) {
        try {
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
