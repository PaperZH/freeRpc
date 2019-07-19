package rpc.free.common.model;


/**
 * @program: rpcfree
 * @description: rpc 自定义request封装
 * @author: guodong.zhang
 * todo:
 * @create: 2019-05-16 11:21
 */
public class RpcRequest {
  private String requestId; //请求ID
  private String interfaceName; //接口名称
  private String serviceVersion;  //版本号
  private String methodName;  //请求方法
  private Class<?> clazz;//请求接口
  private Class<?>[] parameterTypes; //参数类型
  private Object[] parameters;  //参数对象

  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  public String getInterfaceName() {
    return interfaceName;
  }

  public void setInterfaceName(String interfaceName) {
    this.interfaceName = interfaceName;
  }

  public String getServiceVersion() {
    return serviceVersion;
  }

  public void setServiceVersion(String serviceVersion) {
    this.serviceVersion = serviceVersion;
  }

  public String getMethodName() {
    return methodName;
  }

  public void setMethodName(String methodName) {
    this.methodName = methodName;
  }

  public Class<?> getClazz() {
    return clazz;
  }

  public void setClazz(Class<?> clazz) {
    this.clazz = clazz;
  }

  public Class<?>[] getParameterTypes() {
    return parameterTypes;
  }

  public void setParameterTypes(Class<?>[] parameterTypes) {
    this.parameterTypes = parameterTypes;
  }

  public Object[] getParameters() {
    return parameters;
  }

  public void setParameters(Object[] parameters) {
    this.parameters = parameters;
  }
}
