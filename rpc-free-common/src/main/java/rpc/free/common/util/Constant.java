package rpc.free.common.util;

public enum Constant {
  ZK_SESSION_TIMEOUT(5000,""),
  ZK_CONNECTION_TIMEOUT(5000,""),
  ZK_REGISTER_PATH(101,"/freerpc");

  private Integer code;

  private  String desc;

  Constant(Integer code, String desc){
    this.code = code;
    this.desc = desc;
  }

  public Integer getCode(){
    return this.code;
  }

  public String getDesc(){
    return this.desc;
  }
}
