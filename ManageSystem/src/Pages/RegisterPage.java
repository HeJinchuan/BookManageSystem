package Pages;

import Datas.User;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class RegisterPage {

    public static void registerPage(){
        JFrame jf = new JFrame("注册");
        jf.setLayout(new BorderLayout());
        jf.setSize(320,270);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //次级容器
        JPanel jp = new JPanel(null);

        //账号输入
        JLabel account = new JLabel("账号");
        JTextField textField = new JTextField(16);
        account.setBounds(new Rectangle(0,5,50,30));
        textField.setBounds(new Rectangle(55,5,200,30));

        //密码输入
        JLabel key = new JLabel("密码");
        JPasswordField passwordField1 = new JPasswordField(16);
        key.setBounds(new Rectangle(0,45,50,30));
        passwordField1.setBounds(new Rectangle(55,45,200,30));

        //确认密码
        JLabel keyMakeSure = new JLabel("确认密码");
        JPasswordField passwordField2 = new JPasswordField(16);
        keyMakeSure.setBounds(new Rectangle(0,85,50,30));
        passwordField2.setBounds(new Rectangle(55,85,200,30));

        //管理员注册
        JRadioButton admin = new JRadioButton("管理员注册");
        JPasswordField passwordField3 = new JPasswordField(16);
        admin.setBounds(0,125,100,30);
        passwordField3.setBounds(new Rectangle(105,125,150,30));
        passwordField3.setEnabled(false);
        admin.addActionListener( e -> passwordField3.setEnabled(admin.isSelected()));

        //注册按钮
        JButton register = new JButton("注册");
        register.setBounds(new Rectangle(110,180,100,30));
        register.addActionListener(e -> {
            if (textField.getText().isEmpty()){
                JOptionPane.showMessageDialog(null,"请输入账号","错误",JOptionPane.ERROR_MESSAGE);
                textField.requestFocus();
            }else if (new String(passwordField1.getPassword()).isEmpty()){
                JOptionPane.showMessageDialog(null,"请输入密码","错误",JOptionPane.ERROR_MESSAGE);
                passwordField1.requestFocus();
            }else if (new String(passwordField2.getPassword()).isEmpty()){
                JOptionPane.showMessageDialog(null,"请确认密码","错误",JOptionPane.ERROR_MESSAGE);
                passwordField2.requestFocus();
            }else if (admin.isSelected() && new String(passwordField3.getPassword()).isEmpty()){
                JOptionPane.showMessageDialog(null,"请输入管理员密码","错误",JOptionPane.ERROR_MESSAGE);
                passwordField3.requestFocus();
            }else {
                if (!Arrays.equals(passwordField1.getPassword(), passwordField2.getPassword())){
                    JOptionPane.showMessageDialog(null,"密码不相同！","错误",JOptionPane.ERROR_MESSAGE);
                    passwordField2.requestFocus();
                }else {
                    User.register(textField.getText(),String.valueOf(passwordField1.getPassword()),String.valueOf(passwordField3.getPassword()));
                    jf.dispose();
                }
            }
        });

        jf.setContentPane(jp);
        jp.add(account);
        jp.add(textField);
        jp.add(key);
        jp.add(passwordField1);
        jp.add(keyMakeSure);
        jp.add(passwordField2);
        jp.add(admin);
        jp.add(passwordField3);
        jp.add(register);
        jf.setVisible(true);
    }
}