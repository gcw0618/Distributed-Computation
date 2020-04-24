package test.GUI;

import test.Interface.CheckLoginService;
import test.Interface.MyAspect;
import test.JSON.RoundRobin;
import test.JSON.ServerSite;
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
 * @date 2020/4/20 15:17
 */
public class LoginFrame extends JFrame implements ActionListener {
    private JLabel title;
    private JLabel viceTitle;
    private JLabel titleIcon;
    private JLabel userLa;
    private JLabel pwdLa;
    private JLabel verCodeLa;//验证码
    private JTextField userTxt;
    private JPasswordField pwdTxt;
    private JTextField verCodeTxt;//验证码
    private JButton sureBt;
    private JButton quitBt;
    private Mypanel mp;
    private JLabel pay;

    //构造方法
    public LoginFrame()
    {
        Init();
    }
    public void Init()
    {
        Frame frame = new Frame("登录");

        //创建出控件对象（因为上面只是声明出来，并没有给出实际的空间)
        //
        titleIcon = new JLabel();
        URL url = LoginFrame.class.getResource("yanzu.jpg");
        ImageIcon icon = new ImageIcon(url);
        icon.setImage(icon.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT));

        titleIcon.setIcon(icon);
        titleIcon.setSize(100,100);
        titleIcon.setLocation(0,0);

        url = LoginFrame.class.getResource("pay.jpg");
        ImageIcon payIcon = new ImageIcon(url);
        payIcon.setImage(payIcon.getImage().getScaledInstance(60,60,Image.SCALE_DEFAULT));

        pay = new JLabel();
        pay.setIcon(payIcon);
        pay.setSize(60,60);
        pay.setLocation(400,300);

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


        //用户文本
        userLa = new JLabel();
        userLa.setText("用户名：");
        userLa.setSize(100, 60);
        userLa.setLocation(100, 100);
        userLa.setFont(vf);
        //密码文本
        pwdLa = new JLabel();
        pwdLa.setText("密 码：");
        pwdLa.setSize(100, 60);
        pwdLa.setLocation(100, 160);
        pwdLa.setFont(vf);
        //用户输入框
        userTxt = new JTextField();
        userTxt.setSize(120, 30);
        //this.setSize(width, height)
        userTxt.setLocation(170, 115);
        userTxt.setFont(vf);
        //密码输入框
        pwdTxt = new JPasswordField();
        pwdTxt.setSize(120, 30);
        pwdTxt.setLocation(170, 175);
        pwdTxt.setFont(vf);
        //确认按钮
        sureBt = new JButton("登录");
        sureBt.setSize(60, 25);
        sureBt.setLocation(135, 270);

        //退出按钮
        quitBt = new JButton("退出");
        quitBt.setSize(60, 25);
        quitBt.setLocation(240, 270);

        //验证码文本
        verCodeLa = new JLabel();
        verCodeLa.setText("验证码：");
        verCodeLa.setSize(60, 50);
        verCodeLa.setLocation(100,185);

        //验证码文本框
        verCodeTxt = new JTextField();
        verCodeTxt.setSize(100, 20);
        verCodeTxt.setLocation(170, 200);

        //验证码
        mp = new Mypanel();
        mp.setSize(100, 30);
        mp.setLocation(280, 195);

        this.setLayout(null);
        this.setSize(500, 400);
        this.add(titleIcon);
        this.add(title);
        this.add(viceTitle);
        this.add(userLa);
        this.add(pwdLa);
        this.add(userTxt);
        this.add(pwdTxt);
        this.add(sureBt);
        this.add(quitBt);
        this.add(pay);
//        this.add(verCodeLa);
//        this.add(verCodeTxt);
//        this.add(mp);
        sureBt.addActionListener(this);
        quitBt.addActionListener(this);
        this.setVisible(true);

    }
    //具体事件的处理
    public void actionPerformed(ActionEvent e)
    {
        //获取产生事件的事件源强制转换
        JButton bt = (JButton)e.getSource();
        //获取按钮上显示的文本
        String str = bt.getText();

        int serverNum;

        int loginkey = 0;

        if(str.equals("登录"))
        {
            if(!CheckIsNull())
            {
                //获取用户所输入的用户名
                String user = userTxt.getText().trim();
                //获取用户所输入的密码
                String pwd = pwdTxt.getText().trim();

                //轮询获得服务器地址和端口
                RoundRobin robin = new RoundRobin();
//                serverNum = robin.ServerNum();
                serverNum = 10;

                //获取切面检测是否正常连接到服务器
                MyAspect aspect = new MyAspect();
                boolean answer = false;
                //正确的地址
                ServerSite trueSite = null;

                while (true){
//                    ServerSite site = robin.testRoundRobin();
                    ServerSite site = robin.testRandom();
                    answer = aspect.testServerConnect(site.getHost(),site.getPort());

                    if (answer){
                        trueSite = site;
                        System.out.println("服务器 "+site.getName()+" 通过测试连接，可用");
                        break;
                    }
                    if (serverNum==1) break;
                    serverNum--;
                }

//                ServerSite site = robin.testRoundRobin();
                if (trueSite!=null){
                    System.out.println("地址成功转接:"+trueSite.getHost());
                    //反射
                    CheckLoginService service= null;
                    try {
                        service = Client.getRemoteProxyObj(
                                Class.forName("test.Interface.CheckLoginService") ,
                                new InetSocketAddress(trueSite.getHost(), trueSite.getPort()));
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        loginkey=service.check_login(user,pwd) ;
                        System.out.println("你的登陆密钥状态为："+loginkey);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    if(loginkey==1)
                    {
                        //如果错误则弹出一个显示框
                        JOptionPane pane = new JOptionPane("服务器："+trueSite.getName()+" 将为您服务！");
                        JDialog dialog  = pane.createDialog(this,"连接成功");
                        dialog.show();
                        //隐藏当前登录窗口
                        this.setVisible(false);
                        //验证成功创建一个主窗口
                        MainFrame frame = new MainFrame(user,trueSite.getHost(),trueSite.getPort());
                        frame.setLocationRelativeTo(null);
                    }
                    else if (loginkey==0)
                    {
                        //如果错误则弹出一个显示框
                        JOptionPane pane = new JOptionPane("登入失败!");
                        JDialog dialog  = pane.createDialog(this,"警告");
                        dialog.show();
                    }else if (loginkey==-1){
                        //如果输入三次错误则冻结账户
                        JOptionPane pane = new JOptionPane("账户已冻结！");
                        JDialog dialog  = pane.createDialog(this,"警告");
                        dialog.show();
                    }
                }else {
                    //没有服务器在线
                    JOptionPane pane = new JOptionPane("无服务器在线!");
                    JDialog dialog  = pane.createDialog(this,"警告");
                    dialog.show();
                }
            }
        }
        else
        {
            //调用系统类中的一个正常退出
            System.exit(0);
        }
    }
    private boolean CheckIsNull()
    {
        boolean flag = false;
        if(userTxt.getText().trim().equals(" "))
        {
            flag = true;
        }
        else
        {
            if(pwdTxt.getText().trim().equals(" "))
            {
                flag = true;
            }
        }
        return flag;
    }
    private boolean checkUserAndPwd(String user,String pwd) {
        boolean result = false;
        if (user.equals("1")&&pwd.equals("1")) return true;
        else return false;
//        try
//        {
//            FileReader  file = new FileReader("D:\\Workspaces\\MyEclipse 8.5\\testGUI.txt");
//            BufferedReader bre =  new BufferedReader(file);
//            String str = bre.readLine();
//
//            while(str!=null)
//            {
//                String[] strs = str.split(",");
//                if(strs[0].equals(user))
//                {
//                    if(strs[1].equals(pwd))
//                        result = true;
//                }
//                str = bre.readLine();
//            }
//            file.close();
//        }catch(Exception ex)
//        {
//            System.out.print("");
//        }
//        return result;
    }
}
