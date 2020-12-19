package Datas;

import Pages.IndexPage;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable{
    private String userName;
    private String password;
    private boolean isAdmin = false;
    private static List<User> users = new ArrayList<>();
    private static final String src = IndexPage.getUsersSrc();
    @Serial
    private static final long serialVersionUID = 10000;
    private static final String adminKey = "520axcs";

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    //注册和下面的判断存在，使用io读取Object文件，存入userList缓存，再全部写入users，io写入
    public static void register(String userName, String password,String adminWords) {
        try {
            User user = new User(userName, password);
            if (adminWords.isEmpty()){
                if (!exist(user)){
                    users.add(user);
                    user.isAdmin = false;
                    FileOutputStream fileOutputStream = new FileOutputStream(src,false);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                    objectOutputStream.writeObject(users);
                    objectOutputStream.flush();
                    objectOutputStream.close();
                    JOptionPane.showMessageDialog(null,"注册成功");
                }else {
                    JOptionPane.showMessageDialog(null, "用户已存在！", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }else if (adminKey.equals(adminWords) && !exist(user)){
                users.add(user);
                user.isAdmin = true;
                FileOutputStream fileOutputStream = new FileOutputStream(src,false);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(users);
                objectOutputStream.flush();
                objectOutputStream.close();
                JOptionPane.showMessageDialog(null,"管理员注册成功");
            }else if (exist(user)){
                JOptionPane.showMessageDialog(null, "用户已存在！", "错误", JOptionPane.ERROR_MESSAGE);
            }else {
                JOptionPane.showMessageDialog(null,"管理员密码错误","错误",JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static boolean exist(User user){
        boolean flag = false;
        try {
            FileInputStream fileInputStream = new FileInputStream(src);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            List<User> userList = (List<User>) objectInputStream.readObject();
            users = userList;
            for (User value : userList) {
                if (value.userName.equals(user.userName)) {
                    flag = true;
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException exception){
            exception.printStackTrace();
        }
        return flag;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}