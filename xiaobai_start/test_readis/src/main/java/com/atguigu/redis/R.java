//package com.atguigu.redis;
//
//import java.util.HashMap;
//import java.util.Map;
//
////@Data
//public class R {
//    private Boolean success;
//    private Integer code;
//    private String message;
//    private Map<String,Object> data = new HashMap<>();
//
//
//    //private  R(){};
//   //成功的静态方法
//    public R success(){
//        R r = new R();
//        r.setSuccess(true);
//        r.setCode(20000);
//        r.setMessage("成功");
//        return r;
//    }
//    //失败的静态方法
//    public  R error(){
//        R r = new R();
//        r.setSuccess(false);
//        r.setCode(20001);
//        r.setMessage("失败");
//        return r;
//    }
//    public static void main(String [] args){
//        R r = new R();
//        r.error();
//
//    }
//}
