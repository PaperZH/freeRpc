package rpc.free.common.Exception;

/**
 * @program: rpcfree
 * @description: 普通异常
 * @author: guodong.zhang
 * todo:
 * @create: 2019-05-16 14:38
 */
public class SimpleException extends RuntimeException{
  public SimpleException(){}
  public SimpleException(String message){super(message);}
  public SimpleException(Throwable cause){super(cause);}
}
