package handler;

import org.slf4j.LoggerFactory;
import rpc.free.common.model.RpcResponse;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.slf4j.Logger;
/**
 * @program: rpcfree
 * @description:
 * @author: guodong.zhang
 * todo:
 * @create: 2019-05-16 15:52
 */
class ResponseHolder {

  private static Logger logger = LoggerFactory.getLogger(ResponseHolder.class);

  public static ConcurrentMap<String,BlockingQueue<RpcResponse>> responseMap = new ConcurrentHashMap<>();

  public BlockingQueue<RpcResponse> get(String key){
    return responseMap.get(key);
  }

  public static void set(String key, RpcResponse rpcResponse){
    BlockingQueue<RpcResponse> blockingQueue1 = new ArrayBlockingQueue<>(10);
    try {
      blockingQueue1.put(rpcResponse);
      responseMap.put(key,blockingQueue1);
    } catch (InterruptedException e) {
      logger.info("[rpc-free-core-ResponseHolder]:exception",e);
    }
  }
}
