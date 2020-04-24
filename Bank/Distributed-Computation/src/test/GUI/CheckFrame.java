package test.GUI;

import test.Interface.UserService;
import test.RPC.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetSocketAddress;
import java.net.URL;
import java.rmi.Remote;

/**
 * @author Gam
 * @version 1.0
 * @date 2020/4/20 19:06
 */
public class CheckFrame extends JFrame implements ActionListener {

    private JLabel title;
    private JLabel viceTitle;
    private JLabel titleIcon;
    private JLabel notice;
    private JLabel Remain;
    private JButton returnBt;
    private String user;
    private String host;
    private String result;
    private int port;


    public CheckFrame(String username, String Host, int Port) {
        this.user = username;
        this.host = Host;
        this.port = Port;
        Init();
    }

    public void Init() {
        try {
            UserService service = Client.getRemoteProxyObj(
                    Class.forName("test.Interface.UserService"),
                    new InetSocketAddress(host, port));
            result = String.valueOf(service.check(user));
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        titleIcon = new JLabel();
        URL url = LoginFrame.class.getResource("yanzu.jpg");
        ImageIcon icon = new ImageIcon(url);
        icon.setImage(icon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        //
        titleIcon.setIcon(icon);
        titleIcon.setSize(100, 100);
        titleIcon.setLocation(0, 0);
        //
        title = new JLabel();
        title.setText("分布式彦祖银行");
        title.setSize(400, 100);
        Font f = new Font("宋体", Font.BOLD, 48);
        title.setFont(f);
        title.setLocation(120, 0);

        viceTitle = new JLabel();
        viceTitle.setText("Distributed Yanzu Bank");
        viceTitle.setSize(400, 100);
        Font vf = new Font("宋体", Font.BOLD, 20);
        viceTitle.setFont(vf);
        viceTitle.setLocation(125, 30);

        notice = new JLabel();
        notice.setText("您的余额为：");
        notice.setSize(300, 100);
        Font kf = new Font("楷体", Font.PLAIN, 24);
        notice.setFont(kf);
        notice.setLocation(110, 100);
        //余额
        Remain = new JLabel();
        Remain.setText(result+" 元");
        Remain.setSize(300, 100);
        Font rf = new Font("黑体", Font.BOLD, 48);
        Remain.setFont(rf);
        Remain.setLocation(110, 150);
        //返回按钮
        returnBt = new JButton("返回 Back");
        returnBt.setSize(120, 30);
        returnBt.setLocation(250, 300);

        this.add(titleIcon);
        this.add(title);
        this.add(viceTitle);
        this.add(notice);
        this.add(Remain);
        this.add(returnBt);
        this.setLayout(null);
        this.setSize(500, 400);
        this.setVisible(true);
        returnBt.addActionListener(this);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //获取产生事件的事件源强制转换
        JButton bt = (JButton) e.getSource();
        //获取按钮上显示的文本
        String str = bt.getText();
        if (str.equals("返回 Back")) {
            this.setVisible(false);
            //返回登陆界面
            MainFrame frame = new MainFrame(user, host, port);
            frame.setLocationRelativeTo(null);
        }
    }
}
