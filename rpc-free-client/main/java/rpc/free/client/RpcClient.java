package rpc.free.client;

import bootstrap.ClientBootStart;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;
import rpc.free.common.util.ConfigPropertes;
import rpc.free.registry.zookpeer.ServiceDiscovery;
import rpc.free.registry.zookpeer.impl.ServiceDiscoveryImpl;

import java.util.Map;
import java.util.Properties;

/**
 * @ProjectName: freeRpc
 * @Package: rpc.free.client
 * @ClassName: RpcClient
 * @Author: zhangguodong12
 * @Description: Rpc客户端
 * @Date: 2020/6/22 16:37
 * @Version: 1.0
 */
public class RpcClient implements ApplicationContextAware, InitializingBean {

    private String serviceName;
    private String zkCon;
    private String ip;
    private int port;

    @Override
    public void afterPropertiesSet() {
        afterInit();
        ClientBootStart.connectToServer(ip,port);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        init(applicationContext);
    }

    private void init(ApplicationContext applicationContext){
        Properties properties= ConfigPropertes.configProperty();
        zkCon = properties.getProperty("free.rpc.service.zkCon");
        Map<String, Object> servers = applicationContext.getBeansWithAnnotation(EnableRpcClient.class);
        if (!servers.isEmpty()) {
            for (Object object : servers.values()) {
                EnableRpcClient rpcService = object.getClass().getAnnotation(EnableRpcClient.class);
                serviceName = rpcService.appName();
                String rpcServerVersion = rpcService.version();
                if (!StringUtils.isEmpty(rpcServerVersion)) {
                    serviceName += "-" + rpcServerVersion;
                }
                break;
            }
        }
    }

    private void afterInit(){
        ServiceDiscovery serviceDiscovery = new ServiceDiscoveryImpl(zkCon);
        String address = serviceDiscovery.discovery(serviceName);
        ip = address.split(":")[0];
        port = Integer.parseInt(address.split(":")[1]);
    }


}
