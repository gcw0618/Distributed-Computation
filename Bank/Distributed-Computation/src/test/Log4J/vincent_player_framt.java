package test.Log4J;

import org.apache.log4j.*;

import java.math.BigDecimal;

public class vincent_player_framt {
    private static Logger logger = Logger.getLogger(vincent_player_framt.class);

    public static void main(String[] args) throws Exception {
        // 记录debug级别的信息  
        logger.debug("This is debug message.");
        // 记录info级别的信息  
        //logger.info("This is info message.");
        // 记录error级别的信息  
        logger.error("This is error message.");
    }
    public void inform(String name, BigDecimal money, BigDecimal num,double remain){
        String s="用户"+name+"有存款"+money+"元。取钱"+num+"元。余额"+remain+"元";
        logger.info(s);
    }
    
    
}
