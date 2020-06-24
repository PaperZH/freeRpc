package pool;

import io.netty.channel.Channel;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc.free.common.util.ConfigPropertes;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ProjectName: freeRpc
 * @Package: pool
 * @ClassName: ChannelPool
 * @Author: zhangguodong12
 * @Description: channel连接池
 * @Date: 2020/6/24 11:39
 * @Version: 1.0
 */
public class ChannelPool {

    private static Logger logger = LoggerFactory.getLogger(ChannelPool.class);

    private final static ConcurrentHashMap<String,GenericObjectPool<Channel>> concurrentMap = new ConcurrentHashMap<>();

    public static Channel getChannel(String ip, int port){
        //concurrentMap 存入，取出复用
        String key = ip+port;
        GenericObjectPool genericObjectPool = null;
        if(null!=concurrentMap.get(key)){
            genericObjectPool = concurrentMap.get(key);
        }else{
            genericObjectPool = init(ip,port);
            concurrentMap.put(key,genericObjectPool);
        }
        Channel channel = null;
        try {
            channel = (Channel) genericObjectPool.borrowObject();
        } catch (Exception e) {
            logger.info("[rpc-free-core-ClientBootStart]:",e);
        }
        return channel;
    }

    private static GenericObjectPool init(String ip, int port){
        // 对象池工厂
        GenericObjectPoolConfig<Channel> poolConfig = new GenericObjectPoolConfig<>();

        // 对象池配置
        ConnectionObjectFactory connectionPoolFactory = new ConnectionObjectFactory(ip, port);

        Properties properties = ConfigPropertes.configProperty();
        poolConfig.setMaxIdle(Integer.parseInt(properties.getProperty("","5")));
        // 最小空闲数, 池中只有一个空闲对象的时候，池会在创建一个对象，并借出一个对象，从而保证池中最小空闲数为1
        poolConfig.setMinIdle(Integer.parseInt(properties.getProperty("","1")));
        // 最大池对象总数
        poolConfig.setMaxTotal(Integer.parseInt(properties.getProperty("","20")));
        // 逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
        poolConfig.setMinEvictableIdleTimeMillis(Integer.parseInt(properties.getProperty("","1800000")));
        // 逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
        poolConfig.setTimeBetweenEvictionRunsMillis(Integer.parseInt(properties.getProperty("","1800000")) * 2L);
        // 在获取对象的时候检查有效性, 默认false
        poolConfig.setTestOnBorrow(Boolean.parseBoolean(properties.getProperty("","true")));
        // 在归还对象的时候检查有效性, 默认false
        poolConfig.setTestOnReturn(Boolean.parseBoolean(properties.getProperty("","false")));
        // 在空闲时检查有效性, 默认false
        poolConfig.setTestWhileIdle(Boolean.parseBoolean(properties.getProperty("","false")));
        // 最大等待时间， 默认的值为-1，表示无限等待。
        poolConfig.setMaxWaitMillis(Integer.parseInt(properties.getProperty("","5000")));
        // 是否启用后进先出, 默认true
        poolConfig.setLifo(true);
        // 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        poolConfig.setBlockWhenExhausted(Boolean.parseBoolean(properties.getProperty("","true")));
        // 每次逐出检查时 逐出的最大数目 默认3
        poolConfig.setNumTestsPerEvictionRun(Integer.parseInt(properties.getProperty("","3")));
        // 对象池
        return new GenericObjectPool<>(connectionPoolFactory, poolConfig);
    }
}
