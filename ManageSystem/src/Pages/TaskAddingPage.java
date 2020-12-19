package Pages;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class TaskAddingPage {
    private static final String src = FirstPage.getTasksSrc();
    private static final JTextArea taskAdding = new JTextArea();

    public static void taskAddingPage(){
        JFrame jf = new JFrame("修改任务");
        jf.setSize(400, 300);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //主面板
        JPanel jp = new JPanel(null);

        //文本
        taskAdding.setBounds(new Rectangle(0,0,386,220));
        taskAdding.setBorder(BorderFactory.createLineBorder(Color.black));
        taskAdding.setLineWrap(true);
        showTasks();

        //添加
        JButton add = new JButton("确认修改");
        add.setBounds(new Rectangle(143,230,100,20));
        add.addActionListener( e -> {
            try {
                Writer taskOut = new FileWriter(src);
                BufferedWriter bufferedWriter = new BufferedWriter(taskOut);
                if (taskAdding.getText().isEmpty()){
                    bufferedWriter.write("最近没有任务哦~");
                }else {
                    bufferedWriter.write(taskAdding.getText());
                }
                bufferedWriter.flush();
                bufferedWriter.close();
                showTasks();
            }catch (IOException exp){
                exp.printStackTrace();
            }
            FirstPage.showTasks();
        });

        jf.setContentPane(jp);
        jp.add(taskAdding);
        jp.add(add);
        jf.setResizable(false);
        jf.setVisible(true);
    }

    public static void showTasks(){
        try{
            String s = "";
            String str;
            Reader taskIn = new FileReader(src);
            BufferedReader bufferedReader = new BufferedReader(taskIn);
            int i = 0;
            while ((str = bufferedReader.readLine()) != null){
                if (i ==0){
                    s = str;
                }else{
                    s = s + "\n" + str;
                }
                taskAdding.setText(s);
                i ++;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}