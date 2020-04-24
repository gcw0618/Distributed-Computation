package test.RPC;

import test.Interface.UserService;
import test.JSON.RoundRobin;
import test.JSON.ServerSite;

import java.net.InetSocketAddress;
import java.util.Scanner;

public class ClientOperation {
    public void operation(String username,String Host,int Port) throws Exception{
        UserService service= Client.getRemoteProxyObj(
                Class.forName("test.Interface.UserService") ,
                new InetSocketAddress(Host, Port)) ;
        String opt = "";
        double num;
        double result;
        while(!opt.equals("退出")){
            Scanner in=new Scanner(System.in);
            System.out.println("输入操作： 查询 | 取钱 | 存钱 | 退出");
            opt=in.nextLine();
            switch (opt){
                case "查询":
                    result = service.check(username);
                    System.out.println("余额为："+result+"元");
                    break;
                case "取钱":
                    System.out.println("输入取款金额：");
                    num = in.nextDouble();
                    result = service.withdraw(username,num);
                    if(result==-1) System.out.println("取钱超过数额！");
                    else System.out.println("剩余："+result+"元");
                    break;
                case "存钱":
                    System.out.println("输入存款金额：");
                    num = in.nextDouble();
                    result = service.save(username,num);
                    if(result==-1) System.out.println("存款金额应为非负数！");
                    else System.out.println("剩余："+result+"元");
                    break;
                case "退出":
                    System.out.println("退出成功...");
                    break;
                default:
                    System.out.println("输入错误！");
                    break;    
            }
        }
    }
}
