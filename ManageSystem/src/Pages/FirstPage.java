package Pages;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FirstPage {
    private static final File file = new File("");
    private static final String path = file.getAbsolutePath();
    private static final JTextArea tasks = new JTextArea();
    private static final JTextArea wordsGiven = new JTextArea();
    private static final String DailyBBSrc = path + "\\data\\dailyBB.txt";
    private static final String tasksSrc = path + "\\data\\tasks.txt";
    private static final String wordsSrc = path + "\\data\\wordsGiven.txt";
    static {
        File dailyBB = new File(DailyBBSrc);
        File tasks = new File(tasksSrc);
        File wordsGiven = new File(wordsSrc);
        try{
        if (!dailyBB.exists()){
            dailyBB.createNewFile();
        }
        if (!tasks.exists()){
            tasks.createNewFile();
        }
        if (!wordsGiven.exists()){
            wordsGiven.createNewFile();
        }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void firstPage(String userName,boolean isAdmin) {
        JFrame jf = new JFrame();
        if (isAdmin){
            jf.setTitle("图书管理系统(管理员)(欢迎，" + userName + "!)");
        }else {
            jf.setTitle("图书管理系统(欢迎，" + userName + "!)");
        }
        jf.setSize(1550,840);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //容器
        JPanel jp = new JPanel(null);

        //toolBar
        JPanel tools = new JPanel(null);
        tools.setBounds(new Rectangle(2,10,1650,40));
        tools.setBorder(BorderFactory.createLineBorder(Color.black));
        JLabel toolBars = new JLabel("工具栏");
        toolBars.setBounds(new Rectangle(0,10,100,20));

        //图书录入
        JButton addBooks = new JButton("录入登记");
        addBooks.setBounds(new Rectangle(110,10,100,20));
        addBooks.addActionListener(e -> AddingPage.addingPage());

        //设置
        JButton settings = new JButton("设置");
        settings.setBounds(new Rectangle(220,10,100,20));
        settings.addActionListener(e -> SettingPage.settingPage());

        //卖书
        JButton sellBooks = new JButton("卖出登记");
        sellBooks.setBounds(new Rectangle(330,10,100,20));
        sellBooks.addActionListener(e -> SellPage.sellPage());
        sellBooks.setEnabled(true);

        //物品查找
        String[] kinds = {"图书","文创","杂物"};
        JComboBox<String> seekBox = new JComboBox<>(kinds);
        seekBox.setBounds(new Rectangle(490,10,50,20));
        JTextField name = new JTextField();
        name.setBounds(new Rectangle(550,10,300,20));
        JButton seeking = new JButton("查找");
        seeking.setBounds(new Rectangle(865,10,100,20));
        seeking.addActionListener(e -> {
            if (!name.getText().isEmpty()){
                SeekingResultPage.seekingResultPage(name.getText(),seekBox.getSelectedIndex());
            }
        });

        //寄语添加（限管理员）
        JButton wordsGivenButton = new JButton("寄语");
        wordsGivenButton.setBounds(new Rectangle(990,10,100,20));
        wordsGivenButton.addActionListener( e -> WordsGivenAddingPage.wordsGivenAddingPage());
        wordsGivenButton.setVisible(false);
        if (isAdmin){
            wordsGivenButton.setVisible(true);
        }

        //任务添加（限管理员）
        JButton taskButton = new JButton("任务");
        taskButton.setBounds(new Rectangle(1110,10,100,20));
        taskButton.addActionListener( e -> TaskAddingPage.taskAddingPage());
        taskButton.setVisible(false);
        if (isAdmin){
            taskButton.setVisible(true);
        }

        tools.add(toolBars);
        tools.add(addBooks);
        tools.add(sellBooks);
        tools.add(settings);
        tools.add(seekBox);
        tools.add(name);
        tools.add(seeking);
        tools.add(wordsGivenButton);
        tools.add(taskButton);

        //每日bb记录
        JTextArea dailyBBRecords = new JTextArea();
        dailyBBRecords.setBounds(new Rectangle(5,60,1000,360));
        dailyBBRecords.setBorder(BorderFactory.createLineBorder(Color.black));
        dailyBBRecords.setFont(new Font("宋体",Font.BOLD,15));
        dailyBBRecords.setEnabled(false);
        dailyBBRecords.setLineWrap(true);
        JScrollPane jScrollPane = new JScrollPane(dailyBBRecords,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setBounds(new Rectangle(5,60,1000,360));

        //每日bb写入
        JTextArea dailyBBWrite = new JTextArea();
        dailyBBWrite.setBounds(new Rectangle(5,425,1000,355));
        dailyBBWrite.setBorder(BorderFactory.createLineBorder(Color.black));
        dailyBBWrite.setFont(new Font("宋体",Font.BOLD,16));
        dailyBBWrite.setLineWrap(true);
        JScrollPane jScrollPane2 = new JScrollPane(dailyBBWrite,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setBounds(new Rectangle(5,425,1000,355));

        JButton save = new JButton("保存");
        save.setBounds(new Rectangle(900,782,100,15));
        try{
            String s = "";
            String string ;
            Reader dailyIn = new FileReader(DailyBBSrc);
            BufferedReader bufferedReader = new BufferedReader(dailyIn);
            int i = 0;
            while ((string = bufferedReader.readLine() )!= null){
                if (i == 0){
                    s= string;
                }else{
                    s = s + "\n"+ string;
                }
                dailyBBRecords.setText(s);
                i ++;
            }
        }catch (IOException ioException){
            ioException.printStackTrace();
        }

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dailyBBWrite.append("\n" + format.format(date) +" by " + userName);
        dailyBBWrite.append("\n---------------------------------------------------------");
        save.addActionListener(e -> {
            try {
                Writer dailyBBOut = new FileWriter(DailyBBSrc,true);
                BufferedWriter bufferedWriter = new BufferedWriter(dailyBBOut);
                bufferedWriter.write(dailyBBWrite.getText());
                dailyBBWrite.setText("\n" + format.format(date) +" by " + userName);
                dailyBBWrite.append("\n---------------------------------------------------------");
                bufferedWriter.flush();
                bufferedWriter.close();
                dailyBBOut.close();

                String s = "";
                String str ;
                Reader dailyIn = new FileReader(DailyBBSrc);
                BufferedReader bufferedReader = new BufferedReader(dailyIn);
                int i = 0;
                while ((str = bufferedReader.readLine() )!= null){
                    if (i ==0){
                        s = str;
                    }else{
                        s = s + "\n" + str;
                    }
                    dailyBBRecords.setText(s);
                    i++;
                }
            }catch (IOException exp){
                exp.printStackTrace();
            }
        });

        //天气
        JPanel weather = new JPanel();
        weather.setBounds(new Rectangle(1020,60,500,260));
        weather.setBorder(BorderFactory.createLineBorder(Color.black));

        //寄语
        wordsGiven.setBounds(new Rectangle(1020,330,500,200));
        wordsGiven.setBorder(BorderFactory.createLineBorder(Color.black));
        wordsGiven.setFont(new Font("楷体",Font.BOLD+Font.ITALIC,18));
        wordsGiven.setEnabled(false);
        wordsGiven.setLineWrap(true);
        showGivenWords();


        //任务
        tasks.setBounds(new Rectangle(1020,540,500,240));
        tasks.setBorder(BorderFactory.createLineBorder(Color.black));
        tasks.setFont(new Font("宋体",Font.BOLD,16));
        tasks.setEnabled(false);
        tasks.setLineWrap(true);
        showTasks();


        //添加组件
        jf.setContentPane(jp);
        jp.add(tools);
        jp.add(jScrollPane);
        jp.add(jScrollPane2);
        jp.add(save);
        jp.add(weather);
        jp.add(wordsGiven);
        jp.add(tasks);
        jf.setResizable(false);
        jf.setVisible(true);
        dailyBBWrite.requestFocus();
    }

    public static void showTasks(){
        try{
            String s = "";
            String str;
            Reader taskOut = new FileReader(tasksSrc);
            BufferedReader bufferedReader = new BufferedReader(taskOut);
            int i = 0;
            while ((str = bufferedReader.readLine()) != null){
                if (i ==0){
                    s = str;
                }else{
                    s = s + "\n" + str;
                }
                tasks.setText(s);
                i ++;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public static void showGivenWords(){
        try{
            String s = "";
            String str;
            Reader taskOut = new FileReader(wordsSrc);
            BufferedReader bufferedReader = new BufferedReader(taskOut);
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

    public static String getTasksSrc() {
        return tasksSrc;
    }

    public static String getWordsSrc() {
        return wordsSrc;
    }
}