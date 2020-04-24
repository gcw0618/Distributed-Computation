package test.JSON;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gam
 * @version 1.0
 * @date 2020/4/16 20:26
 */
public class testJSON {

    private static JSONObject obj;
    private static JSONArray arr;

    private static void OpenFile(String path) {

        try{
            File file = new File(path);
            String str = FileUtils.readFileToString(file);

            obj = JSON.parseObject(str);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

    }

    public static void readServer(String path){

        OpenFile(path);

        arr = obj.getJSONArray("server");

//        DynamicProxyFactory.host = arr.getJSONObject(0).getString("host");
//        DynamicProxyFactory.port = arr.getJSONObject(0).getInteger("port");

    }

    public List<ServerSite> readServers(String path){
        List<ServerSite> result = new ArrayList<>();

        OpenFile(path);

        arr = obj.getJSONArray("server");

        for(int i = 0;i < arr.size();i++){
            ServerSite site = new ServerSite();
            site.setName(arr.getJSONObject(i).getString("name"));
            site.setHost(arr.getJSONObject(i).getString("host"));
            site.setPort(arr.getJSONObject(i).getInteger("port"));
            result.add(site);
        }

        return result;

    }

}
