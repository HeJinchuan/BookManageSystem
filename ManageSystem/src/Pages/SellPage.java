package Pages;

import Datas.Books;
import Datas.CACP;
import Datas.Sundry;

import javax.swing.*;
import java.awt.*;

public class SellPage extends JFrame {

    public static void sellPage(){
        JFrame jf = new JFrame("卖出");
        jf.setSize(400, 240);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //主面板
        JPanel jp = new JPanel(null);

        //种类
        JLabel kindsLabel = new JLabel("商品类型");
        kindsLabel.setBounds(new Rectangle(5,10,50,30));
        String[] kinds = {"图书","文创","杂物"};
        JComboBox<String> kindsBox = new JComboBox<>(kinds);
        kindsBox.setBounds(new Rectangle(65,10,100,30));

        //名字
        JLabel nameLabel = new JLabel("名字");
        JTextField nameField = new JTextField();
        nameLabel.setBounds(new Rectangle(5,60,50,30));
        nameField.setBounds(new Rectangle(65,60,200,30));

        //数量
        JLabel numLabel = new JLabel("数量");
        JTextField numField = new JTextField();
        numLabel.setBounds(new Rectangle(5,110,50,30));
        numField.setBounds(new Rectangle(65,110,200,30));

        //出售
        JButton sellButton = new JButton("出售");
        sellButton.setBounds(new Rectangle(150,170,100,30));
        sellButton.addActionListener( e -> {
            if (nameField.getText().isEmpty()){
                JOptionPane.showMessageDialog(null,"输入名称","错误",JOptionPane.ERROR_MESSAGE);
                nameField.requestFocus();
            }else if (numField.getText().isEmpty() || isNumeric(numField.getText()) || Integer.parseInt(numField.getText()) < 1){
                JOptionPane.showMessageDialog(null,"数量输入错误！","错误",JOptionPane.ERROR_MESSAGE);
                numField.requestFocus();
            } else {
                switch (kindsBox.getSelectedIndex()){
                    case 0 -> Books.sell(nameField.getText(),Integer.parseInt(numField.getText()));
                    case 1 -> CACP.sell(nameField.getText(),Integer.parseInt(numField.getText()));
                    case 2 -> Sundry.sell(nameField.getText(),Integer.parseInt(numField.getText()));
                }
            }
            jf.dispose();
        });

        jf.setContentPane(jp);
        jp.add(kindsLabel);
        jp.add(kindsBox);
        jp.add(nameLabel);
        jp.add(nameField);
        jp.add(numLabel);
        jp.add(numField);
        jp.add(sellButton);
        jf.setResizable(false);
        jf.setVisible(true);
    }

    public static boolean isNumeric(String str){
        String reg = "[0-9]*";
        return !str.matches(reg);
    }
}