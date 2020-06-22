package rpc.free.client;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

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
    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}
