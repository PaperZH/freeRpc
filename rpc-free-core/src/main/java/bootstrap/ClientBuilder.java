package bootstrap;

import proxy.ClientProxy;
import proxy.JdkClientProxy;

/**
 * @program: rpcfree
 * @description: 客户端创造类 todo:
 * @author: guodong.zhang
 * todo:
 * @create: 2019-07-09 16:15
 */
public class ClientBuilder<T> {
  private String serviceName;
  private String zkConn;
  private Class<T> serviceInterface;
  private int requestTimeOutMillis = 1000;
  private Class<? extends ClientProxy> proxyClass = JdkClientProxy.class;

  public static <T> ClientBuilder<T> builder(){
    return new ClientBuilder<>();
  }

  public ClientBuilder<T> serviceName(String serviceName){

    this.serviceName = serviceName;
    return this;
  }

  public ClientBuilder<T> zkConn(String zkConn){
    this.zkConn = zkConn;
    return this;
  }


}
