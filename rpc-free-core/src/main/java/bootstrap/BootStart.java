package bootstrap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @program: rpcfree
 * @description: 配置启动类
 * @author: guodong.zhang
 * todo:
 * @create: 2019-06-28 11:12
 */
public class BootStart implements ApplicationContextAware, InitializingBean {
  @Override
  public void afterPropertiesSet() throws Exception {

  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

  }
}
