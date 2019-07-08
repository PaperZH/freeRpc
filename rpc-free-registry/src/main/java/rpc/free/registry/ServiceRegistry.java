package rpc.free.registry;

/**
 * @program: rpcfree
 * @description: 服务注册中心
 * @author: guodong.zhang
 * todo:
 * @create: 2019-05-20 09:51
 */
public interface ServiceRegistry {


  /**
   * @Author guozhang.zhang01
   * @Description
   * //TODO guodong.zhang
   *
   * @Date 10:01 2019/5/20
   * @Param [serviceName, ServiceAddress]
   * @return void
   **/
  void registry(String serviceName, String ServiceAddress) throws Exception;

  /**
   * @Author guozhang.zhang01
   * @Description 
   * //TODO guodong.zhang
   *
   * @Date 15:12 2019/7/8
   * @Param [serviceName]
   * @return void
   **/
  public void unRegister(String serviceName);
}
