package controller;

import io.Read;
import model.ChessColor;
import model.ErrorType;
import model.GameData;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static io.Write.defaultOutFilePath;

public class ReadController {
    private ErrorType error = ErrorType.NOError;
    private GameData gameData;
    private final String[] colorNames = {ChessColor.BLACK.getName(), ChessColor.RED.getName(), ChessColor.NONE.getName()};
    private final String[] chessStyles = {"0","1","2","3","4","5","6","7"};
    private final String[] currentColors = {"0", "1"};
    private ArrayList<String[][]> chessDatas;

    public ReadController(GameData gameData) {
        this.gameData = gameData;
    }

    public void setErrors(ErrorType error) {
        if (this.error == ErrorType.NOError){
            this.error = error;
        }
    }

    public ErrorType getError(){
        if (error != ErrorType.NOError){
//            JOptionPane.showMessageDialog(frame, error.getName()+" "+error.getMessage());
        }
        return error;
    }

    public String readPath(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(defaultOutFilePath));
//        fileChooser.showOpenDialog();
        //todo: filechooser
        try {
            String filePath = fileChooser.getSelectedFile().getName();
            String[] filePaths = filePath.split("\\.");
            if (filePaths.length != 0) {
                if (!filePaths[filePaths.length - 1].equals("txt")) {
                    setErrors(ErrorType.ONE01);
                }
            } else setErrors(ErrorType.ONE01);
            return fileChooser.getSelectedFile().getAbsolutePath();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public void loadGameFromFile(String path) {
        try {
            Read in = new Read(path);
            String tempData = in.nextLine();
            String[] types = tempData.split(" ");
            tempData = in.nextLine();
            String[] difficulties = tempData.split(" ");

            gameData.setAItype01(Integer.parseInt(types[0]));
            gameData.setAItype02(Integer.parseInt(types[1]));
            gameData.setDifficulty01(Integer.parseInt(difficulties[0]));
            gameData.setDifficulty02(Integer.parseInt(difficulties[1]));
            gameData.setCurrentColor(Integer.parseInt(in.nextLine()));

            in.nextLine();

            tempData = in.nextLine();
            chessDatas = gameData.getChessDatas();
            while (tempData != null) {
                String[][] chessData = new String[32][3];
                for (int i = 0; i < 32; i++) {
                    if (tempData.equals("")) {
                        setErrors(ErrorType.ONE02);
                    }
                    chessData[i] = tempData.split(" ");
                    tempData = in.nextLine();
                }
                chessDatas.add(chessData);
                tempData = in.nextLine();
            }
            checkFileData();
        } catch (IOException e) {
            setErrors(ErrorType.ONE02);
        }
    }

    public void checkFileData() {
        for (String[][] chessBoardData : chessDatas) {
            for (int j = 0; j < 32; j++) {
                String[] chessData = chessBoardData[j];
                if (!(Arrays.asList(colorNames).contains(chessData[0]) && Arrays.asList(chessStyles).contains(chessData[1]))) {
                    setErrors(ErrorType.ONE03);
                }
            }
        }

        if (!Arrays.asList(currentColors).contains(gameData.getCurrentColor())) {
            setErrors(ErrorType.ONE04);
        }

        //todo: 行棋步骤错误
        for (int i=1;i<chessDatas.size(); i++) {
            int notSame = 0;
            String[][] chessBoardData = chessDatas.get(i);
            String[][] lastChessBoardData = chessDatas.get(i-1);
            for (int j=0; j<=32; j++){
                if (!Arrays.deepEquals(chessBoardData, lastChessBoardData)) {
                    notSame++;
                }
            }
            if (notSame > 2){
                setErrors(ErrorType.ONE05);
            }
        }
    }
}
