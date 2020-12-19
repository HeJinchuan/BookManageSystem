package Datas;

import Pages.AddingPage;

import javax.swing.*;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class Sundry implements Serializable {
    private static Set<Sundry> sundries = new HashSet<>();
    private static final String src = AddingPage.getSundriesSrc();
    @Serial
    private static final long serialVersionUID = 10002;
    private static final String kinds = "杂物";
    private final String name;
    private final double price;
    private int number;

    public Sundry(String name, double price, int number) {
        this.name = name;
        this.price = price;
        this.number = number;
    }

    //录入
    public static void add(String name,double price,int number){
        try {
            Sundry sundry = new Sundry(name, price, number);
            if (!exist(sundry)){
                sundries.add(sundry);
                FileOutputStream fileOutputStream = new FileOutputStream(src,false);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(sundries);
                objectOutputStream.flush();
                objectOutputStream.close();
            }else {
                addNumber(sundry);
                FileOutputStream fileOutputStream = new FileOutputStream(src,false);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(sundries);
                objectOutputStream.flush();
                objectOutputStream.close();
            }
            JOptionPane.showMessageDialog(null,"添加成功！");
        }catch (IOException exp ){
            exp.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static boolean exist(Sundry sundry){
        boolean flag = false;
        try {
            FileInputStream fileInputStream = new FileInputStream(src);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Set<Sundry> sundrySet = (Set<Sundry>) objectInputStream.readObject();
            for (Sundry value : sundrySet){
                if (value.name.equals(sundry.name)){
                    flag = true;
                    break;
                }
            }
            sundries = sundrySet;
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return flag;
    }

    //录入添加数量
    @SuppressWarnings("unchecked")
    public static void addNumber(Sundry sundry){
        try {
            FileInputStream fileInputStream = new FileInputStream(src);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Set<Sundry> sundrySet = (Set<Sundry>) objectInputStream.readObject();
            for (Sundry value : sundrySet){
                if (value.name.equals(sundry.name)){
                    value.number = value.getNumber() + sundry.number;
                    break;
                }
            }
            sundries = sundrySet;
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    //卖出
    @SuppressWarnings("unchecked")
    public static void sell(String name,int number){
        try {
            FileInputStream fileInputStream = new FileInputStream(src);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Set<Sundry> sundrySet = (Set<Sundry>) objectInputStream.readObject();
            boolean haveIt = false;
            for (Sundry value : sundrySet){
                if (value.name.equals(name)){
                    if ((value.getNumber() - number) >= 0){
                        value.number = value.getNumber() - number;
                        JOptionPane.showMessageDialog(null,"卖掉啦~");
                    }else {
                        JOptionPane.showMessageDialog(null,"没有那么多东西呀！","错误",JOptionPane.ERROR_MESSAGE);
                    }
                    haveIt = true;
                    break;
                }
            }
            if (!haveIt){
                JOptionPane.showMessageDialog(null,"没有该物品！");
            }
            sundries = sundrySet;
            FileOutputStream fileOutputStream = new FileOutputStream(src,false);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(sundries);
            objectOutputStream.flush();
            objectOutputStream.close();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static Sundry exist(String name){
        try {
            FileInputStream fileInputStream = new FileInputStream(src);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Set<Sundry> sundrySet = (Set<Sundry>) objectInputStream.readObject();
            for (Sundry value : sundrySet){
                if (value.name.equals(name)){
                    return value;
                }
            }
            objectInputStream.close();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public double getPrice() {
        return price;
    }

}