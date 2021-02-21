package com.atguigu.study.entity.vo;

import lombok.Data;

import java.util.List;

@Data
public class BrokenLineVo {
    private String name;
    private String type;
    private String stack;
    private List<Integer> data;
}
