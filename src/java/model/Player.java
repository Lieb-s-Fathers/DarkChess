package model;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Player {
    private String name;
    private String password;
    private int winNumbers;
    private int credits;

    //Register
    public Player(String name, String realPassword) throws UnsupportedEncodingException {
        this.name = name;
        this.password = Base64.getEncoder().encodeToString(realPassword.getBytes("utf-8"));
    }

    public Player(String name, String password, int winNumbers, int credits) {
        this.name = name;
        this.password = password;
        this.winNumbers = winNumbers;
        this.credits = credits;
    }

    public boolean chechPassword(String password) throws UnsupportedEncodingException {
        String realPassword = Base64.getDecoder().decode(password).toString();
        if (realPassword.equals(password)) {
            return true;
        }
        return false;
    }
}
