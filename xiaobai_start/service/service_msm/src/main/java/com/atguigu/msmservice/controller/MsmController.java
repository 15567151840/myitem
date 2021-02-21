package com.atguigu.msmservice.controller;

import com.aliyuncs.utils.StringUtils;
import com.atguigu.msmservice.service.MsmService;
import com.atguigu.msmservice.utils.RandomUtil;
import com.atguigu.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msm")
public class MsmController {
    @Autowired
    private MsmService msmService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @PostMapping("send/{phone}")
    public R sendMsm(@PathVariable String phone){
        //从readis中获取验证码如果取到直接返回
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)){
            return R.ok().data("code",code);
        }
        //如果redis获取不到进行阿里云发送
        //生成随机数到阿里云进行发送
        code = RandomUtil.getFourBitRandom();
        Map<String,Object> params = new HashMap<>();
        params.put("code",code);
        boolean isSend  = msmService.send(params,phone);
        if(isSend){
            //发送成功把验证码放到redis中
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return R.ok();
        }else{
            return R.error().message("发送短信验证码失败");
        }

    }
}
