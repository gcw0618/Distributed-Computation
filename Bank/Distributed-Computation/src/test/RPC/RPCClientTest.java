package test.RPC;


import test.Interface.CheckLoginService;
import test.JSON.RoundRobin;
import test.JSON.ServerSite;

import java.net.InetSocketAddress;
import java.util.Scanner;

//启动完RPCClientServer之后启动
public class RPCClientTest {
    public static void main(String[] args) throws Exception {
        int loginkey=0;
        String name=null;
        String psw=null;
        Scanner in=new Scanner(System.in);

        //轮询获得服务器地址和端口
        RoundRobin robin = new RoundRobin();
        ServerSite site = robin.testRoundRobin();
        
        while(loginkey==0){
            System.out.println("输入用户名:");
            name=in.nextLine();
            System.out.println("输入密码");
            psw=in.nextLine();
            System.out.println("输入结束");
            System.out.println("验证中.....");

            /*name="gcw";
            psw="gcw";*/
//            System.out.println(site.getHost()+site.getPort());

            //反射
            CheckLoginService service= Client.getRemoteProxyObj(
                    Class.forName("test.Interface.CheckLoginService") ,
                    new InetSocketAddress(site.getHost(), site.getPort())) ;
            loginkey=service.check_login(name,psw) ;
            if(loginkey==0) System.out.println("登入失败");
        }
        //in.close();
        if(loginkey==1) {
            System.out.println("登入成功");
            ClientOperation operation=new ClientOperation();
            operation.operation(name,site.getHost(),site.getPort());
        }
        if(loginkey==-1) System.out.println("已被冻结");
    }
}

