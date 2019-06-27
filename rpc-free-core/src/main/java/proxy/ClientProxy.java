package proxy;

import transport.Client;

/**
 * @program: rpcfree
 * @description: 代理产生对象
 * @author: guodong.zhang
 * todo:
 * @create: 2019-05-16 16:41
 */
public interface ClientProxy {
  public <T> T proxyInterface(Client client,final Class<T> serviceInterface);
}
