package rpc.free.common.serializer;


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @program: rpcfree
 * @description: 序列化实现类
 * @author: guodong.zhang
 * todo:
 * @create: 2019-05-16 11:41
 */
public class KryoSerializeImpl implements Serialize {
  @Override
  public byte[] serialize(Object object) {
    Kryo kryo = new Kryo();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    Output output = new Output(byteArrayOutputStream);
    kryo.writeClassAndObject(output,object);
    output.close();
    return byteArrayOutputStream.toByteArray();
  }

  @Override
  public <T> T deserialize(byte[] bytes) {
    Kryo kryo = new Kryo();
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
    Input input = new Input(byteArrayInputStream);
    input.close();
    return (T)kryo.readClassAndObject(input);
  }
}
