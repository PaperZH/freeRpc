package rpc.free.client;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ProjectName: freeRpc
 * @Package: rpc.free.client
 * @ClassName: RomoteClient
 * @Author: zhangguodong12
 * @Description: rpc方法调用注解
 * @Date: 2020/6/22 17:36
 * @Version: 1.0
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface RomoteClient {
    /**
     * 服务接口类名称
     */
    String serviceName();

    /**
     * 服务版本号
     */
    String version() default "";
}
