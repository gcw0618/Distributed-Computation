package test.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

/**
 * @author Gam
 * @version 1.0
 * @date 2020/4/20 15:21
 */
public class MainFrame extends JFrame implements ActionListener {
    private JLabel title;
    private JLabel viceTitle;
    private JLabel titleIcon;
    private JLabel notice;
    private JButton checkBt;
    private JButton withdrawBt;
    private JButton saveBt;
    private JButton quitBt;
    private String user;
    private String host;
    private int port;

    public MainFrame(String username,String Host,int Port)
    {
        this.user = username;
        this.host = Host;
        this.port = Port;
        Init();
    }

    public void Init(){

        titleIcon = new JLabel();
        URL url = LoginFrame.class.getResource("yanzu.jpg");
        ImageIcon icon = new ImageIcon(url);
        icon.setImage(icon.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT));
        //
        titleIcon.setIcon(icon);
        titleIcon.setSize(100,100);
        titleIcon.setLocation(0,0);
        //
        title = new JLabel();
        title.setText("分布式彦祖银行");
        title.setSize(400,100);
        Font f = new Font("宋体",Font.BOLD,48);
        title.setFont(f);
        title.setLocation(120,0);

        viceTitle = new JLabel();
        viceTitle.setText("Distributed Yanzu Bank");
        viceTitle.setSize(400,100);
        Font vf = new Font("宋体",Font.BOLD,20);
        viceTitle.setFont(vf);
        viceTitle.setLocation(125,30);

        notice = new JLabel();
        notice.setText("您好，请选择需要的服务！");
        notice.setSize(300,100);
        Font kf = new Font("楷体",Font.PLAIN,24);
        notice.setFont(kf);
        notice.setLocation(110,100);

        //查询按钮
        checkBt = new JButton("查询 Check");
        checkBt.setSize(120, 30);
        checkBt.setLocation(300, 200);

        //取钱按钮
        withdrawBt = new JButton("取钱 Withdraw");
        withdrawBt.setSize(120, 30);
        withdrawBt.setLocation(300, 250);

        //存钱按钮
        saveBt = new JButton("存钱 Save");
        saveBt.setSize(120, 30);
        saveBt.setLocation(300, 300);

        //退出按钮
        quitBt = new JButton("退出 Exit");
        quitBt.setSize(120, 30);
        quitBt.setLocation(50, 300);

        this.add(titleIcon);
        this.add(title);
        this.add(viceTitle);
        this.add(notice);
        this.add(checkBt);
        this.add(withdrawBt);
        this.add(saveBt);
        this.add(quitBt);
        this.setLayout(null);
        this.setSize(500, 400);
        this.setVisible(true);
        checkBt.addActionListener(this);
        withdrawBt.addActionListener(this);
        saveBt.addActionListener(this);
        quitBt.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //获取产生事件的事件源强制转换
        JButton bt = (JButton)e.getSource();
        //获取按钮上显示的文本
        String str = bt.getText();
        System.out.println(str);
        if(str.equals("查询 Check")) {
            this.setVisible(false);
            CheckFrame frame = new CheckFrame(user,host,port);
            frame.setLocationRelativeTo(null);
        }
        else if (str.equals("取钱 Withdraw")){
            this.setVisible(false);
            WithdrawFrame frame = new WithdrawFrame(user,host,port);
            frame.setLocationRelativeTo(null);
        }
        else if (str.equals("存钱 Save")){
            this.setVisible(false);
            SaveFrame frame = new SaveFrame(user,host,port);
            frame.setLocationRelativeTo(null);
        }
        else if (str.equals("退出 Exit"))
        {
            System.out.println("退出！！！");
            this.setVisible(false);
            //返回登陆界面
            LoginFrame frame = new LoginFrame();
            frame.setLocationRelativeTo(null);
        }
    }
}
