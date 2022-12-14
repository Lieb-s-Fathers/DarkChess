package model;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Player implements Comparable<Player> {
    private String name;
    private String password;
    private int winNumbers;
    private int credits;

    public String getName() {
        return name;
    }

    public int getWinNumbers(){
        return winNumbers;
    }

    public int getCredits() {
        return credits;
    }

    //Register
    public Player(String name, String realPassword) throws UnsupportedEncodingException {
        this.name = name;
        this.password = Base64.getEncoder().encodeToString(realPassword.getBytes("UTF-8"));
    }

    public Player(String name, String password, int winNumbers, int credits) {
        this.name = name;
        this.password = password;
        this.winNumbers = winNumbers;
        this.credits = credits;
    }

    public Player(String tempData) {
        String[] tempDatas = tempData.split(" ");
        this.name = tempDatas[0];
        this.password = tempDatas[1];
        this.winNumbers = Integer.parseInt(tempDatas[2]);
        this.credits = Integer.parseInt(tempDatas[3]);
    }

    public void addScore(int score) {
        winNumbers++;
        credits += score;
    }

    public boolean checkPassword(String password) throws UnsupportedEncodingException {
        byte[] result = Base64.getDecoder().decode(this.password.getBytes());
        String realPassword = new String(result);
        if (realPassword.equals(password)) {
            return true;
        }
        return false;
    }

    public int compareTo(Player o) {
        if (this.credits > o.credits) {
            return -1;
        } else if (this.credits < o.credits) {
            return 1;
        }
        return 0;
    }

    public String toString() {
        return String.format("%s %s %d %d", name, password, winNumbers, credits);
    }
}
