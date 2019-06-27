package rpc.free.common.util;

/**
 * @program: rpcfree
 * @description: 注册工具类
 * @author: guodong.zhang
 * todo:
 * @create: 2019-05-16 14:54
 */
public class RegistryUtil {
  public static String getServicePath(String serviceName){
    return Constant.ZK_REGISTER_PATH.getDesc() + "/services/" + serviceName;
  }

  public static String getServiceInstancePath(String serviceName, String ip, int port){
    String servicePath = RegistryUtil.getServicePath(serviceName);
    return servicePath +"/"+ip+":"+port;
  }
}
