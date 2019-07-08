package rpc.free.registry.zookpeer;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.GetChildrenBuilder;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import rpc.free.common.util.RegistryUtil;
import rpc.free.registry.ServiceDiscovery;
import org.apache.commons.collections4.CollectionUtils;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @program: rpcfree
 * @description: ZooKeeper发现服务实现类
 * @author: guodong.zhang
 * todo:
 * @create: 2019-05-20 09:54
 */
public class ServiceDiscoveryImpl implements ServiceDiscovery {

  private static Logger LOGGER = LoggerFactory.getLogger(ServiceRegistryImpl.class);

  private String zkAddress;

  private CuratorFramework curatorFramework;

  public ServiceDiscoveryImpl(String zkAddress){
    this.zkAddress = zkAddress;
  }

  @Override
  public String discovery(String serviceName) {
    curatorFramework= CuratorFrameworkFactory.builder()
        .connectString(zkAddress)
        .connectionTimeoutMs(2000)
        .sessionTimeoutMs(5000)
        .retryPolicy(new ExponentialBackoffRetry(1000,3))
        .build();
    curatorFramework.start();
    final GetChildrenBuilder childrenBuilder = curatorFramework.getChildren();
    String serviceZKPath = RegistryUtil.getServicePath(serviceName);

    try{
      List<String> serviceAddress = childrenBuilder.forPath(serviceZKPath);
      if(CollectionUtils.isEmpty(serviceAddress)){
        throw new RuntimeException("No service available for "+serviceName);
      }
      int size = serviceAddress.size();
      String address;
      //若只有一个地址
      if(size == 1 ){
        address = serviceAddress.get(0);
        LOGGER.info("get only address node :{}",address);
      }else{
        address = serviceAddress.get(ThreadLocalRandom.current().nextInt(size));
        LOGGER.info("get random address node :{}",address);
      }
      return curatorFramework.getData().forPath(serviceZKPath+"/"+address).toString();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
