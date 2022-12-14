package view;

import model.Player;
import model.UserData;

import javax.swing.*;
import java.awt.*;

public class RankingFrame extends JFrame {
    private UserData userData;

    public RankingFrame(UserData userData, JFrame frame) {
        this.userData = userData;
        userData.sort();
        this.setTitle("排行榜");
        this.setSize(720, userData.getPlayers().size()*50+350);
        this.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());//设置为流式布局

        JLabel label3 = new JLabel("                                                     ");
        label3.setFont(new Font("黑体", Font.BOLD, 30));
        panel.add(label3);

        JLabel label0 = new JLabel("                            排行榜                             ");
        label0.setFont(new Font("黑体", Font.BOLD, 60));
        panel.add(label0);//把组件添加到面板panel


        JLabel label2 = new JLabel("                                                     ");
        label2.setFont(new Font("黑体", Font.BOLD, 30));
        panel.add(label2);

        JLabel label1 = new JLabel(String.format("      %-10s %-10s %-10s", "Name", "胜局数", "积分"));
        label1.setFont(new Font("黑体", Font.BOLD, 30));

        panel.add(label1);

        for (int i = 0; i < userData.getPlayers().size(); i++) {
            Player player = userData.getPlayers().get(i);
            JLabel label = new JLabel(String.format("   %-10s      %-10d   %-10d", player.getName(),player.getWinNumbers(), player.getCredits()));
            label.setFont(new Font("黑体", Font.BOLD, 30));
            panel.add(label);
        }


        JLabel label4 = new JLabel("                                                     ");
        label4.setFont(new Font("黑体", Font.BOLD, 30));
        panel.add(label4);

        JButton exitButton = new JButton("退出");
        exitButton.setFont(new Font("黑体", Font.BOLD, 30));
        exitButton.addActionListener((e) -> {
            this.setVisible(false);
            this.dispose();
        });
        panel.add(exitButton);

        this.add(panel);//实现面板panel
        this.setVisible(true);//设置可见
    }
}
