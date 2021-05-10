package bootstrap;

import proxy.ClientProxy;
import proxy.JdkClientProxy;
import rpc.free.registry.zookpeer.ServiceDiscovery;
import transport.Client;
import transport.ClientImpl;

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
  private Integer requestTimeOutMillis;
  private ServiceDiscovery serviceDiscovery;

  public static <T> ClientBuilder<T> builder(){
    return new ClientBuilder<>();
  }

  public  Client buildClient(){
    return new ClientImpl( serviceName,  zkConn, requestTimeOutMillis, serviceDiscovery);
  }

  public ClientBuilder<T> serviceName(String serviceName){
    this.serviceName = serviceName;
    return this;
  }

  public ClientBuilder<T> zkConn(String zkConn){
    this.zkConn = zkConn;
    return this;
  }

  public ClientBuilder<T> serviceDiscovery(ServiceDiscovery serviceDiscovery){
    this.serviceDiscovery = serviceDiscovery;
    return this;
  }

  public ClientBuilder<T> requestTimeOutMillis(Integer requestTimeOutMillis){
    this.requestTimeOutMillis = requestTimeOutMillis;
    return this;
  }

}
