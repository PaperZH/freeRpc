package handler;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.internal.StringUtil;
import rpc.free.common.model.RpcRequest;
import rpc.free.common.model.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @program: rpcfree
 * @description: rpc服务器
 * @author: guodong.zhang
 * todo:
 * @create: 2019-05-16 16:11
 */
@ChannelHandler.Sharable
public class RpcServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

  private static final Logger LOGGER = LoggerFactory.getLogger(RpcServerHandler.class);

  private final Map<String, Object> services;

  public RpcServerHandler(Map handleMap){
    this.services = handleMap;
  }
  @Override
  protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcRequest rpcRequest) throws Exception {

    String methodName = rpcRequest.getMethodName();
    String serviceName = rpcRequest.getInterfaceName();
    String versionName = rpcRequest.getServiceVersion();
    if(!StringUtil.isNullOrEmpty(versionName)){
      serviceName+= "-" + versionName;
    }
    Object serviceBean = services.get(serviceName);
    if(serviceBean == null){
      throw new RuntimeException(String.format("[rpc-free-server-exception]:can't find service of key: s%", serviceName));
    }
    Class<?> serviceClass = serviceBean.getClass();
    Object[] params = rpcRequest.getParameters();
    Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
    String requestId = rpcRequest.getRequestId();
    /*执行反射调用*/
    Method method = serviceClass.getDeclaredMethod(methodName,parameterTypes);
    method.setAccessible(true);
    Object invoke = method.invoke(serviceBean,params);
    /*执行CGLIB调用*/
    /*
     * @Author guozhang.zhang01
     * FastClass serviceFastClass = FastClass.create(serviceClass)
     * FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName, parameterTypes);
     * Object result = serviceFastMethod.invoke(serviceBean, parameters)
     */
    RpcResponse rpcResponse = new RpcResponse();
    rpcResponse.setRequestId(requestId);
    rpcResponse.setResult(invoke);
    channelHandlerContext.pipeline().writeAndFlush(rpcResponse).addListener(ChannelFutureListener.CLOSE);
  }
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    LOGGER.error("[rpc-free-common]: model exception caught on {},",ctx.channel(),cause);
    ctx.channel().close();
  }
}
