package rpc.free.common.Exception;

/**
 * @program: rpcfree
 * @description: 请求超时异常
 * @author: guodong.zhang
 * todo:
 * @create: 2019-05-16 14:37
 */
public class RequestTimeoutException  extends SimpleException{
  public RequestTimeoutException(String message){super(message);}
}
