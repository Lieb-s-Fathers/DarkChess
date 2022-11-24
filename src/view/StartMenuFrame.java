package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static controller.ReadController.loadGameFromFile;

public class StartMenuFrame extends JFrame {
    private final int WIDTH;
    private final int HEIGHT;
    private final int extraDistance;

    private GameController gameController;

    private static JLabel statusLabel;

    public StartMenuFrame(int width, int height, boolean isNewGame) {
        setTitle("DarkChess");
        this.WIDTH = width;
        this.HEIGHT = height;

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        extraDistance = isNewGame ? 0 : 100;
        if (!isNewGame) {
            //如果不是新游戏，添加继续游戏按钮
            addContinueButton();
        }

        addLabel();
        addStartButton();
        addLoadButton();
        addQuitButton();
    }

    //标题标签
    private void addLabel() {
        statusLabel = new JLabel("Dark Chess");
        statusLabel.setLocation(WIDTH / 2 - 145, HEIGHT / 5);
        statusLabel.setSize(400, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 50));
        add(statusLabel);
    }

    public static JLabel getStatusLabel() {
        return statusLabel;
    }

    private void addStartButton() {
        JButton button = new JButton("Start");
        button.addActionListener((e) -> {
            //窗口跳转
            start();
        });
        button.setLocation(WIDTH / 2 - 90, HEIGHT / 5 + 120 + extraDistance);
        button.setSize(180, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(WIDTH / 2 - 90, HEIGHT / 5 + 220 + extraDistance);
        button.setSize(180, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBackground(Color.LIGHT_GRAY);
        add(button);

        button.addActionListener(e -> {
            System.out.println("click load");
            String path = JOptionPane.showInputDialog(this, "Input Path here");
            if (path != null) {
                try{
                    ArrayList<String[][]> gameData = loadGameFromFile(path);
                    ChessGameFrame mainFrame = new ChessGameFrame(720, 720, gameData.get(gameData.size() - 1));
                    mainFrame.setVisible(true);
                    this.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "请输入正确的路径!");
                }
            }
        });
    }

    private void addContinueButton() {
        JButton button = new JButton("Continue");
        button.setLocation(WIDTH / 2 - 90, HEIGHT / 5 + 120);
        button.setSize(180, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBackground(Color.LIGHT_GRAY);
        add(button);

        button.addActionListener(e -> {
            System.out.println("click continue");
            try{
                ArrayList<String[][]> gameData = loadGameFromFile("save/save.out");
                ChessGameFrame mainFrame = new ChessGameFrame(720, 720, gameData.get(gameData.size() - 1));
                mainFrame.setVisible(true);
                this.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "存档已丢失，请重新开始游戏");
            }
        });
    }

    private void addQuitButton() {
        JButton button = new JButton("Quit");
        button.addActionListener((e) -> {
            //退出程序
            System.out.println("click quit");
            System.exit(0);
        });

        button.setLocation(WIDTH / 2 - 90, HEIGHT / 5 + 320 + extraDistance);
        button.setSize(180, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void start(){
        ChessGameFrame mainFrame = new ChessGameFrame(720, 720);
        System.out.println("click start");
        this.dispose();
        mainFrame.setVisible(true);
    }
}
