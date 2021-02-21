package com.atguigu.eduuser.service;

import com.atguigu.eduuser.entity.Persons;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2020-11-30
 */
public interface PersonsService extends IService<Persons> {

    String getWantInfo(Integer id);
}
