package com.atguigu.eduservice.entity.vo;

import lombok.Data;
//该类是为了做条件查询专门封装的一个类，作为条件查询的对象
@Data
public class TeacherQuery {
    private String name;
    private Integer level;
    private String begin;
    private String end;
}
