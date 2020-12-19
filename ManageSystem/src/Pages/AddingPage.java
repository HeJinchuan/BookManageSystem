package Pages;

import Datas.Books;
import Datas.CACP;
import Datas.Sundry;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class AddingPage {
    private static final File file = new File("");
    private static final JTextField ISBNCode = new JTextField();
    private static final JTextField priceField = new JTextField();
    private static final JTextField numberField = new JTextField();
    private static final String path = file.getAbsolutePath();
    private static final String BooksSrc = path + "\\data\\books.txt";
    private static final String CACPSrc = path + "\\data\\CACP.txt";
    private static final String sundriesSrc = path + "\\data\\sundries.txt";

    static {
        File books = new File(BooksSrc);
        File CACP = new File(CACPSrc);
        File sundries = new File(sundriesSrc);
        try{
            if (!books.exists()){
                books.createNewFile();
            }
            if (!CACP.exists()){
                CACP.createNewFile();
            }
            if (!sundries.exists()){
                sundries.createNewFile();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void addingPage(){
        JFrame jf = new JFrame("录入登记");
        jf.setSize(400,260);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //主面板
        JPanel jp = new JPanel(null);

        //录入类型
        JLabel add = new JLabel("录入类型");
        add.setBounds(new Rectangle(0,10,100,20));
        String[] s = {"图书","文创","杂物"};
        JComboBox<String> kinds = new JComboBox<>(s);
        kinds.setBounds(new Rectangle(110,10,100,20));

        //名称
        JLabel name = new JLabel("名称");
        name.setBounds(new Rectangle(0,40,100,20));
        JTextField nameField = new JTextField();
        nameField.setBounds(new Rectangle(110,40,200,20));

        //查询
        JLabel tips = new JLabel("输入名称进行查询");
        tips.setBounds(new Rectangle(0,70,140,20));
        JButton check = new JButton("查询");
        check.setBounds(new Rectangle(150,70,100,20));
        check.addActionListener( e -> {
            ISBNCode.setText("");
            priceField.setText("");
            numberField.setText("");
            switch (kinds.getSelectedIndex()){
                case 0 ->{
                    Books book = Books.exist(nameField.getText());
                    if (book != null){
                        ISBNCode.setText(book.getISBN());
                        priceField.setText(String.valueOf(book.getPrice()));
                        numberField.requestFocus();
                    }else {
                        ISBNCode.setEnabled(true);
                        priceField.setEnabled(true);
                        ISBNCode.requestFocus();
                    }
                    numberField.setEnabled(true);
                }
                case 1 ->{
                    CACP cacp = CACP.exist(nameField.getText());
                    if (cacp != null){
                        ISBNCode.setText("");
                        priceField.setText(String.valueOf(cacp.getPrice()));
                        numberField.requestFocus();
                    }else {
                        priceField.setEnabled(true);
                        priceField.requestFocus();
                    }
                    numberField.setEnabled(true);
                }
                case 2 ->{
                    Sundry sundry = Sundry.exist(nameField.getText());
                    if (sundry != null){
                        ISBNCode.setText("");
                        priceField.setText(String.valueOf(sundry.getPrice()));
                        numberField.requestFocus();
                    }else {
                        priceField.setEnabled(true);
                        priceField.requestFocus();
                    }
                    numberField.setEnabled(true);
                }
            }
        });

        //ISBN
        JLabel ISBN = new JLabel("ISBN");
        ISBN.setBounds(new Rectangle(0,100,100,20));
        ISBNCode.setBounds(new Rectangle(110,100,200,20));
        ISBNCode.setEnabled(false);

        //原价
        JLabel price = new JLabel("原价");
        price.setBounds(new Rectangle(0,130,100,20));
        priceField.setBounds(new Rectangle(110,130,200,20));
        priceField.setEnabled(false);

        //数量
        JLabel number = new JLabel("数量");
        number.setBounds(new Rectangle(0,160,100,20));
        numberField.setBounds(new Rectangle(110,160,200,20));
        numberField.setEnabled(false);

        //添加
        JButton addTo = new JButton("添加");
        addTo.setBounds(new Rectangle(150,200,100,20));
        addTo.addActionListener(e -> {
            if (numberField.getText().isEmpty() || priceField.getText().isEmpty() || numberField.getText().isEmpty()){
                JOptionPane.showMessageDialog(null,"请完整填写信息！","错误",JOptionPane.ERROR_MESSAGE);
            }else if (isNumeric(priceField.getText())){
                JOptionPane.showMessageDialog(null,"价格只能为数字！","错误",JOptionPane.ERROR_MESSAGE);
                priceField.requestFocus();
            }else if (isNumeric(numberField.getText())){
                JOptionPane.showMessageDialog(null,"数量只能为数字！","错误",JOptionPane.ERROR_MESSAGE);
                numberField.requestFocus();
            }else {
                if (0==kinds.getSelectedIndex()){
                    if (ISBNCode.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null,"请填写ISBN码！","错误",JOptionPane.ERROR_MESSAGE);
                        ISBNCode.requestFocus();
                    }else {
                        Books.add(nameField.getText(),ISBNCode.getText(),Double.parseDouble(priceField.getText()),Integer.parseInt(numberField.getText()));
                        ISBNCode.setText("");
                        nameField.setText("");
                        priceField.setText("");
                        numberField.setText("");
                    }
                }else if (1==kinds.getSelectedIndex()){
                    CACP.add(nameField.getText(),Double.parseDouble(priceField.getText()),Integer.parseInt(numberField.getText()));
                    ISBNCode.setText("");
                    nameField.setText("");
                    priceField.setText("");
                    numberField.setText("");
                }else {
                    Sundry.add(nameField.getText(),Double.parseDouble(priceField.getText()),Integer.parseInt(numberField.getText()));
                    ISBNCode.setText("");
                    nameField.setText("");
                    priceField.setText("");
                    numberField.setText("");
                }
                ISBNCode.setEnabled(false);
                priceField.setEnabled(false);
                numberField.setEnabled(false);
            }
        });

        kinds.addActionListener(e ->{
            if (2==kinds.getSelectedIndex() || 1==kinds.getSelectedIndex()){
                ISBNCode.setEnabled(false);
                ISBNCode.setText("");
                nameField.setText("");
                priceField.setText("");
                numberField.setText("");
            }
            if (0==kinds.getSelectedIndex()){ISBNCode.setEnabled(true);}
        });

        jf.setContentPane(jp);
        jp.add(add);
        jp.add(kinds);
        jp.add(ISBN);
        jp.add(ISBNCode);
        jp.add(name);
        jp.add(nameField);
        jp.add(tips);
        jp.add(check);
        jp.add(price);
        jp.add(priceField);
        jp.add(number);
        jp.add(numberField);
        jp.add(addTo);
        jf.setResizable(false);
        jf.setVisible(true);
    }

    public static boolean isNumeric(String str){
        String reg = "^[0-9]+(.[0-9]+)?$";
        return !str.matches(reg);
    }

    public static String getBooksSrc() {
        return BooksSrc;
    }

    public static String getCACPSrc() {
        return CACPSrc;
    }

    public static String getSundriesSrc() {
        return sundriesSrc;
    }
}