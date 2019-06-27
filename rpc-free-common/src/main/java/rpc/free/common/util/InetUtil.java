package rpc.free.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @program: rpcfree
 * @description: int工具类
 * @author: guodong.zhang
 * todo:
 * @create: 2019-05-20 14:35
 */
public class InetUtil {
  private static Logger LOGGER = LoggerFactory.getLogger(InetUtil.class);
  private InetUtil(){}
  public static String getLocalIp(){
    try{
      return InetAddress.getLocalHost().getHostAddress();
    } catch (UnknownHostException e){
      LOGGER.error("unknown host: {}",e);
    }
    return null;
  }
}
