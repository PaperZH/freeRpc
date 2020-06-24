package transport;


import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import proxy.ClientProxy;
import rpc.free.common.model.RpcRequest;
import rpc.free.common.model.RpcResponse;

import java.lang.reflect.Method;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
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
  private String serviceName;//这个是用来发现ZooKeeper服务的
  private String zkConn;
  private EventLoopGroup eventLoopGroup = new NioEventLoopGroup(2);
  private int requestTimeoutTimeMills = 10*1000;
  private CuratorFramework curatorFramework;
  private Class<? extends ClientProxy> clientProxyClass;
  private ClientProxy cliet;
  // 存放字符串Channel对应的map
  public static CopyOnWriteArrayList<ChannelWrapper> channelWrappers = new CopyOnWriteArrayList<>();


  @Override
  public RpcResponse sendMessage(Class<?> clazz, Method method, Object[] args) {
    RpcRequest request = new RpcRequest();
    request.setRequestId(String.valueOf(atomicLong.incrementAndGet()));
    request.setMethodName(method.getName());
    request.setParameters(args);
    request.setInterfaceName(serviceName);
    request.setParameters(method.getParameterTypes());

    ChannelWrapper channelWrapper = selectChannel();
    if(null == channelWrapper){
      RpcResponse rpcResponse = new RpcResponse();
      RuntimeException runtimeException = new RuntimeException("channel is not active now");
      rpcResponse.setException(runtimeException);
      return rpcResponse;
    }

    Channel channel = null;

    return null;
  }

  private ChannelWrapper selectChannel() {
    Random random = new Random();
    int size = channelWrappers.size();
    if (size < 1) {
      return null;
    }
    int i = random.nextInt(size);
    return channelWrappers.get(i);
  }

  @Override
  public <T> T proxyInterface(Class<T> serviceInterface) {
    return null;
  }

  @Override
  public void close() {

  }
}
