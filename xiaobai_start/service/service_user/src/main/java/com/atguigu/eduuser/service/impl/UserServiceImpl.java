package com.atguigu.eduuser.service.impl;

import com.atguigu.eduuser.client.PersonClient;
import com.atguigu.eduuser.entity.User;
import com.atguigu.eduuser.mapper.UserMapper;
import com.atguigu.eduuser.service.UserService;
import com.atguigu.util.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-11-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private PersonClient personClient;
    @Override
    public Map<String, Object> getPersonInfo(Integer id) {

        Map<String,Object> map = new HashMap<>();
        R personInfo = personClient.getPersonInfo(id);
        Object personInfo1 = personInfo.getData().get("personInfo");
        map.put("personInfo",personInfo1);
        User user = baseMapper.selectById(id);
        map.put("user",user);
        return map;
    }
}
