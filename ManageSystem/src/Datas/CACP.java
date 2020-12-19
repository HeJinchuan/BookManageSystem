package Datas;

import Pages.AddingPage;

import javax.swing.*;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class CACP implements Serializable {
    private static Set<CACP> cacps = new HashSet<>();
    private static final String src = AddingPage.getCACPSrc();
    @Serial
    private static final long serialVersionUID = 10004;
    private static final String kinds = "文创";
    private final String name;
    private final double price;
    private int number;

    public CACP(String name,double price,int number) {
        this.name = name;
        this.price = price;
        this.number = number;
    }

    //录入
    public static void add(String name,double price,int number){
        try {
            CACP cacp = new CACP(name, price, number);
            if (!exist(cacp)){
                cacps.add(cacp);
                FileOutputStream fileOutputStream = new FileOutputStream(src,false);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(cacps);
                objectOutputStream.flush();
                objectOutputStream.close();
            }else {
                addNumber(cacp);
                FileOutputStream fileOutputStream = new FileOutputStream(src,false);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(cacps);
                objectOutputStream.flush();
                objectOutputStream.close();
            }
            JOptionPane.showMessageDialog(null,"添加成功！");
        }catch (IOException exp ){
            exp.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static boolean exist(CACP cacp){
        boolean flag = false;
        try {
            FileInputStream fileInputStream = new FileInputStream(src);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Set<CACP> cacpsSet = (Set<CACP>) objectInputStream.readObject();
            for (CACP value : cacpsSet){
                if (value.name.equals(cacp.name)){
                    flag = true;
                    break;
                }
            }
            cacps = cacpsSet;
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return flag;
    }

    //录入添加数量
    @SuppressWarnings("unchecked")
    public static void addNumber(CACP cacp){
        try {
            FileInputStream fileInputStream = new FileInputStream(src);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Set<CACP> cacpsSet = (Set<CACP>) objectInputStream.readObject();
            for (CACP value : cacpsSet){
                if (value.name.equals(cacp.name)){
                    value.number = value.getNumber() + cacp.number;
                    break;
                }
            }
            cacps = cacpsSet;
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
            Set<CACP> cacpsSet = (Set<CACP>) objectInputStream.readObject();
            boolean haveIt = false;
            for (CACP value : cacpsSet){
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
            cacps = cacpsSet;
            FileOutputStream fileOutputStream = new FileOutputStream(src,false);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(cacps);
            objectOutputStream.flush();
            objectOutputStream.close();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static CACP exist(String name){
        try {
            FileInputStream fileInputStream = new FileInputStream(src);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Set<CACP> cacpsSet = (Set<CACP>) objectInputStream.readObject();
            for (CACP value : cacpsSet){
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