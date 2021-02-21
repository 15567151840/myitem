package com.atguigu.personproperty.service;

import com.atguigu.personproperty.entity.PersonProperty;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 属性 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-11-30
 */
public interface PersonPropertyService extends IService<PersonProperty> {

    PersonProperty getPersonInfoByParentId(Integer id);
}
