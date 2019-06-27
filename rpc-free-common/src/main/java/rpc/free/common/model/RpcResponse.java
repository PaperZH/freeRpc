package rpc.free.common.model;

/**
 * @program: rpcfree
 * @description: rpc 请求返回对象封装
 * @author: guodong.zhang
 * todo:
 * @create: 2019-05-16 11:27
 */
public class RpcResponse {
  private String requestId; //请求id
  private Exception exception; //异常信息
  private Object result;  //返回结果

  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  public Exception getException() {
    return exception;
  }

  public void setException(Exception exception) {
    this.exception = exception;
  }

  public Object getResult() {
    return result;
  }

  public void setResult(Object result) {
    this.result = result;
  }
}
