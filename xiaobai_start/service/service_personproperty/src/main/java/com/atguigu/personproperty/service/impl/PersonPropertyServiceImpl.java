package com.atguigu.personproperty.service.impl;

import com.atguigu.personproperty.entity.PersonProperty;
import com.atguigu.personproperty.mapper.PersonPropertyMapper;
import com.atguigu.personproperty.service.PersonPropertyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 属性 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-11-30
 */
@Service
public class PersonPropertyServiceImpl extends ServiceImpl<PersonPropertyMapper, PersonProperty> implements PersonPropertyService {

    @Override
    public PersonProperty getPersonInfoByParentId(Integer id) {
        QueryWrapper<PersonProperty> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        PersonProperty personProperty = baseMapper.selectOne(wrapper);
        return personProperty;
    }
}
