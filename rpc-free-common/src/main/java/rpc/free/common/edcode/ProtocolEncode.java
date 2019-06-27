package rpc.free.common.edcode;

import com.aliyun.openservices.shade.io.netty.buffer.ByteBuf;
import com.aliyun.openservices.shade.io.netty.channel.ChannelHandlerContext;
import com.aliyun.openservices.shade.io.netty.handler.codec.MessageToByteEncoder;
import rpc.free.common.serializer.KryoSerializeImpl;
import rpc.free.common.serializer.Serialize;

/**
 * @program: rpcfree
 * @description: 编码
 * @author: guodong.zhang
 * todo:
 * @create: 2019-05-16 14:31
 */
public class ProtocolEncode extends MessageToByteEncoder {
  private Serialize serialize = new KryoSerializeImpl();
  @Override
  protected void encode(ChannelHandlerContext channelHandlerContext, Object msg, ByteBuf byteBuf) throws Exception {
    byte[] serializedBytes = serialize.serialize(msg);
    int length = serializedBytes.length;
    byteBuf.writeInt(length);
    byteBuf.writeBytes(serializedBytes);
  }
}
