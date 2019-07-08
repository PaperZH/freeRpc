package rpc.free.registry.zookpeer;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import rpc.free.common.util.InetUtil;
import rpc.free.common.util.RegistryUtil;
import rpc.free.registry.ServiceRegistry;

/**
 * @program: rpcfree
 * @description: ZooKeeper实现的服务注册类
 * @author: guodong.zhang
 * todo:
 * @create: 2019-05-20 09:57
 */
public class ServiceRegistryImpl implements ServiceRegistry {
  private static Logger LOGGER = LoggerFactory.getLogger(ServiceRegistryImpl.class);
  private String zkConn;
  private String ip;
  private int port;
  private CuratorFramework curatorFramework;

  public ServiceRegistryImpl(String zkConn,int port){
    this.zkConn = zkConn;
    this.ip = InetUtil.getLocalIp();
    this.port = port;
  }


  @Override
  public void registry(String serviceName, String serviceAddress) {

    String ipPortStr = ip+":"+port;

    curatorFramework= CuratorFrameworkFactory.builder()
        .connectString(zkConn)
        .connectionTimeoutMs(2000)
        .sessionTimeoutMs(5000)
        .retryPolicy(new ExponentialBackoffRetry(1000,3))
        .build();

    curatorFramework.start();
    String serviceBasePath = RegistryUtil.getServicePath(serviceName);
    //创建节点，递归创建父节点
    try{
      curatorFramework.create()
          .creatingParentContainersIfNeeded()
          .forPath(serviceBasePath);
    }catch (Exception e){
      if(e.getMessage().contains("NodeExist")){
        LOGGER.info("Path already exist");
      }else{
        LOGGER.error("create path error");
        throw new RuntimeException("register error");
      }
    }

    boolean registerSuccesss=false;
    while(!registerSuccesss){
      try{
        String s = curatorFramework.create()
            .withMode(CreateMode.PERSISTENT)
            .forPath(serviceBasePath+"/"+ipPortStr,serviceAddress.getBytes());
        registerSuccesss = true;
//       List array = curatorFramework.getChildren().forPath(serviceBasePath);
//       System.out.println(array);
//       LOGGER.info("获得的数据:{}",array);
//        CuratorFramework curatorFramework1= CuratorFrameworkFactory.builder()
//            .connectString("10.103.13.214:8888")
//            .connectionTimeoutMs(2000)
//            .sessionTimeoutMs(5000)
//            .retryPolicy(new ExponentialBackoffRetry(1000,3))
//            .build();
//        curatorFramework1.start();
//
//        Stat stat = curatorFramework1.checkExists().forPath(serviceBasePath);
//        System.out.println(array);
//        LOGGER.info("获得的数据:{}",array);
      } catch (Exception e){
        try{
          Thread.sleep(1000);
        } catch (InterruptedException e1){
          e1.printStackTrace();
        }
        LOGGER.info("Retry Register ZK, {}", e.getMessage());
        try {
          curatorFramework.delete().forPath(serviceBasePath + "/" + ipPortStr);
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      }
    }
  }

  @Override
  public void unRegister(String serviceName){
    LOGGER.info("UnRegister zookeeper");
    try{
      String serviceInstancePath = RegistryUtil.getServiceInstancePath(serviceName,ip,port);
      curatorFramework.delete().forPath(serviceInstancePath);
    } catch (Exception e) {
      LOGGER.error("unregister error: {}",e.getMessage());
      e.printStackTrace();
    }
  }
}
