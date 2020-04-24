package test.JSON;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author Gam
 * @version 1.0
 * @date 2020/4/16 21:11
 */
public class ServerSite {
    private String Name;
    private String Host;
    private int Port;

    public String getName() { return Name; }

    public void setName(String name) { this.Name = name; }

    public String getHost() {
        return Host;
    }

    public void setHost(String host) {
        Host = host;
    }

    public int getPort() {
        return Port;
    }

    public void setPort(int port) {
        Port = port;
    }

}
