package model;

import io.Write;
import java.util.ArrayList;

public class UserData {
    ArrayList<Player> players = new ArrayList<>();

    public void addPlayer(Player player) {
        players.add(player);
        save();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player findPlayerByName(String name){
        for (Player player : players) {
            if (player.getName().equals(name)) {
                return player;
            }
        }
        return null;
    }

    private void save(){
        Write out = new Write("src/resources/userData.txt");
        for (Player player : players) {
            out.printWriter.println(player);
        }
        out.flush();
        out.close();
    }
}
