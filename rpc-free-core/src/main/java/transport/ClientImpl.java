package transport;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc.free.common.model.RpcRequest;
import rpc.free.common.model.RpcResponse;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @program: rpcfree
 * @description: 客户端实现类
 * @author: guodong.zhang
 * todo:
 * @create: 2019-05-16 17:03
 */
public class ClientImpl extends Client {
  private static final Logger LOGGER = LoggerFactory.getLogger(ClientImpl.class);
  private AtomicLong atomicLong = new AtomicLong();

  @Override
  public RpcResponse sendMessage(Class<?> clazz, Method method, Object[] args) {
    RpcRequest request = new RpcRequest();
    request.setRequestId(String.valueOf(atomicLong.incrementAndGet()));
    request.setMethodName(method.getName());
    request.setParameters(args);
    request.setInterfaceName(clazz);
    request.setParameters(method.getParameterTypes());


    return null;
  }
  @Override
  public <T> T proxyInterface(Class<T> serviceInterface) {
    return null;
  }

  @Override
  public void close() {

  }
}
