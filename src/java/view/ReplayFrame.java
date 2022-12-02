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
        addEatenChesses();

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
        nextButton.setLocation(WIDTH * 3 / 5 +10,  HEIGHT / 10 + 200);
        nextButton.setSize(180, 40);
        nextButton.setFont(new Font("Rockwell", Font.BOLD, 20));

        add(nextButton);
        nextButton.addActionListener((e) -> {
            System.out.println("click next");
            steps++;
            gameController.reloadChessboard(gameData, steps);
            clickController.calculateScore(this);
            showButton();

            clickController.setCanClick(true);
            clickController.setIsCheating(false);
            notCheatButton.setVisible(false);
            remove(notCheatButton);
            remove(cheatButton);
            addCheatButton();
        });

    }

    private void addLastButton() {
        lastButton = new JButton("Last");
        lastButton.setLocation(WIDTH * 3 / 5+10, HEIGHT / 10 + 280);
        lastButton.setSize(180, 40);
        lastButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(lastButton);

        lastButton.addActionListener((e) -> {
            System.out.println("click last");
            steps--;
            gameController.reloadChessboard(gameData, steps);
            clickController.calculateScore(this);
            showButton();

            clickController.setCanClick(true);
            clickController.setIsCheating(false);
            notCheatButton.setVisible(false);
            remove(notCheatButton);
            remove(cheatButton);
            addCheatButton();
        });
    }

    @Override
    protected void addNotCheatButton(){
        notCheatButton = new JButton("NotCheat");
        notCheatButton.setLocation(WIDTH * 3 / 5+10, HEIGHT / 10 + 420);
        notCheatButton.setSize(180, 20);
        notCheatButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        notCheatButton.setVisible(true);
        add(notCheatButton);

        notCheatButton.addActionListener((e) -> {
            System.out.println("click notCheat");
            clickController.setCanClick(true);
            clickController.setIsCheating(false);
            gameController.reloadChessboard(gameData, steps);
            notCheatButton.setVisible(false);
            addCheatButton();
            remove(notCheatButton);
        });
    }

    @Override
    public void addBackButton() {
        JButton button = new JButton("Back");
        button.addActionListener((e) -> {
            System.out.println("click back");
            this.setVisible(false);
        });
        button.setLocation(WIDTH * 3 / 5 + 10, HEIGHT / 10 + 440);
        button.setSize(180, 40);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void showButton() {
        lastButton.setVisible(steps != 0);
        nextButton.setVisible(steps != gameData.size() - 1);
    }
}
