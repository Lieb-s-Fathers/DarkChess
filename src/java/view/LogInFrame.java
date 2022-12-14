package view;

import model.Player;
import model.UserData;

import javax.swing.*;
import java.awt.*;

import static view.StartMenuFrame.isLogged;
import static view.StartMenuFrame.player;

public class LogInFrame extends JFrame {
    private UserData userData;

    public LogInFrame(UserData userData, JFrame frame) {
        this.userData = userData;
        this.setTitle("用户登录界面");
        this.setSize(250, 220);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());//设置为流式布局
        JLabel label = new JLabel("用户名");
        JLabel label2 = new JLabel("密码");
        JButton loginButton = new JButton("登录");
        JButton registerButton = new JButton("注册");
        JButton exitButton = new JButton("退出");
        JTextField jTextField = new JTextField(18);//设置文本框的长度
        JPasswordField passwordField = new JPasswordField(18);//设置密码框

        jTextField.setText("admin");
        passwordField.setText("123456");

        loginButton.addActionListener((e) -> {
            try {
                Player tempPlayer = userData.findPlayerByName(jTextField.getText());
                String password = String.valueOf(passwordField.getPassword());
                if (tempPlayer != null && tempPlayer.checkPassword(password)) {
                    JOptionPane.showMessageDialog(this,"登录成功！" );
                    player = tempPlayer;
                    isLogged = true;
                    ModeSelection modeSelection = new ModeSelection(720, 720, frame);
                    modeSelection.setVisible(true);
                    frame.dispose();
                    this.dispose();

                    this.setVisible(false);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this,"用户名或密码错误！" );
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,"用户名或密码错误！" );
                throw new RuntimeException(ex);
            }

        });

        registerButton.addActionListener((e) -> {
            JFrame registerFrame = new RegisterFrame(userData);
            registerFrame.setVisible(true);
        });


        exitButton.addActionListener((e) -> {
            this.setVisible(false);
            this.dispose();
        });


        panel.add(label);//把组件添加到面板panel
        panel.add(jTextField);
        panel.add(label2);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);
        panel.add(exitButton);

        this.add(panel);//实现面板panel
        this.setVisible(true);//设置可见

//        setTitle("DarkChess");
//        this.WIDTH = width;
//        this.HEIGHT = height;
//        this.frame = frame;
//
//        setSize(WIDTH, HEIGHT);
//        setLocationRelativeTo(null); // Center the window.
//        getContentPane().setBackground(Color.WHITE);
//        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
//        setLayout(null);
//        setIconImage(icon);
//
//        addLabel();
//
//        addP2PButton();
//        addP2CButton();
//        addC2CButton();
//        addBackButton();
    }
}
