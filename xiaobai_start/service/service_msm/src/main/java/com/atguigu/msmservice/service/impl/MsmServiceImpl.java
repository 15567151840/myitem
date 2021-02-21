package com.atguigu.msmservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.utils.StringUtils;
import com.atguigu.msmservice.service.MsmService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean send(Map<String, Object> params, String phone) {
        if(StringUtils.isEmpty(phone)) return false;
        DefaultProfile profile =
                DefaultProfile.getProfile("default","LTAI4GJvsM7kwjgqdaz6EYoM", "Z5E52fvJE4X1i15pCslwayUhtctLNo");
        IAcsClient client = new DefaultAcsClient(profile);

        //设置固定的参数
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        //设置发送的相关参数
        request.putQueryParameter("PhoneNumbers",phone);//手机号
        request.putQueryParameter("SignName","我的鼓励在线学习网站");//申请的阿里云的签名名称
        request.putQueryParameter("TemplateCode","SMS_205138238");//申请的阿里云的模板code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(params));//验证数据转换成json传递
        try{
            //最终发送
            CommonResponse response = client.getCommonResponse(request);
            boolean success = response.getHttpResponse().isSuccess();
            return success;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
