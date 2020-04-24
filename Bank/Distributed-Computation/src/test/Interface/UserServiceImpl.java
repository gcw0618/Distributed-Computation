package test.Interface;

import test.Log4J.vincent_player_framt;
import test.RPC.ProxyHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static test.DB.BaseDao.getConnection;

//用户界面接口实现

public class UserServiceImpl implements UserService {
    @Override
    //取钱
    public double withdraw(String name, double num) throws Exception {
        UserServiceImpl userService=new UserServiceImpl();
        InvocationHandler handler=new ProxyHandler(new UserServiceImpl());
        UserService i=(UserService) Proxy.newProxyInstance(UserServiceImpl.class.getClassLoader(), UserServiceImpl.class.getInterfaces(), handler);
        String sql="select money from infor where name=? ";
        try (Connection conn=getConnection();
             PreparedStatement stmt= conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs1 = stmt.executeQuery();
            double FindMoney=0.0;
            double remain;//余额
            BigDecimal num1=new BigDecimal(Double.toString(num));
            while (rs1.next()){
                FindMoney=rs1.getDouble("money");//获取用户存款
            }
            BigDecimal FindMoney1=new BigDecimal(Double.toString(FindMoney));//用户存款
            if(num<=FindMoney&&num>=0) {//取钱合法
                remain=new Double(FindMoney1.subtract(num1).doubleValue());
                i.update(name,remain);//更新余额
                System.out.println("该用户取钱合法，取钱"+num+"元，剩余"+remain+"元");
                vincent_player_framt vincentPlayerFramt=new vincent_player_framt();
                vincentPlayerFramt.inform(name,FindMoney1,num1,remain);
                return remain;
            }
            else{//取钱不合法
                System.out.println("该用户取钱不合法");
                return -1;
            }
        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    //查询余额
    public double check(String name) throws Exception {
        UserServiceImpl userService = new UserServiceImpl();
        InvocationHandler handler = new ProxyHandler(new UserServiceImpl());
        UserService i=(UserService) Proxy.newProxyInstance(UserServiceImpl.class.getClassLoader(), UserServiceImpl.class.getInterfaces(), handler);
        String sql="select money from infor where name=? ";
        try (Connection conn=getConnection();
             PreparedStatement stmt= conn.prepareStatement(sql)){
            stmt.setString(1, name);
            ResultSet rs1 = stmt.executeQuery();
            double FindMoney=0.0;//用户存款
            while (rs1.next()) {
                FindMoney = rs1.getDouble("money");//获取用户存款
            }
            System.out.println("该用户余额为："+FindMoney+"元");
            return FindMoney;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    //存钱
    public double save(String name, double num) throws Exception {
        UserServiceImpl userService=new UserServiceImpl();
        InvocationHandler handler=new ProxyHandler(new UserServiceImpl());
        UserService i=(UserService) Proxy.newProxyInstance(UserServiceImpl.class.getClassLoader(), UserServiceImpl.class.getInterfaces(), handler);
        String sql="select money from infor where name=? ";
        try (Connection conn=getConnection();
             PreparedStatement stmt= conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs1 = stmt.executeQuery();
            double FindMoney=0.0;//用户存款
            double remain;//余额
            while (rs1.next()){
                FindMoney=rs1.getDouble("money");//获取用户存款
            }
            if(num>0) {//存钱合法
                remain=FindMoney+num;
                i.update(name,remain);//更新余额
                System.out.println("该用户存钱"+num+"元，剩余"+remain+"元");
                return remain;
            }
            else{//取钱不合法
                System.out.println("该用户存钱不合法");
                return -1;
            }
        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }

    }

    @Override
    //更新余款
    public boolean update(String name, double money) throws Exception {
        String sql="update infor set money=? where name=? ";
        try (Connection conn=getConnection();
             PreparedStatement stmt= conn.prepareStatement(sql)) {
            stmt.setDouble(1,money);
            stmt.setString(2,name);
            stmt.executeUpdate();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
