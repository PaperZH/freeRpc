package rpc.free.server;

import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.slf4j.Logger;

import java.util.Map;

/**
 * @program: rpcfree
 * @description: rpc服务端
 * @author: guodong.zhang
 * todo:
 * @create: 2019-05-21 16:58
 */
public class RpcServer implements ApplicationContextAware,InitializingBean {
  private static final Logger LOGGER = LoggerFactory.getLogger(RpcServer.class);
  private Map<String,Object> handlMap;
  public RpcServer(Map<String,Object> handlMap){
    this.handlMap = handlMap;
  }
  @Override
  public void afterPropertiesSet() throws Exception {

  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

  }
}
