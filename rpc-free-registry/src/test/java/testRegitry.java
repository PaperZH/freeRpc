import org.junit.Test;
import rpc.free.registry.zookpeer.ServiceDiscovery;
import rpc.free.registry.zookpeer.ServiceRegistry;
import rpc.free.registry.zookpeer.impl.ServiceDiscoveryImpl;
import rpc.free.registry.zookpeer.impl.ServiceRegistryImpl;

/**
 * @program: rpcfree
 * @description: test the registry of Zookeeper
 * @author: guodong.zhang
 * todo:
 * @create: 2019-06-24 09:50
 */
public class testRegitry {
  private static String zkConn = "127.0.0.1:2181";
  @Test
  public void Registy() throws Exception {
    ServiceRegistry serviceRegistry = new ServiceRegistryImpl(zkConn,8888);
    serviceRegistry.registry("testService","guodong1");
    System.out.println("注册成功");
  }

  @Test
  public void discovery(){
    ServiceDiscovery serviceDiscovery = new ServiceDiscoveryImpl(zkConn);
    try{
      String data = serviceDiscovery.discovery("testService");
      System.out.println("发现服务成功"+data);
    }catch (Exception e){
      e.printStackTrace();
      System.out.println("发现服务异常");
    }
  }
}
