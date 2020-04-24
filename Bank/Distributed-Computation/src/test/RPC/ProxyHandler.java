package test.RPC;


import test.Interface.MyAspect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyHandler implements InvocationHandler {
    static String beforeMethod = "";
    static String afterMethod = "";
    Object obj;
    
    public ProxyHandler(Object v){this.obj=v;}
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        XmlReader.readXml("src/aop.xml");
        Class<?> clazz = MyAspect.class;
        //处理before方法
        if(beforeMethod!=null&&beforeMethod.length()>0){
            Method m=clazz.getMethod(beforeMethod);
            Object obj = clazz.newInstance();
            m.invoke(obj);
        }
        
        Object ret=method.invoke(obj,args);//obj是要代理的真实目标对象

        //处理after方法
        if(afterMethod!=null&&afterMethod.length()>0){
            Method m=clazz.getMethod(afterMethod);
            Object obj = clazz.newInstance();
            m.invoke(obj);
        }
        
        return ret;
    }

}
