package test.JSON;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import test.Interface.CheckLoginService;
import test.RPC.Client;
import test.RPC.ClientOperation;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

/**
 * @author Gam
 * @version 1.0
 * @date 2020/4/16 21:44
 */
public class json {

    public static void main(String args[]) throws IOException {

//        URL path = json.class.getClassLoader().getResource("test/JSON/ServerList.json");
//        URL temp = json.class.getResource("ServerList.json");
//        String path = temp.toString().replace("file:/","");
//
//        testJSON testjson = new testJSON();
//        List<ServerSite> sites = testjson.readServers(path);
//        myThread mt = new myThread();
//        myThread mt2 = new myThread();
//        mt.start();
//        mt2.start();
//        for (int i = 0;i<10;i++){
//            System.out.println("主线程：");
//            RoundRobin roundRobin = new RoundRobin();
//            ServerSite site =  roundRobin.testRoundRobin();
////            System.out.println(site);
//        }
//        int loginkey=0;
//        String name=null;
//        String psw=null;
//        Scanner in=new Scanner(System.in);
//
//        while(loginkey==0){
//            System.out.println("输入用户名:");
//            name=in.nextLine();
//            System.out.println("输入密码");
//            psw=in.nextLine();
//            System.out.println("输入结束");
//            System.out.println("验证中.....");
//
//            //轮询获得服务器地址和端口
//            RoundRobin robin = new RoundRobin();
//            ServerSite site = robin.testRoundRobin();
//
//            //反射
//            CheckLoginService service= Client.getRemoteProxyObj(
//                    Class.forName("test.Interface.CheckLoginService") ,
//                    new InetSocketAddress(site.getHost(), site.getPort())) ;
//            loginkey=service.check_login(name,psw) ;
//            if(loginkey==0) System.out.println("登入失败");
//        }
//        //in.close();
//        if(loginkey==1) {
//            System.out.println("登入成功");
//            ClientOperation operation=new ClientOperation();
//            operation.operation(name);
//        }
//        if(loginkey==-1) System.out.println("已被冻结");
//
//
//        System.out.println("["+path+"]\n");
//        System.out.println(sites.get(0).getHost());

//        InputStream inputStream = new FileInputStream("test.json");
//        String text = IOUtils.toString(inputStream,"utf8");
//        List<ServerSite> s= JSON.parseArray(text, ServerSite.class);
//        String path  = "sql.txt";
//        BufferedWriter out = new BufferedWriter(
//                new OutputStreamWriter(new FileOutputStream(path,true)));
//        for (ServerSite d:s) {
//            //String SQL  = "update HDJC_BASE_MONITOR2 set RTSPURL = '"+d.getRtspUrl()+"',NAME = '"+d.getName()+"',UM = '"+d.getUsername()+"',PW = '"+d.getPassword()+"' where ID = '"+d.getId()+"'";
//            String SQL = "insert into test (HOST,PORT)"
//                    + "VALUES('"+d.getHost()+"','"+d.getPort()+"')";
//            System.out.println(SQL);
//            out.write(SQL);
//            out.write("\n");
//            out.flush();
//        }
//        out.close();



    }

}

class myThread extends Thread{
    @Override
    public void run(){
        int loginkey=0;
        String name=null;
        String psw=null;
        Scanner in=new Scanner(System.in);

        while(loginkey==0){
            System.out.println("输入用户名:");
            name=in.nextLine();
            System.out.println("输入密码");
            psw=in.nextLine();
            System.out.println("输入结束");
            System.out.println("验证中.....");

            //轮询获得服务器地址和端口
            RoundRobin robin = new RoundRobin();
            ServerSite site = robin.testRoundRobin();

            //反射
            CheckLoginService service= null;
            try {
                service = Client.getRemoteProxyObj(
                        Class.forName("test.Interface.CheckLoginService") ,
                        new InetSocketAddress(site.getHost(), site.getPort()));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                loginkey=service.check_login(name,psw) ;
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(loginkey==0) System.out.println("登入失败");
        }
        //in.close();
        if(loginkey==1) {
            System.out.println("登入成功");
            ClientOperation operation=new ClientOperation();
            try {
                //operation.operation(name);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(loginkey==-1) System.out.println("已被冻结");
    }
}