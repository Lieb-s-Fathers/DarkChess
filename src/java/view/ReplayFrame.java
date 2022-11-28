package view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ReplayFrame extends FatherFrame {

    private int steps = 0;
    private ArrayList<String[][]> gameData;
    private JButton nextButton;
    private JButton lastButton;

    public ReplayFrame(int WIDTH, int HEIGHT, ArrayList<String[][]> gameData) {
        super(WIDTH, HEIGHT);
        this.gameData = gameData;

        addChessboard();

        addTurnLabel();
        addRedScoreLabel();
        addBlackScoreLabel();
        addMessageLabel();
        addBackButton();

        addNextButton();
        addLastButton();

        addCheatButton();

        clickController.setCanClick(false);

        showButton();
    }

    private void addNextButton() {
        nextButton = new JButton("Next");
        nextButton.addActionListener((e) -> {
            System.out.println("click next");
            steps++;
            gameController.reloadChessboard(gameData.get(steps));
            clickController.calculateScore(this);
            showButton();
        });
        nextButton.setLocation(WIDTH * 3 / 5, HEIGHT / 10 + 200);
        nextButton.setSize(180, 40);
        nextButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(nextButton);
    }

    private void addLastButton() {
        lastButton = new JButton("Last");
        lastButton.addActionListener((e) -> {
            System.out.println("click last");
            steps--;
            gameController.reloadChessboard(gameData.get(steps));
            clickController.calculateScore(this);
            showButton();
        });
        lastButton.setLocation(WIDTH * 3 / 5, HEIGHT / 10 + 280);
        lastButton.setSize(180, 40);
        lastButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(lastButton);
    }

    @Override
    protected void addNotCheatButton(){
        JButton button = new JButton("NotCheat");
        button.setLocation(WIDTH * 3 / 5, HEIGHT / 10 + 420);
        button.setSize(180, 20);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setVisible(true);
        add(button);

        button.addActionListener((e) -> {
            System.out.println("click notCheat");
            clickController.setCanClick(true);
            clickController.setIsCheating(false);
            gameController.reloadChessboard(gameData.get(steps));
            button.setVisible(false);
            addCheatButton();
            remove(button);
        });
    }

    @Override
    public void addBackButton() {
        JButton button = new JButton("Back");
        button.addActionListener((e) -> {
            System.out.println("click back");
            this.setVisible(false);
        });
        button.setLocation(WIDTH * 3 / 5, HEIGHT / 10 + 440);
        button.setSize(180, 40);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void showButton() {
        lastButton.setVisible(steps != 0);
        nextButton.setVisible(steps != gameData.size() - 1);
    }
}
