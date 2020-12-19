package Pages;

import javax.swing.*;

public class SettingPage {

    public static void settingPage(){
        JFrame jf = new JFrame("设置");
        jf.setSize(400, 300);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //主面板
        JPanel jp = new JPanel(null);

        jf.setContentPane(jp);
        jf.setResizable(false);
        jf.setVisible(true);
    }
}