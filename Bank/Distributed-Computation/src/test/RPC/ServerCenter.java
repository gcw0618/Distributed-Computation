package test.RPC;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//服务中心的具体实现
public class ServerCenter implements Server {
    // map：服务端的所有 可供客户端访问的接口，都注册到该map中
    // key:接口的名字"HelloService" value:真正的HelloService实现
    private static HashMap<String, Class> serviceRegiser = new HashMap<>();
    private static int port;// = 9999
    //连接池：连接池中存在多个连接对象，每个连接对象都可以处理一个客户请求
    private static ExecutorService executor
            = Executors.newFixedThreadPool( Runtime.getRuntime().availableProcessors()   ) ;

    private static boolean isRunning = false;

    public ServerCenter(int port) {
        this.port = port;
    }

    // 开启服务端服务
    @Override
    public void start() {//while(true){start();}

        //start ->线程对象  
        ServerSocket server = null ;
        try {
            server = new ServerSocket();
            server.bind(new InetSocketAddress(port));

        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        isRunning = true ; //服务已经启动
        System.out.println("start  server....");
        while(true) {
            //具体的服务内容：接收客户端请求，处理请求，并返回结果
            //100 :1  1   1 ...1  -->如果想让多个 客户端请求并发执行 
            //-> 多线程
            //客户端每次请求一次连接（发出一次请求），则服务端 从连接池中 
            //获取一个线程对象去处理
            Socket socket = null ;
            try {
                socket = server.accept();// 等待客户端连接
            } catch (IOException e) {
                e.printStackTrace();
            }
            //启动线程 去处理客户请求
            executor.execute(new ServiceTask(socket) );

        }
    }

    @Override
    public void stop() {//关闭服务
        isRunning = false ;
        executor.shutdown();
    }

    @Override
    public void register(Class service, Class serviceImpl) {
        serviceRegiser.put(service.getName(), serviceImpl);
    }

    //socket客户端  - socket服务端（start() 、ServiceTask ）
    private static class ServiceTask implements Runnable{
        private Socket socket ;
        public ServiceTask() {
        }
        public ServiceTask(Socket socket) {
            this.socket = socket ;
        }

        @Override
        public void run() {//线程所做的事情
            ObjectOutputStream output = null;
            ObjectInputStream input = null;
            try {
                // 接收到客户端连接及请求，处理该请求...
                input = new ObjectInputStream(socket.getInputStream()); //
                // 因为ObjectInputStream对 发送数据的顺序 严格要求，
                //因此需要参照发送的顺序逐个接收
                String serviceName = input.readUTF();
                String methodName = input.readUTF();
                // 方法的参数类型 String Integer
                Class[] parameterTypes = (Class[]) input.readObject();
                // 方法的参数名
                Object[] arguments = (Object[]) input.readObject();
                // 根据客户请求，到map中找到 与之对应的 具体接口
                // HelloService
                Class ServiceClass = serviceRegiser.get(serviceName);

                Method method = ServiceClass.getMethod(methodName, parameterTypes);
                // 执行该方法
                Object result = method.invoke(ServiceClass.newInstance(), arguments); // person.say(参数列表);

                // 将发放执行完毕的返回值 传给客户端

                output = new ObjectOutputStream(socket.getOutputStream());
                output.writeObject(result);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

    }





}

