package rpc.free.client;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: freeRpc
 * @Package: rpc.free.client
 * @ClassName: RpcClientInterpret
 * @Author: zhangguodong12
 * @Description: RpcClient解释器
 * @Date: 2020/6/22 17:58
 * @Version: 1.0
 */
@Aspect
@Component
@Lazy(false)
public class RpcClientInterpret {
    /**
     * 定义切入点：对要拦截的方法进行定义与限制，如包、类
     *
     * 1、execution(public * *(..)) 任意的公共方法
     * 2、execution（* set*（..）） 以set开头的所有的方法
     * 3、execution（* com.*.annotation.LoggerApply.*（..））com.*.annotation.LoggerApply这个类里的所有的方法
     * 4、execution（* com.*.annotation.*.*（..））com.*.annotation包下的所有的类的所有的方法
     * 5、execution（* com.*.annotation..*.*（..））com.*.annotation包及子包下所有的类的所有的方法
     * 6、execution(* com.*.annotation..*.*(String,?,Long)) com.*.annotation包及子包下所有的类的有三个参数，第一个参数为String类型，第二个参数为任意类型，第三个参数为Long类型的方法
     * 7、execution(@annotation())
     */
    @Pointcut("@annotation(rpc.free.client.RemoteClient)")
    private void remoteMethod() {

    }

    /**
     * 前置：在目标方法执行前调用
     */
    @Before("remoteMethod()")
    public void begin() {
    }

    /**
     * 后置：在目标方法执行后调用，若目标方法出现异常，则不执行
     */
    @AfterReturning("remoteMethod()")
    public void afterReturning() {
    }

    /**
     * 后置：无论目标方法在执行过程中出现一场都会在它之后调用
     */
    @After("remoteMethod()")
    public void after() {
    }

    /**
     * 异常通知：目标方法抛出异常时执行
     */
    @AfterThrowing("remoteMethod()")
    public void afterThrowing() {
    }

    /**
     * 异常通知：目标方法抛出异常时执行
     */
    @Around("remoteMethod()")
    public Object around(ProceedingJoinPoint joinPoint){
        //目标方法名称
        String methodName = joinPoint.getSignature().getName();
        //获取方法传入参数
        Object[] params = joinPoint.getArgs();

        return null;
    }
}
