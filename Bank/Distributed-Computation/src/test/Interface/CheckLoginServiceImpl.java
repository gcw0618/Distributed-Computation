package test.Interface;


import test.RPC.ProxyHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import static test.DB.BaseDao.getConnection;

//登入接口实现

public class CheckLoginServiceImpl implements CheckLoginService {
    @Override
    //检查登入
    public int check_login(String name,String password) throws Exception {
        CheckLoginServiceImpl checkLoginService=new CheckLoginServiceImpl();
        InvocationHandler handler=new ProxyHandler(new CheckLoginServiceImpl());
        CheckLoginService i=(CheckLoginService) Proxy.newProxyInstance(CheckLoginServiceImpl.class.getClassLoader(), CheckLoginServiceImpl.class.getInterfaces(), handler);
        
        String sql="select * from infor where name=? and password=?";
        try (Connection conn=getConnection();
             PreparedStatement stmt= conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, password);
            ResultSet rs1 = stmt.executeQuery();
            int fail=i.check_fail(name);
            if(fail>=3){ 
                System.out.println("用户"+name+"因登入失败超过3次，被冻结");
                return -1; 
            }
            if(rs1.next()&&fail<3) {
                checkLoginService.clear_fail(name);
                System.out.println("用户"+name+"已登入");
                return 1;
            }
            else{
                checkLoginService.addition_fail(name,fail);
                System.out.println("用户"+name+"密码错误，登入失败，累计次数+1");
                return 0;
            }
        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }
    @Override
    //查看错误次数
    public int check_fail(String name) throws Exception {
        int fail = 0;
        String sql="select failure from infor where name=? ";
        try (Connection conn=getConnection();
             PreparedStatement stmt= conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                fail=rs.getInt("failure");
            }
           return fail;
        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }
    @Override
    //登入成功，清空错误次数
    public void clear_fail(String name) throws Exception {
        String sql="UPDATE infor set failure=0 WHERE name=?";
        try (Connection conn=getConnection();
             PreparedStatement stmt= conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
            return;
        }catch (SQLException e){
            e.printStackTrace();
            return ;
        }
    }
    @Override
    //错误次数+1
    public void addition_fail(String name,int fail) throws Exception {
        String sql="UPDATE infor set failure=? WHERE name=?";
        try (Connection conn=getConnection();
             PreparedStatement stmt= conn.prepareStatement(sql)) {
            stmt.setInt(1, fail+1);
            stmt.setString(2, name);
            stmt.executeUpdate();
            return;
        }catch (SQLException e){
            e.printStackTrace();
            return ;
        }
    }


}
