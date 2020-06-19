package rpc.free.server;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * @param
 * @description:
 * @return:
 * @author: zhangguodong12
 * @time: 2020/6/19 17:22
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface RpcService {

  /**
   * 服务接口类
   */
  Class<?> value();

  /**
   * 服务版本号
   */
  String version() default "";
}
