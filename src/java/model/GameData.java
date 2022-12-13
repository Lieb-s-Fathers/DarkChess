package model;

import java.util.ArrayList;

public class GameData {
    private int AItype01;
    private int AItype02;
    private int difficulty01;
    private int difficulty02;
    private String currentColor;
    private int AIPlayers;
    ArrayList<String[][]> chessDatas = new ArrayList<>();
    ArrayList<int[][]> stepDatas = new ArrayList<>();

    public GameData(GameData formerGamedata){
        this.AItype01 = formerGamedata.getAItype01();
        this.AItype02 = formerGamedata.getAItype02();
        this.difficulty01 = formerGamedata.getDifficulty01();
        this.difficulty02 = formerGamedata.getDifficulty02();
        this.currentColor = formerGamedata.currentColor;
        this.AIPlayers = formerGamedata.getAIPlayers();
        this.chessDatas = formerGamedata.chessDatas;
        this.stepDatas = formerGamedata.stepDatas;
    }

    public GameData(int AItype01, int AItype02, int difficulty01, int difficulty02) {
        this.AItype01 = AItype01;
        this.AItype02 = AItype02;
        this.difficulty01 = difficulty01;
        this.difficulty02 = difficulty02;
        if (AItype01 != 0 && AItype02 != 0) {
            AIPlayers = 2;
        } else {
            if (AItype01 == 0 && AItype02 == 0) {
                AIPlayers = 0;
            } else {
                AIPlayers = 1;
            }
        }
        int[][] tempStepData = {{-1,-1},{-1,-1}};
        stepDatas.add(tempStepData);
    }

    public int getAItype01() {
        return AItype01;
    }

    public void setAItype01(int AItype01) {
        this.AItype01 = AItype01;
    }

    public int getAItype02() {
        return AItype02;
    }

    public int getDifficulty01() {
        return difficulty01;
    }

    public int getDifficulty02() {
        return difficulty02;
    }

    public String getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(String currentColor) {
        this.currentColor = currentColor;
    }

    public int getAIPlayers() {
        return AIPlayers;
    }

    public ArrayList<String[][]> getChessDatas() {
        return chessDatas;
    }

    public ArrayList<int[][]> getStepDatas() {
        return stepDatas;
    }

    public void setChessDatas(ArrayList<String[][]> chessDatas) {
        this.chessDatas = chessDatas;
    }
}
