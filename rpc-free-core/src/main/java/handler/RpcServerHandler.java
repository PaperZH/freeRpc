package handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import rpc.free.common.model.RpcRequest;
import rpc.free.common.model.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Method;

/**
 * @program: rpcfree
 * @description: rpc服务器
 * @author: guodong.zhang
 * todo:
 * @create: 2019-05-16 16:11
 */
public class RpcServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

  private static final Logger LOGGER = LoggerFactory.getLogger(RpcServerHandler.class);

  private Object service;

  public RpcServerHandler(Object servicImpl){
    this.service = servicImpl;
  }
  @Override
  protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcRequest rpcRequest) throws Exception {
    String methodName = rpcRequest.getMethodName();
    Object[] params = rpcRequest.getParameters();
    Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
    String requestId = rpcRequest.getRequestId();
    Method method = service.getClass().getDeclaredMethod(methodName,parameterTypes);
    Object invoke = method.invoke(service,params);
    RpcResponse rpcResponse = new RpcResponse();
    rpcResponse.setRequestId(requestId);
    rpcResponse.setResult(invoke);
    channelHandlerContext.pipeline().writeAndFlush(rpcResponse);
  }
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    LOGGER.error("rpc.free.common.model.Exception caught on {},",ctx.channel(),cause);
    ctx.channel().close();
  }
}
