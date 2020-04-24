package test.RPC;

import test.Interface.CheckLoginService;
import test.Interface.CheckLoginServiceImpl;
import test.Interface.UserService;
import test.Interface.UserServiceImpl;


//先启动
public class RPCServerTest {
    public static void main(String[] args) {
        //开启一个线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                //服务中心
                Server server = new ServerCenter(9999);
                //将XXXX接口及实现类 注册到 服务中心
                server.register(CheckLoginService.class, CheckLoginServiceImpl.class);
                server.register(UserService.class, UserServiceImpl.class);
                server.start();
            }
        }).start();//start()
    }
}
