package bootstrap;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import pool.ConnectionObjectFactory;

import java.nio.channels.Channel;

/**
 * @ProjectName: freeRpc
 * @Package: bootstrap
 * @ClassName: RpcClient
 * @Author: zhangguodong12
 * @Description: client启动类
 * @Date: 2020/6/23 19:52
 * @Version: 1.0
 */
public class ClientBootStart implements ApplicationContextAware, InitializingBean {

    private String ip;

    private int port;
    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 对象池工厂
        BasePooledObjectFactory connectionPoolFactory = new ConnectionObjectFactory(ip, port);
        // 对象池配置
        GenericObjectPoolConfig<Channel> objectPoolConfig = new GenericObjectPoolConfig<>();
        objectPoolConfig.setMaxTotal(5);
        // 对象池
        GenericObjectPool<Channel> personPool = new GenericObjectPool<Channel>(connectionPoolFactory, objectPoolConfig);




    }
}
