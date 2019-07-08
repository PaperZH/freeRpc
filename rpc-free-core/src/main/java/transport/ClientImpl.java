package transport;


import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import proxy.ClientProxy;
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
    /**
      *发布的服务名称用来寻找发布服务的提供者
      */
  private String serviceName;
  private EventLoopGroup eventLoopGroup = new NioEventLoopGroup(2);
  private String zkConn;
  private int requestTimeoutTimeMills = 10*1000;
  private CuratorFramework curatorFramework;
  private Class<? extends ClientProxy> clientProxyClass;
  private ClientProxy cliet;

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
