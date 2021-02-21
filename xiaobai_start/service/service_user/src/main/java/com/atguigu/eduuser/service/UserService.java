package com.atguigu.eduuser.service;

import com.atguigu.eduuser.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2020-11-30
 */
public interface UserService extends IService<User> {

    Map<String, Object> getPersonInfo(Integer id);
}
