package Pages;

import Datas.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

public class IndexPage{
    private static final JFrame root = new JFrame("图书管理系统");
    private static final JButton login = new JButton("登录");
    private static final File file = new File("");
    private static final String path = file.getAbsolutePath();
    private static final String dataSrc = path + "\\data";
    private static final String usersSrc = path + "\\data\\users.txt";

    static {
        try{
            File data = new File(dataSrc);
            if (!data.exists()){
                data.mkdirs();
            }

            File users = new File(usersSrc);
            if (!users.exists()){
                users.createNewFile();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void firstPage(){

        //容器
        root.setLayout(new BorderLayout());
        root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        root.setSize(320,160);
        root.setLocationRelativeTo(null);

        //次级容器
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,2));

        //文本框
        JLabel text = new JLabel("欢迎使用图书管理系统！",SwingConstants.CENTER);

        //账号输入
        JPanel panel1 = new JPanel();
        JLabel account = new JLabel("账号");
        JTextField textField = new JTextField(16);
        textField.requestFocus();
        textField.addKeyListener(new KeyAdapter(){
            @Override
            public void keyTyped(KeyEvent e){
                if (e.getKeyChar()=='\n'){
                    login.doClick();
                }
            }
        });
        panel1.add(account);
        panel1.add(textField);

        //密码输入
        JPanel panel2 = new JPanel();
        JLabel key = new JLabel("密码");
        JPasswordField passwordField = new JPasswordField(16);
        passwordField.addKeyListener(new KeyAdapter(){
            @Override
            public void keyTyped(KeyEvent e){
                if (e.getKeyChar()=='\n'){
                    login.doClick();
                }
            }
        });
        panel2.add(key);
        panel2.add(passwordField);

        //注册按钮
        JPanel panel3 = new JPanel(new FlowLayout());
        JButton register = new JButton("注册");
        register.addActionListener(e -> RegisterPage.registerPage());

        //登录按钮
        login.addActionListener(e -> {
            if (textField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null,"请输入账号！","错误",JOptionPane.ERROR_MESSAGE);
                textField.requestFocus();
            }else if (new String(passwordField.getPassword()).isEmpty()) {
                JOptionPane.showMessageDialog(null,"请输入密码！","错误",JOptionPane.ERROR_MESSAGE);
                passwordField.requestFocus();
            }
            else {
                switch (check(textField.getText(), new String(passwordField.getPassword()))) {
                    case CORRECT_IS_ADMIN -> {
                        JOptionPane.showMessageDialog(null, "登陆成功!");
                        root.dispose();
                        FirstPage.firstPage(textField.getText(), true);
                    }
                    case CORRECT_NOT_ADMIN -> {
                        JOptionPane.showMessageDialog(null, "登陆成功!");
                        root.dispose();
                        FirstPage.firstPage(textField.getText(), false);
                    }
                    case INCORRECT -> {
                        JOptionPane.showMessageDialog(null, "账号或密码错误！", "错误", JOptionPane.ERROR_MESSAGE);
                        passwordField.requestFocus();
                    }
                }
            }
        });
        panel3.add(register);
        panel3.add(login);

        //添加组件
        root.add(text,BorderLayout.NORTH);
        root.add(panel,BorderLayout.CENTER);
        panel.add(panel1);
        panel.add(panel2);
        root.add(panel3,BorderLayout.SOUTH);
        root.setResizable(false);
        root.setVisible(true);
    }

    public static String getUsersSrc() {
        return usersSrc;
    }

    public static void main(String[] args) {
        firstPage();
    }

    @SuppressWarnings("unchecked")
    public static KindEnum check(String userName,String password){
        boolean flag = false;
        boolean isAdmin = false;
        try {
            FileInputStream fileInputStream = new FileInputStream(usersSrc);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            java.util.List<User> userList = (List<User>)objectInputStream.readObject();
           for (User value: userList){
               if (userName.equals(value.getUserName()) && password.equals(value.getPassword())){
                   flag = true;
                   isAdmin = value.isAdmin();
               }
           }
        }catch (IOException | ClassNotFoundException exception){
            exception.printStackTrace();
        }
        if (!flag){
            return KindEnum.INCORRECT;
        }else if (!isAdmin){
            return KindEnum.CORRECT_NOT_ADMIN;
        }else {
            return KindEnum.CORRECT_IS_ADMIN;
        }
    }

    public enum KindEnum{
        CORRECT_NOT_ADMIN,
        CORRECT_IS_ADMIN,
        INCORRECT
    }
}