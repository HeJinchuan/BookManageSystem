package Datas;

import Pages.AddingPage;

import javax.swing.*;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class Books implements Serializable {
    private static Set<Books> books = new HashSet<>();
    private static final String src = AddingPage.getBooksSrc();
    @Serial
    private static final long serialVersionUID = 10003;
    private static final String kinds = "图书";
    private final String name;
    private final String ISBN;
    private final double price;
    private int number;

    public Books(String name,String ISBN,double price,int number) {
        this.name = name;
        this.ISBN =ISBN;
        this.price = price;
        this.number = number;
    }

    //录入
    public static void add(String name,String ISBN,double price,int number){
        try {
            Books book = new Books(name, ISBN, price, number);
            if (!exist(book)){
                books.add(book);
                FileOutputStream fileOutputStream = new FileOutputStream(src,false);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(books);
                objectOutputStream.flush();
                objectOutputStream.close();
            }else {
                addNumber(book);
                FileOutputStream fileOutputStream = new FileOutputStream(src,false);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(books);
                objectOutputStream.flush();
                objectOutputStream.close();
            }
            JOptionPane.showMessageDialog(null,"添加成功！");
        }catch (IOException exp ){
            exp.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static boolean exist(Books book){
        boolean flag = false;
        try {
            FileInputStream fileInputStream = new FileInputStream(src);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Set<Books> booksSet = (Set<Books>) objectInputStream.readObject();
            for (Books value : booksSet){
                if (value.name.equals(book.name) || value.ISBN.equals(book.ISBN)){
                    flag = true;
                    break;
                }
            }
            books = booksSet;
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return flag;
    }

    //录入添加数量
    @SuppressWarnings("unchecked")
    public static void addNumber(Books book){
        try {
            FileInputStream fileInputStream = new FileInputStream(src);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Set<Books> booksSet = (Set<Books>) objectInputStream.readObject();
            for (Books value : booksSet){
                if (value.name.equals(book.name) || value.ISBN.equals(book.ISBN)){
                    value.number = value.getNumber() + book.number;
                    break;
                }
            }
            books = booksSet;
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
            Set<Books> booksSet = (Set<Books>) objectInputStream.readObject();
            boolean haveIt = false;
            for (Books value : booksSet){
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
            books = booksSet;
            FileOutputStream fileOutputStream = new FileOutputStream(src,false);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(books);
            objectOutputStream.flush();
            objectOutputStream.close();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static Books exist(String name){
        try {
            FileInputStream fileInputStream = new FileInputStream(src);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Set<Books> booksSet = (Set<Books>) objectInputStream.readObject();
            for (Books value : booksSet){
                if (value.name.equals(name) || value.ISBN.equals(name)){
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

    public String getISBN() {
        return ISBN;
    }
}