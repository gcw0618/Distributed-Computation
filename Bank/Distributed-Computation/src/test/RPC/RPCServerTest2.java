package test.RPC;

import test.Interface.CheckLoginService;
import test.Interface.CheckLoginServiceImpl;
import test.Interface.UserService;
import test.Interface.UserServiceImpl;

/**
 * @author Gam
 * @version 1.0
 * @date 2020/4/22 15:18
 */
public class RPCServerTest2 {
    public static void main(String[] args) {
        //开启一个线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                //服务中心
                Server server = new ServerCenter(8888);
                //将XXXX接口及实现类 注册到 服务中心
                server.register(CheckLoginService.class, CheckLoginServiceImpl.class);
                server.register(UserService.class, UserServiceImpl.class);
                server.start();
            }
        }).start();//start()
    }
}
