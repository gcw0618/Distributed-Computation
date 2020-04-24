package test.GUI;

import test.Interface.UserService;
import test.RPC.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetSocketAddress;
import java.net.URL;

/**
 * @author Gam
 * @version 1.0
 * @date 2020/4/20 17:17
 */
public class WithdrawFrame extends JFrame implements ActionListener {

    private JLabel title;
    private JLabel viceTitle;
    private JLabel titleIcon;
    private JLabel notice;
    private JTextField userTxt;
    private JButton sureBt;
    private JButton returnBt;
    private String user;
    private String host;
    private int port;

    public WithdrawFrame(String username,String Host,int Port)
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
        icon.setImage(icon.getImage().getScaledInstance(100,100, Image.SCALE_DEFAULT));
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
        notice.setText("请输入取款金额！");
        notice.setSize(300,100);
        Font kf = new Font("楷体",Font.PLAIN,24);
        notice.setFont(kf);
        notice.setLocation(110,100);

        //用户输入框
        userTxt = new JTextField();
        userTxt.setSize(200, 50);
        userTxt.setFont(kf);
        userTxt.setLocation(110, 200);

        //确认按钮
        sureBt = new JButton("确认 Confirm");
        sureBt.setSize(120, 30);
        sureBt.setLocation(50, 300);

        //返回按钮
        returnBt = new JButton("返回 Back");
        returnBt.setSize(120, 30);
        returnBt.setLocation(250, 300);

        this.add(titleIcon);
        this.add(title);
        this.add(viceTitle);
        this.add(notice);
        this.add(userTxt);
        this.add(sureBt);
        this.add(returnBt);
        this.setLayout(null);
        this.setSize(500, 400);
        this.setVisible(true);
        sureBt.addActionListener(this);
        returnBt.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //获取产生事件的事件源强制转换
        JButton bt = (JButton)e.getSource();
        //获取按钮上显示的文本
        String str = bt.getText();
        System.out.println(str);
        if(str.equals("确认 Confirm")) {
            double result;
            double num = Double.parseDouble(userTxt.getText());
            try {
                UserService service= Client.getRemoteProxyObj(
                        Class.forName("test.Interface.UserService") ,
                        new InetSocketAddress(host, port)) ;
                result = service.withdraw(user,num);
                if(result==-1) {
                    //取出金额非法
                    JOptionPane pane = new JOptionPane("取出金额非法！");
                    JDialog dialog  = pane.createDialog(this,"警告");
                    dialog.show();
                }
                else {
                    //取出金额合法
                    JOptionPane pane = new JOptionPane("取出成功！余额现为："+result+" 元。");
                    JDialog dialog  = pane.createDialog(this,"成功");
                    dialog.show();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        else if (str.equals("返回 Back"))
        {
            this.setVisible(false);
            //返回登陆界面
            MainFrame frame = new MainFrame(user,host,port);
            frame.setLocationRelativeTo(null);
        }
    }

}
