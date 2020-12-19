package Pages;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class WordsGivenAddingPage {
    private static final String src = FirstPage.getWordsSrc();
    private static final JTextArea wordsGiven = new JTextArea();

    public static void wordsGivenAddingPage(){
        JFrame jf = new JFrame("修改寄语");
        jf.setSize(400, 300);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //主面板
        JPanel jp = new JPanel(null);

        //文本
        wordsGiven.setBounds(new Rectangle(0,0,386,220));
        wordsGiven.setBorder(BorderFactory.createLineBorder(Color.black));
        wordsGiven.setLineWrap(true);
        showGivenWords();

        //添加
        JButton add = new JButton("确认修改");
        add.setBounds(new Rectangle(143,230,100,20));
        add.addActionListener( e -> {
            try {
                Writer wordsGivenOut = new FileWriter(src);
                BufferedWriter bufferedWriter = new BufferedWriter(wordsGivenOut);
                if (wordsGiven.getText().isEmpty()){
                    bufferedWriter.write("部长们很忙，带火加油干！");
                }else {
                    bufferedWriter.write(wordsGiven.getText());
                }
                bufferedWriter.flush();
                bufferedWriter.close();
                showGivenWords();
            }catch (IOException exp){
                exp.printStackTrace();
            }
            FirstPage.showGivenWords();
        });

        jf.setContentPane(jp);
        jp.add(wordsGiven);
        jp.add(add);
        jf.setResizable(false);
        jf.setVisible(true);
    }

    public static void showGivenWords(){
        try {
            String s = "";
            String str;
            Reader wordsGivenIn = new FileReader(src);
            BufferedReader bufferedReader = new BufferedReader(wordsGivenIn);
            int i = 0;
            while ((str = bufferedReader.readLine()) != null){
                if (i ==0){
                    s = str;
                }else{
                    s = s + "\n" + str;
                }
                wordsGiven.setText(s);
                i ++;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}