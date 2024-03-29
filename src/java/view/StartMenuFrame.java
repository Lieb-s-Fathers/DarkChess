package view;

import controller.GameController;
import controller.ReadController;
import model.ErrorType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static io.Write.defaultOutFile;

public class StartMenuFrame extends JFrame {
    private final int WIDTH;
    private final int HEIGHT;
    private final int extraDistance;
    public static Image icon = new ImageIcon("src/resources/image/icon.png").getImage();

    private GameController gameController;
    public ReadController readController = new ReadController(this);
    public static ChessGameFrame mainFrame;

    private final Color buttonColor = new Color(192, 196, 113);
    private final Color fontColor = new Color(120, 97, 69);

    private static JLabel statusLabel;

    public StartMenuFrame(int width, int height, boolean isNewGame) {
        setTitle("DarkChess");
        this.WIDTH = width;
        //todo: 设置首页为无边框窗口
//        this.setUndecorated(true);
        int extraHeight = this.isUndecorated() ? 0 : 35;
        this.HEIGHT = height + extraHeight;

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        setIconImage(icon);

        extraDistance = isNewGame ? 0 : 100;
        if (!isNewGame) {
            //如果不是新游戏，添加继续游戏按钮
            addContinueButton();
        }

//        addLabel();
        addStartButton();
        addLoadButton();
        addQuitButton();
        addBackImage();
    }

    //标题标签
    private void addLabel() {
        statusLabel = new JLabel("暗  棋");
        statusLabel.setLocation(WIDTH / 2 - 140, HEIGHT / 5 - 20);
        statusLabel.setSize(400, 100);
        statusLabel.setFont(new Font("华文行楷", Font.BOLD, 100));
        statusLabel.setForeground(Color.WHITE);
        add(statusLabel);
    }

    public void addBackImage(){
        ImageIcon icon = new ImageIcon("src/resources/image/首页背景2.jpg");
        JLabel backImage = new JLabel(icon);
        backImage.setSize(icon.getIconWidth(), icon.getIconWidth());
        backImage.setLocation(0,-178);
        getContentPane().add(backImage);
    }

    public static JLabel getStatusLabel() {
        return statusLabel;
    }

    private void addStartButton() {
        JButton button = new JButton("Start");
        button.addActionListener((e) -> start());
        button.setLocation(WIDTH / 2 + 300, HEIGHT / 5 + 220 + extraDistance);
        button.setSize(180, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setForeground(fontColor);
        button.setBackground(buttonColor);
        add(button);
    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(WIDTH / 2 + 300, HEIGHT / 5 + 320 + extraDistance);
        button.setSize(180, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setForeground(fontColor);
        button.setBackground(buttonColor);
        add(button);

        button.addActionListener(e -> {
            System.out.println("click load");
            String path = readController.readPath();
            ArrayList<String[][]> gameData = new ArrayList<>();
            if (path != null) {
                try {
                    gameData = readController.loadGameFromFile(path);
                } catch (Exception ex) {
                    readController.setErrors(ErrorType.ONE00);
                }
            }else {
                readController.setErrors(ErrorType.ONE00);
            }
            if (readController.getError() == ErrorType.NOError){
                mainFrame = new ChessGameFrame(720, 720, gameData);
                mainFrame.setVisible(true);
                this.dispose();
            }
        });
    }

    private void addContinueButton() {
        JButton button = new JButton("Continue");
        button.setLocation(WIDTH / 2 + 300, HEIGHT / 5 + 220);
        button.setSize(180, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));

        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setForeground(fontColor);
        button.setBackground(buttonColor);
        add(button);

        button.addActionListener(e -> {
            System.out.println("click continue");
//            try {
            ArrayList<String[][]> gameData = readController.loadGameFromFile(defaultOutFile);
            mainFrame = new ChessGameFrame(720, 720, gameData);
            mainFrame.setVisible(true);
            this.dispose();
//            } catch (Exception ex) {
//                JOptionPane.showMessageDialog(this, "存档已丢失，请重新开始游戏");
//            }
        });
    }

    private void addQuitButton() {
        JButton button = new JButton("Quit");
        button.setLocation(WIDTH / 2 + 300, HEIGHT / 5 + 420 + extraDistance);
        button.setSize(180, 60);
        button.setFont(new Font("Rockw,ell", Font.BOLD, 20));

        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setForeground(fontColor);
        button.setBackground(buttonColor);
        add(button);

        button.addActionListener((e) -> {
            //退出程序
            System.out.println("click quit");
            System.exit(0);
        });
    }

    private void start() {
        mainFrame = new ChessGameFrame(720, 720);
        System.out.println("click start");
        mainFrame.setVisible(true);
        this.dispose();
    }
}
