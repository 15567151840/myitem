package com.atguigu.util;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

//统一返回结果的类
@Data
public class R {

    @ApiModelProperty(value = "是否成功")
    private Boolean success;
    @ApiModelProperty(value = "返回码")
    private Integer code;
    @ApiModelProperty(value = "返回消息")
    private String message;
    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    //把构造方法私有化
    private R(){}
    //成功的静态方法
    public static com.atguigu.util.R ok(){
        com.atguigu.util.R r = new com.atguigu.util.R();
        r.setSuccess(true);
        r.setCode(com.atguigu.util.ResultCode.SUCCESS);
        r.setMessage("成功");
        return r;
    }
    //失败的静态方法
    public static com.atguigu.util.R error(){
        com.atguigu.util.R r = new com.atguigu.util.R();
        r.setSuccess(false);
        r.setCode(com.atguigu.util.ResultCode.ERROR);
        r.setMessage("失败");
        return r;
    }
    //链式编程
    public com.atguigu.util.R success(Boolean success){
        this.setSuccess(success);
        return this;
    }
    public com.atguigu.util.R message(String message){
        this.setMessage(message);
        return this;
    }

    public com.atguigu.util.R code(Integer code){
        this.setCode(code);
        return this;
    }

    public com.atguigu.util.R data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public com.atguigu.util.R data(Map<String, Object> map){
        this.setData(map);
        return this;
    }

}
