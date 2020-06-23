package rpc.free.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * @ProjectName: freeRpc
 * @Package: rpc.free.common.util
 * @ClassName: ConfigPropertes
 * @Author: zhangguodong12
 * @Description: property加载类
 * @Date: 2020/6/23 19:03
 * @Version: 1.0
 */
public class ConfigPropertes {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigPropertes.class);

    public static Properties configProperty(){
        Properties properties = new Properties();
        InputStream in = null;
        String confPath = System.getProperty("user.dir");
        confPath = confPath + File.separator + "application.properties";
        File file = new File(confPath);
        if (file.exists()) {
            LOGGER.info("[Rc-free-server-info]:配置文件路径-->>:{}",confPath);
            try {
                in = new FileInputStream(new File(confPath));
            } catch (FileNotFoundException e) {
                LOGGER.info("[Rc-free-server-info]:file not found:{}",confPath);
            }
        }else {
            LOGGER.info("[Rc-free-server-info]:项目路劲【{}】下无连接信息，从classpath下加载",confPath);
            in = ConfigPropertes.class.getClassLoader().getResourceAsStream("application.properties");
        }
        try {
            properties.load(in);
        } catch (IOException e) {
            LOGGER.info("[Rc-free-server-info]:IOException:{}",confPath);
        }
        return properties;
    }
}
