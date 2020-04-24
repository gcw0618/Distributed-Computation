package test.Interface;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;

public interface UserService {
    double withdraw(String name, double num) throws Exception;//取钱
    double save(String name,double num) throws Exception;
    double check(String name) throws Exception;//查询余额
    boolean update(String name,double money) throws Exception;//更新余款
}
