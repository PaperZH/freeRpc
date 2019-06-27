package handler;

import rpc.free.common.model.RpcResponse;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @program: rpcfree
 * @description:
 * @author: guodong.zhang
 * todo:
 * @create: 2019-05-16 15:52
 */
public class ResponseHolder {
  public static ConcurrentMap<String,BlockingQueue<RpcResponse>> responseMap = new ConcurrentHashMap<>();
}
