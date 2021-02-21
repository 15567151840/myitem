package com.atguigu.eduuser.service.impl;

import com.atguigu.eduuser.entity.Persons;
import com.atguigu.eduuser.mapper.PersonsMapper;
import com.atguigu.eduuser.service.PersonsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-11-30
 */
@Service
public class PersonsServiceImpl extends ServiceImpl<PersonsMapper, Persons> implements PersonsService {

    @Override
    public String getWantInfo(Integer id) {
        String wantInfo = baseMapper.getWantInfo(id);
        return wantInfo;
    }
}
