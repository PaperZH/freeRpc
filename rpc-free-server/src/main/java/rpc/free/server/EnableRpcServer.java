package rpc.free.server;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @ProjectName: freeRpc
 * @Package: rpc.free.server
 * @ClassName: EnableRpcServer
 * @Author: zhangguodong12
 * @Description: 自启动注解类
 * @Date: 2020/6/20 14:24
 * @Version: 1.0
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(RpcServer.class)
public @interface EnableRpcServer {
}
