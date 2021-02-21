package com.atguigu.study.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class TableVo {
    private String id;
    private String name;
    private Integer todayDuration;
    private Integer duration;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date  todayDate;
    private String studyDescription;
    private String remark;
}
