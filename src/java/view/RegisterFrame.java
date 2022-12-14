package view;

import model.Player;
import model.UserData;

import javax.swing.*;
import java.awt.*;
import java.io.UnsupportedEncodingException;

public class RegisterFrame extends JFrame{
    private UserData userData;

    public RegisterFrame(UserData userData) {
        this.userData = userData;
        this.setTitle("用户注册界面");
        this.setSize(250, 220);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());//设置为流式布局
        JLabel label = new JLabel("用户名");
        JLabel label2 = new JLabel("密码");
        JLabel label3 = new JLabel("重复密码");
        JButton loginButton = new JButton("注册");
        JButton exitButton = new JButton("退出");
        JTextField jTextField = new JTextField(18);//设置文本框的长度
        JPasswordField passwordField1 = new JPasswordField(18);//设置密码框
        JPasswordField passwordField2 = new JPasswordField(18);

        loginButton.addActionListener((e) -> {
            String name = jTextField.getText();
            String password1 = String.valueOf(passwordField1.getPassword());
            String password2 = String.valueOf(passwordField2.getPassword());
            if (password1.equals(password2)){
                try {
                    Player newPlayer = new Player(name, password1);
                    userData.addPlayer(newPlayer);
                    JOptionPane.showMessageDialog(this, "恭喜您，注册成功！ 请记牢您的账户和密码！");
                    this.setVisible(false);
                    this.dispose();
                } catch (UnsupportedEncodingException ex) {
                    JOptionPane.showMessageDialog(this, "用户名或密码包含非法字符");
                    throw new RuntimeException(ex);
                }
            } else {
                JOptionPane.showMessageDialog(this, "两次密码输入不一致，请重新输入！");
                passwordField1.setText("");
                passwordField2.setText("");
            }
        });


        exitButton.addActionListener((e) -> {
            this.setVisible(false);
            this.dispose();
        });


        panel.add(label);//把组件添加到面板panel
        panel.add(jTextField);
        panel.add(label2);
        panel.add(passwordField1);
        panel.add(label3);
        panel.add(passwordField2);
        panel.add(loginButton);
        panel.add(exitButton);

        this.add(panel);//实现面板panel
        this.setVisible(true);//设置可见

    }
}
