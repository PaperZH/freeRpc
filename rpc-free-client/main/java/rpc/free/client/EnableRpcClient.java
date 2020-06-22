package rpc.free.client;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @ProjectName: freeRpc
 * @Package: rpc.free.client
 * @ClassName: EnableRpcClient
 * @Author: zhangguodong12
 * @Description: Rpc客户端启动注解类
 * @Date: 2020/6/22 17:34
 * @Version: 1.0
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(RpcClient.class)
public @interface EnableRpcClient {
}
