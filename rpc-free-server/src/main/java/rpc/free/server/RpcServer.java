package rpc.free.server;

import handler.RpcServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.slf4j.Logger;
import org.springframework.util.StringUtils;
import rpc.free.common.edcode.ProtocolDecoder;
import rpc.free.common.edcode.ProtocolEncoder;
import rpc.free.registry.ServiceRegistry;

import java.util.Map;

/**
 * @program: rpcfree
 * @description: rpc服务端
 * @author: guodong.zhang
 * todo:
 * @create: 2019-05-21 16:58
 */
public class RpcServer implements ApplicationContextAware,InitializingBean {
  private static final Logger LOGGER = LoggerFactory.getLogger(RpcServer.class);

  private Map<String,Object> handlMap;

  private EventLoopGroup bossGroup = new NioEventLoopGroup();

  private EventLoopGroup workerGroup = new NioEventLoopGroup();

  private String serviceAddress;

  private ServiceRegistry serviceRegistry;

  /**
 * @Author guozhang.zhang01
 * @Description
 * //TODO guodong.zhang
 *这里可以通过配置文件来实现
 * @Date 15:40 2019/7/8
 * @Param [handlMap, serviceAddress, serviceRegistry]
 * @return
 **/
  public RpcServer(Map<String,Object> handlMap,String serviceAddress, ServiceRegistry serviceRegistry){
    this.handlMap = handlMap;
    this.serviceAddress = serviceAddress;
    this.serviceRegistry = serviceRegistry;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    try{
      //创建并且初始化 Netty 服务端 BootStrap
      ServerBootstrap bootstrap = new ServerBootstrap();
      bootstrap.group(bossGroup,workerGroup)
          .channel(NioServerSocketChannel.class)
          .childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
              socketChannel.pipeline()
                  .addLast(new LoggingHandler(LogLevel.INFO))
                  .addLast((ChannelHandler) new ProtocolDecoder(10*1024*1024))
                  .addLast((ChannelHandler) new ProtocolEncoder())
                  .addLast(new RpcServerHandler(handlMap));
            }
          });
      /*channel option 含义
      * ChannelOption.SO_BACKLOG：对应的是tcp/ip协议listen函数中的backlog参数，
      * ChanneOption.SO_REUSEADDR：对应于套接字选项中的SO_REUSEADDR，这个参数表示允许重复使用本地地址和端口
      * Channeloption.SO_KEEPALIVE：参数对应于套接字选项中的SO_KEEPALIVE，该参数用于设置TCP连接
      * ChannelOption.SO_SNDBUF：参数对应于套接字选项中的SO_SNDBUF，ChannelOption.SO_RCVBUF参数对应于套接字选项中的
      * SO_RCVBUF这两个参数用于操作接收缓冲区和发送缓冲区的大小，接收缓冲区用于保存网络协议站内收到的数据，
      * 直到应用程序读取成功，发送缓冲区用于保存发送数据，直到发送成功
      * */
      bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
      bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
      //获取RPC服务器的IP地址与端口号
      String addressArray[] = StringUtils.split(serviceAddress,":");
      String ip = addressArray[0];
      int port = Integer.parseInt(addressArray[1]);
      //启动RPC服务
      ChannelFuture channelFuture = bootstrap.bind(ip,port).sync();
      //注册RPC服务
      if(serviceRegistry != null){
        for(String interfaceName:handlMap.keySet()){
          serviceRegistry.registry(interfaceName,serviceAddress);
          LOGGER.info("register service: {} => {}",interfaceName, serviceAddress);
        }
      }
      LOGGER.info("server started on port {}", port);
      //关闭 RPC服务器
      channelFuture.channel().closeFuture().sync();
    }catch (Exception e){
      LOGGER.error(e.getMessage());
    }finally {
      this.shutdown();
    }
  }

  /**
   * 扫描带有RpcService注解的类并初始化 handlMap
   * @param applicationContext spring配置文件
   * @throws BeansException bean包和子包出现的异常信息
   */
  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    Map<String,Object> serviceBeans = applicationContext.getBeansWithAnnotation(RpcService.class);
    if(!serviceBeans.isEmpty()){
      for(Object object:serviceBeans.values()){
        RpcService rpcService = object.getClass().getAnnotation(RpcService.class);
        String serviceName = rpcService.value().getName();
        String rpcServerVersion = rpcService.version();
        if(!StringUtils.isEmpty(rpcServerVersion)){
          serviceName += "-" + rpcServerVersion;
        }
        handlMap.put(serviceName, object);
      }
    }
  }

  private void shutdown() {
    LOGGER.info("Shutting down server {}", serviceAddress);
    bossGroup.shutdownGracefully();
    workerGroup.shutdownGracefully();
  }
}
