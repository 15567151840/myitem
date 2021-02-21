package com.atguigu.eduuser.mapper;

import com.atguigu.eduuser.entity.Persons;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-11-30
 */
public interface PersonsMapper extends BaseMapper<Persons> {
  public String  getWantInfo(Integer id);
}
