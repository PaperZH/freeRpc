package rpc.free.common.edcode;

import com.aliyun.openservices.shade.io.netty.buffer.ByteBuf;
import com.aliyun.openservices.shade.io.netty.channel.ChannelHandlerContext;
import com.aliyun.openservices.shade.io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc.free.common.serializer.KryoSerializeImpl;
import rpc.free.common.serializer.Serialize;

/**
 * @program: rpcfree
 * @description: 编码
 * @author: guodong.zhang
 * todo:
 * @create: 2019-05-16 11:32
 */
public class ProtocolDecoder extends LengthFieldBasedFrameDecoder {
  private static final Logger logger = LoggerFactory.getLogger(ProtocolDecoder.class);
  private Serialize serialize = new KryoSerializeImpl();
  private static final int MSG_PROTOCOL_HEADER_FIELD_LENGTH = 4;
  public ProtocolDecoder(int maxFrameLength) {
    super(maxFrameLength,0, MSG_PROTOCOL_HEADER_FIELD_LENGTH, 0, 4);
  }
  @Override
  protected Object decode(ChannelHandlerContext ctx, ByteBuf btf) throws Exception {
    ByteBuf decode = (ByteBuf) super.decode(ctx, btf);
    if (decode != null) {
      int byteLength = decode.readableBytes();
      // TODO try to avoid data copy
      byte[] byteHolder = new byte[byteLength];
      decode.readBytes(byteHolder);
      return serialize.deserialize(byteHolder);
    }
    logger.debug("Decoder Result is null");
    return null;
  }
}
