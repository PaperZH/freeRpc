package rpc.free.registry.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @ProjectName: freeRpc
 * @Package: rpc.free.registry.config
 * @ClassName: ConditionOnProperty
 * @Author: zhangguodong12
 * @Description: 继承condition来判断是否注册
 * @Date: 2020/6/19 18:37
 * @Version: 1.0
 */
public class ConditionOnProperty implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {

        return false;
    }
}
