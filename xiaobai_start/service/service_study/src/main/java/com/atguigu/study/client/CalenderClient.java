package com.atguigu.study.client;

import com.atguigu.study.entity.CalenderItem;
import com.atguigu.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Component
@FeignClient("service-calendarday")
public interface CalenderClient {
    @GetMapping("/calendar/calender/getCalenerInfo")
    public List<CalenderItem> getCalenderInfo();

    @DeleteMapping("/calendar/calender/{parentId}")
    public Boolean delCalender(@PathVariable String parentId);
}
