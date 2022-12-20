package view;

import model.ChessColor;
import web.Client;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static view.StartMenuFrame.mainFrame;

public class OnlineWaitFrame extends JFrame {
    private Client client = new Client();
    private JFrame frame;

    public OnlineWaitFrame(JFrame frame) {
        this.setTitle("用户登录界面");
        this.setSize(250, 120);
        this.setLocationRelativeTo(null);
        this.frame = frame;

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());//设置为流式布局
        JLabel label0 = new JLabel("                       ");
        JLabel label = new JLabel("已连接服务器，请等待玩家2加入！");
        panel.add(label0);
        panel.add(label);
        try {
            client.startOnlineGame();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JButton button = new JButton("Back");
        button.addActionListener(e -> {
            this.setVisible(false);
            this.dispose();
        });
        panel.add(button);

        client = new Client();
        add(panel);
    }

    public void newOnlineGame(ChessColor myChessColor) {
        mainFrame = new OnlineChessGameFrame(720, 720, myChessColor, client);
        mainFrame.setVisible(true);
        frame.dispose();
        this.dispose();
    }
}
