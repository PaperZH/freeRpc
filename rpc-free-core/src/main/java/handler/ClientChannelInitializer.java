package handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import rpc.free.common.edcode.ProtocolDecoder;
import rpc.free.common.edcode.ProtocolEncode;

/**
 * @program: rpcfree
 * @description: 通道初始化信息
 * @author: guodong.zhang
 * todo:
 * @create: 2019-05-17 09:45
 */
public class ClientChannelInitializer extends ChannelInitializer<Channel> {
  @Override
  protected void initChannel(Channel channel) throws Exception {
    channel.pipeline().addLast(new LoggingHandler(LogLevel.INFO))
        .addLast((ChannelHandler) new ProtocolDecoder(10*1024*1024))
        .addLast((ChannelHandler) new ProtocolEncode())
        .addLast(new RpcClientHandler());
  }
}
