package stack;

import java.util.Stack;

/**
 * @program: simple-rpc
 * @description: 汉诺塔问题学习 todo:
 * @author: guodong.zhang
 * todo:
 * @create: 2019-07-19 11:42
 */
public class HannuoStack {
  Stack<Integer> left,mid,right;
  int n = 5;
  public void init(){
    for(int i = 5; i > 0; i++){
      left.push(i);
    }
  }

  public static void main(){
    new HannuoStack().moveProcess();
  }

  public void moveProcess(){
    while(right.size()<n){
      if(!left.isEmpty()){
        int temp1 = left.pop();
        System.out.println("move left -> mid; item:"+ temp1);
        if(mid.isEmpty() || temp1<mid.peek()){
          mid.push(temp1);
        } else {

        }
      }
      if(!mid.isEmpty()){
        int temp2 = mid.pop();
        if(right.isEmpty() || right.peek() > temp2){
          System.out.println("move mid -> left; item:"+ temp2);
          right.push(temp2);
        }else{
          int temp3 =  right.pop();
          System.out.println("move right -> mid; item:"+ temp3);
          mid.push(temp3);
          int temp4 = mid.pop();
          left.push(temp4);
          System.out.println("move mid -> left; item:"+ temp4);

          right.push(temp2);
          System.out.println("move mid -> right; item:"+ temp2);
        }
      }
    }

  }
}
