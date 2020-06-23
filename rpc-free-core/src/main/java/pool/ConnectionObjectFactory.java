package pool;

import handler.ClientChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: rpcfree
 * @description: 连接工厂类
 * @author: guodong.zhang
 * todo:
 * @create: 2019-05-16 18:25
 */
public class ConnectionObjectFactory extends BasePooledObjectFactory<Channel> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionObjectFactory.class);

  private String ip;

  private int port;

  private int connectTimes = 3;

  public ConnectionObjectFactory(String ip,int port){
    this.ip = ip;
    this.port = port;
  }

  public ConnectionObjectFactory(String ip,int port,int connectTimes){
    this.ip = ip;
    this.port = port;
    this.connectTimes = connectTimes;
  }
/**********************************************1、连接新的channel Start **************************************/
  private Channel connectNewChannel(){
    Bootstrap bootstrap = new Bootstrap();
    bootstrap.group(new NioEventLoopGroup())
            .channel(NioSocketChannel.class)
            .handler(new ClientChannelInitializer());
    try{
      final ChannelFuture channelFuture = bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS,3000)
          .option(ChannelOption.TCP_NODELAY,true)
          .connect(ip,port).sync();
      final Channel channel = channelFuture.channel();
      addChannelListener(channelFuture,channel);
      return channel;
    } catch (InterruptedException e) {
      LOGGER.error("Interrupted {}",e);
      Thread.currentThread().interrupt();
    }
    return null;
  }

  private void addChannelListener(ChannelFuture future, Channel channel){
    future.addListener(new ChannelFutureListener() {
      @Override
      public void operationComplete(ChannelFuture future) throws Exception {
        if(future.isSuccess()){
          LOGGER.info("Connect success {}" ,future);
        }
      }
    });

    channel.closeFuture().addListener(new ChannelFutureListener() {
      @Override
      public void operationComplete(ChannelFuture future) throws Exception {
        LOGGER.info("Channel close {}:{}", ip, port);
      }
    });
  }
/************************************************* end ******************************************************/

  @Override
  public Channel create() throws Exception {
    for(int i = 0;i<connectTimes;i++){
      Channel channel = connectNewChannel();
      if(null != channel){
        return channel;
      }
    }
    return null;
  }

  @Override
  public void destroyObject(PooledObject<Channel> p) throws Exception {
    p.getObject().close().addListener(new ChannelFutureListener() {
      @Override
      public void operationComplete(ChannelFuture future) throws Exception {
        LOGGER.info("CLOSE FINISH");
      }
    });
  }


  @Override
  public boolean validateObject(PooledObject<Channel> p) {
    return p.getObject().isActive();
  }

  @Override
  public PooledObject<Channel> wrap(Channel channel) {
    return new DefaultPooledObject<>(channel);
  }

}
