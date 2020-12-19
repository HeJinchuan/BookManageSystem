package Pages;

import Datas.Books;
import Datas.CACP;
import Datas.Sundry;

import javax.swing.*;
import java.awt.*;

public class SeekingResultPage {

    public static void seekingResultPage(String name,int index) {
        JFrame jf = new JFrame("查找结果");
        JButton sellButton = new JButton("出售");
        jf.setSize(400,300);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //主面板
        JPanel jp = new JPanel(null);

        //名称
        JTextField nameField = new JTextField();
        nameField.setBounds(new Rectangle(0,20,300,30));
        nameField.setEnabled(false);

        //类型
        JTextField kindsField = new JTextField();
        kindsField.setBounds(new Rectangle(0,65,300,30));
        kindsField.setEnabled(false);

        //价格
        JTextField priceField = new JTextField();
        priceField.setBounds(new Rectangle(0,110,300,30));
        priceField.setEnabled(false);

        //数量
        JTextField numberField = new JTextField();
        numberField.setBounds(new Rectangle(0,155,300,30));
        numberField.setEnabled(false);

        int num = 0;
        switch (index){
            case 0 ->{
                Books aimBook = Books.exist(name);
                if (aimBook != null){
                    num = aimBook.getNumber();
                    nameField.setText("名字:  " + aimBook.getName());
                    kindsField.setText("种类:  图书");
                    priceField.setText("价格:  " + (aimBook.getPrice()));
                    numberField.setText("数量：  " + (aimBook.getNumber()));
                }else {
                    nameField.setText("名字:  没找到");
                    kindsField.setText("种类:  图书");
                    priceField.setText("价格:  没找到");
                    numberField.setText("数量：  没找到");
                }
            }
            case 1 ->{
                CACP aimCACP = CACP.exist(name);
                if (aimCACP != null){
                    num = aimCACP.getNumber();
                    nameField.setText("名字:  " + aimCACP.getName());
                    kindsField.setText("种类:  文创");
                    priceField.setText("价格:  " + (aimCACP.getPrice()));
                    numberField.setText("数量：  " + (aimCACP.getNumber()));
                }else {
                    nameField.setText("名字:  没找到");
                    kindsField.setText("种类:  文创");
                    priceField.setText("价格:  没找到");
                    numberField.setText("数量：  没找到");
                }
            }
            case 2 ->{
                Sundry aimSundry = Sundry.exist(name);
                if (aimSundry != null){
                    num = aimSundry.getNumber();
                    nameField.setText("名字:  " + aimSundry.getName());
                    kindsField.setText("种类:  杂物");
                    priceField.setText("价格:  " + (aimSundry.getPrice()));
                    numberField.setText("数量：  " + (aimSundry.getNumber()));
                }else {
                    nameField.setText("名字:  没找到");
                    kindsField.setText("种类:  杂物");
                    priceField.setText("价格:  没找到");
                    numberField.setText("数量：  没找到");
                }
            }
        }

        //出售数量
        JLabel sellNumLabel = new JLabel("出售数量");
        sellNumLabel.setBounds(new Rectangle(0,200,60,30));
        JTextField sellNumField = new JTextField(3);
        sellNumField.setBounds(new Rectangle(70,200,50,30));
        if (num == 0){
            sellNumField.setEnabled(false);
            sellButton.setEnabled(false);
        }

        //出售
        sellButton.setBounds(new Rectangle(130,200,100,30));
        sellButton.addActionListener( e -> {
            if (sellNumField.getText().isEmpty() || isNumeric(sellNumField.getText()) || Integer.parseInt(sellNumField.getText())<1){
                JOptionPane.showMessageDialog(null,"数量输入错误！","错误",JOptionPane.ERROR_MESSAGE);
                sellNumField.requestFocus();
            }else {
                switch (index){
                    case 0 -> Books.sell(name,Integer.parseInt(sellNumField.getText()));
                    case 1 -> CACP.sell(name,Integer.parseInt(sellNumField.getText()));
                    case 2 -> Sundry.sell(name,Integer.parseInt(sellNumField.getText()));
                }
            }
            jf.dispose();
        });

        jf.setContentPane(jp);
        jp.add(nameField);
        jp.add(kindsField);
        jp.add(priceField);
        jp.add(numberField);
        jp.add(sellNumLabel);
        jp.add(sellNumField);
        jp.add(sellButton);
        jf.setResizable(false);
        jf.setVisible(true);
    }

    public static boolean isNumeric(String str){
        String reg = "[0-9]*";
        return !str.matches(reg);
    }
}