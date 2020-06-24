package bootstrap;

import handler.ClientChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ProjectName: freeRpc
 * @Package: bootstrap
 * @ClassName: RpcClient
 * @Author: zhangguodong12
 * @Description: client启动类
 * @Date: 2020/6/23 19:52
 * @Version: 1.0
 */
public class ClientBootStart {

    private static Logger LOGGER = LoggerFactory.getLogger(ClientBootStart.class);

    public static void connectToServer(String ip,int port){
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ClientChannelInitializer());
        try{
            final ChannelFuture channelFuture = bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS,3000)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .connect(ip,port).sync();
            final Channel channel = channelFuture.channel();
            addChannelListener(channelFuture,channel,ip+":"+port);
        } catch (InterruptedException e) {
            LOGGER.error("[rpc-free-core-ConnectionObjectFactory]:Interrupted",e);
            Thread.currentThread().interrupt();
        }
    }

    private static void addChannelListener(ChannelFuture future, Channel channel,String zkConn){
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if(future.isSuccess()){
                    LOGGER.info("[rpc-free-core-ConnectionObjectFactory]:Connect success {}" ,future);
                }
            }
        });

        channel.closeFuture().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                LOGGER.info("[rpc-free-core-ConnectionObjectFactory]:Channel close {}", zkConn);
            }
        });
    }
}
