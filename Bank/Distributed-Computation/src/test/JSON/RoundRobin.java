package test.JSON;

import java.net.URL;
import java.util.*;

/**
 * @author Gam
 * @version 1.0
 * @date 2020/4/16 22:29
 */
public class RoundRobin {
    public static Integer index = 0;
//    public static int defaultWeight = 1;
//    Map<String, Integer> serverMap = new HashMap<String, Integer>();

    //获取json文件
    URL temp = json.class.getResource("ServerList.json");
    String path = temp.toString().replace("file:/", "");
    //读取文件获得主机列表
    testJSON testjson = new testJSON();
    List<ServerSite> sites = testjson.readServers(path);

    public static Integer getRandomNumber(Integer max) {
        Random rd = new Random();
        return rd.nextInt(max);
    }

    public int ServerNum(){
        return sites.size();
    }

    public ServerSite testRandom(){
        ServerSite s = new ServerSite();
        int temp = getRandomNumber(sites.size());
        s.setName(sites.get(temp).getName());
        s.setHost(sites.get(temp).getHost());
        s.setPort(sites.get(temp).getPort());
        return s;
    }

    public ServerSite testRoundRobin() {
        ServerSite s = new ServerSite();

        synchronized (index) {
            if (index >= sites.size()) {
                index = 0;
            }
            s.setName(sites.get(index).getName());
            s.setHost(sites.get(index).getHost());
            s.setPort(sites.get(index).getPort());
            System.out.println("index: " + index + ",服务器：" + s.getHost() + ",端口：" + s.getPort());
            index++;
        }
        return s;

//        for (int i=0;i<sites.size();i++) {
//            serverMap.put(sites.get(i).getHost(), defaultWeight);
//            serverMap.put(sites.get(i).getPort(), defaultWeight);
//        }
//        //取得Ip地址list
//        Set<String> keySet = serverMap.keySet();
//        ArrayList<String> keyList = new ArrayList<String>();
//        //取得端口
//        Set<int> portSet =
//            //权重轮询
////            List<String> serverList = new ArrayList<String>();
////            Iterator<String> it = keySet.iterator();
////            while(it.hasNext()){
////                String server = it.next();
////                Integer weight = serverMap.get(server);
////                for (int i = 0; i < weight; i++) {
////                    serverList.add(server);
////                }
////            }
//            keyList.addAll(keySet);
//            String server = null;
//            //当前轮询位置index，为了保证服务器选择的顺序性，需要在操作是对齐加上synchronized锁，使得在同一时刻只有一个线程能够修改index的值，
//            //否则index变量被并发修改是则无法保证服务器选择的顺序性
//            synchronized(index){
//                if(index >= keySet.size()){
//                    index = 0;
//                }
//                s.setHost(keyList.get(index));
//                s.setPort(keyList.get(index));
//                System.out.println("index: "+index+",服务器："+server);
//                index++;
//            }
//            return s;
    }

}
