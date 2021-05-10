package transport;


import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pool.ChannelPool;
import rpc.free.common.model.RpcRequest;
import rpc.free.common.model.RpcResponse;
import rpc.free.registry.zookpeer.ServiceDiscovery;
import rpc.free.registry.zookpeer.impl.ServiceDiscoveryImpl;

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
  private int requestTimeoutTimeMills;
  private ServiceDiscovery serviceDiscovery;
  // 存放字符串Channel对应的map
  public static CopyOnWriteArrayList<ChannelWrapper> channelWrappers;

  public ClientImpl(String serviceName, String zkConn, int requestTimeoutTimeMills, ServiceDiscovery serviceDiscovery) {
    this.serviceName = serviceName;
    this.zkConn = zkConn;
    this.requestTimeoutTimeMills = requestTimeoutTimeMills;
    this.serviceDiscovery = serviceDiscovery;
  }

  @Override
  public RpcResponse sendMessage(Class<?> clazz, Method method, Object[] args) {
    RpcRequest request = new RpcRequest();
    request.setRequestId(String.valueOf(atomicLong.incrementAndGet()));
    request.setMethodName(method.getName());
    request.setParameters(args);
    request.setInterfaceName(serviceName);
    request.setParameters(method.getParameters());

    ChannelWrapper channelWrapper = selectChannel();
    if(null == channelWrapper){
      RpcResponse rpcResponse = new RpcResponse();
      RuntimeException runtimeException = new RuntimeException("channel is not active now");
      rpcResponse.setException(runtimeException);
      return rpcResponse;
    }

    ServiceDiscovery serviceDiscovery = new ServiceDiscoveryImpl(zkConn);
    String address = serviceDiscovery.discovery(serviceName);
    String ip = address.split(":")[0];
    int port = Integer.parseInt(address.split(":")[1]);

    Channel channel = ChannelPool.getChannel(ip, port);
    try {
      channel.writeAndFlush(request).sync();
    } catch (InterruptedException e) {
      LOGGER.info("[rpc-free-client-ClientImpl]:", e);
    }

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
