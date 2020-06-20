package rpc.free.registry.zookpeer;

/**
 * @program: rpcfree
 * @description: 发现服务注册接口
 * @author: guodong.zhang
 * todo:
 * @create: 2019-05-20 09:50
 */
public interface ServiceDiscovery {
  /**
   * @Author guozhang.zhang01
   * @Description
   * //TODO guodong.zhang
   *
   * @Date 9:59 2019/5/20
   * @Param [serviceName]
   * @return java.lang.String
   **/
  String discovery(String serviceName) throws Exception;
}
