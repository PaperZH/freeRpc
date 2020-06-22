package handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc.free.common.model.RpcResponse;

import java.util.concurrent.BlockingQueue;


/**
 * @program: rpcfree
 * @description: rpc客户端
 * @author: guodong.zhang
 * todo:
 * @create: 2019-05-16 15:55
 */
@ChannelHandler.Sharable
public class RpcClientHandler extends SimpleChannelInboundHandler<RpcResponse> {
  private static final Logger LOGGER = LoggerFactory.getLogger(RpcClientHandler.class);

  @Override
  protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponse rpcResponse) throws Exception {
    BlockingQueue<RpcResponse> blockingQueue = ResponseHolder.responseMap.get(rpcResponse.getRequestId());
    if(blockingQueue != null){
      blockingQueue.put(rpcResponse);
    } else{
      ResponseHolder.responseMap.get(rpcResponse.getRequestId());
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) throws Exception{
    LOGGER.error("[rpc-free-common]:exception caught on {},",ctx.channel(),cause);
    ctx.channel().close();
  }
}
