package transport;

import rpc.free.common.model.RpcResponse;

import java.lang.reflect.Method;

/**
 * @program: rpcfree
 * @description: 客户端
 * @author: guodong.zhang
 * todo:
 * @create: 2019-05-16 16:52
 */
public abstract class Client {
  public abstract RpcResponse sendMessage(Class<?> clazz, Method method, Object[] args);

  public abstract <T> T proxyInterface(Class<T> serviceInterface);

  public abstract void close();
}
