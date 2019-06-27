package proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc.free.common.Exception.SimpleException;
import transport.Client;
import transport.ClientImpl;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @program: rpcfree
 * @description: jdk自带的代理，用接口映射代理，对不能实现代理
 * @author: guodong.zhang
 * todo:
 * @create: 2019-05-16 17:12
 */
public class JdkClientProxy implements ClientProxy{
  private static Logger LOGGER = LoggerFactory.getLogger(JdkClientProxy.class);
  private static Method hashCodeMethod;
  private static Method equalsMethod;
  private static Method toStringMethod;
  static {
    try{
      hashCodeMethod = Object.class.getMethod("hashCode");
      equalsMethod = Object.class.getMethod("equals",Object.class);
      toStringMethod = Object.class.getMethod("toString");
    }catch (Exception e){
      LOGGER.error("JDK proxy error: {}",e.getMessage());
    }
  }
  @Override
  public <T> T proxyInterface(final Client client, final Class<T> serviceInterface) {
    Object proxyInstance = Proxy.newProxyInstance(ClientImpl.class.getClassLoader()
        ,new Class[]{serviceInterface}
        ,(proxy,method,args)->{
          if(hashCodeMethod.equals(method)){
            return proxyHashCode(proxy);
          }
          if(equalsMethod.equals(method)){
            return proxyEquals(proxy,args[0]);
          }
          if(toStringMethod.equals(method)){
            return proxyToString(proxy);
          }
          try{
            return client.sendMessage(serviceInterface,method,args).getResult();
          } catch (Exception e){
            throw new SimpleException(e);
          }
        });
    return (T) proxyInstance;
  }

  private int proxyHashCode(Object proxy){
    return System.identityHashCode(proxy);
  }

  private boolean proxyEquals(Object proxy,Object other){
    return proxy==other;
  }

  private String proxyToString(Object proxy){
    return proxy.getClass().getName()+"@"+Integer.toHexString(proxy.hashCode());
  }
}
