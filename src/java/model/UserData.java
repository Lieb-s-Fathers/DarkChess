package model;

import web.Client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class UserData {
    ArrayList<Player> players = new ArrayList<>();

    public void addPlayer(Player player) {
        if (findPlayerByName(player.getName()) == null)
            players.add(player);
        save();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void addWinGame(Player player, int score){
        for (Player thisPlayer : players) {
            if (thisPlayer.equals(player)) {
                thisPlayer.addScore(score);
            }
        }
        save();
    }

    public Player findPlayerByName(String name){
        for (Player player : players) {
            if (player.getName().equals(name)) {
                return player;
            }
        }
        return null;
    }

    public void sort(){
        Collections.sort(players);
    }

    private void save(){
        Client client = new Client();
        try {
            System.out.println("刷新排行榜");
            client.saveUserData(this);
            client.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        Write out = new Write("src/resources/userData.txt");
//        for (Player player : players) {
//            out.printWriter.println(player);
//        }
//        out.flush();
//        out.close();
    }
}
