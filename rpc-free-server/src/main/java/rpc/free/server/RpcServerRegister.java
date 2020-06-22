package rpc.free.server;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @ProjectName: freeRpc
 * @Package: rpc.free.server
 * @ClassName: RpcServerRegister
 * @Author: zhangguodong12
 * @Description: rpc注解条件启动类
 * @Date: 2020/6/20 14:32
 * @Version: 1.0
 */
public class RpcServerRegister implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

    }
}
