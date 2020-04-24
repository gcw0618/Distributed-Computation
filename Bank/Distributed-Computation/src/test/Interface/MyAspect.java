package test.Interface;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * MyAspect
 */
public class MyAspect {
    public void check(){
        System.out.println("检查登入。。。。");
    }
    public void accept() {System.out.println("检查通过。。。。");}
    public boolean testServerConnect(String ip,int port) {
        boolean result = false;
        Socket connect = new Socket();
        try {
            connect.connect(
                    new InetSocketAddress(ip, port), 1000); //连接服务器，每隔1秒重试
            result = connect.isConnected();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                connect.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}