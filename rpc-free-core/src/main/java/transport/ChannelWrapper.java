package transport;

import io.netty.channel.Channel;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import pool.ConnectionObjectFactory;


/**
 * @program: rpcfree
 * @description: 管道封装
 * @author: guodong.zhang
 * todo:
 * @create: 2019-05-16 18:19
 */
public class ChannelWrapper {
  private String connStr;
  private String host;
  private int ip;
  private Channel channel;
  private ObjectPool<Channel> channelObjectPool;

  public ChannelWrapper(String host, int port) {
    this.host = host;
    this.ip = port;
    this.connStr = host + ":" + ip;
    channelObjectPool = new GenericObjectPool<Channel>(new ConnectionObjectFactory(host,port));
  }

  public String getConnStr() {
    return connStr;
  }

  public void setConnStr(String connStr) {
    this.connStr = connStr;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public int getIp() {
    return ip;
  }

  public void setIp(int ip) {
    this.ip = ip;
  }

  public Channel getChannel() {
    return channel;
  }

  public void setChannel(Channel channel) {
    this.channel = channel;
  }

  public ObjectPool<Channel> getChannelObjectPool() {
    return channelObjectPool;
  }

  public void setChannelObjectPool(ObjectPool<Channel> channelObjectPool) {
    this.channelObjectPool = channelObjectPool;
  }

  @Override
  public String toString() {
    return "ChannelWrapper{" +
        "connStr='" + connStr + '\'' +
        ", host='" + host + '\'' +
        ", ip=" + ip +
        ", channel=" + channel +
        ", channelObjectPool=" + channelObjectPool +
        '}';
  }
}
