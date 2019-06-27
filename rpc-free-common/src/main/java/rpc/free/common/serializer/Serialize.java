package rpc.free.common.serializer;

/**
 * @program: rpcfree
 * @description: 序列化接口
 * @author: guodong.zhang
 * todo:
 * @create: 2019-05-16 11:39
 */
public interface Serialize {
  public byte[] serialize(Object object);
  public <T> T deserialize(byte[] bytes);
}
