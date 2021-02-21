package com.atguigu.calendar.controller;


import com.atguigu.calendar.entity.CalenderItem;
import com.atguigu.calendar.service.CalenderItemService;
import com.atguigu.util.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 学习总结备注 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-12-07
 */
@RestController
@RequestMapping("/calendar/calender")
@CrossOrigin
public class CalenderItemController {
    @Autowired
    private CalenderItemService calenderItemService;
    @PostMapping("saveCalenderInfo")
    public R saveCalenderInfo(@RequestBody CalenderItem calenderItem){
        boolean save = calenderItemService.save(calenderItem);
        if(save){
            return R.ok();
        }else{
            return R.error();
        }
    }
    @GetMapping("getCalenerInfo")
    public List<CalenderItem> getCalenderInfo(){
        List<CalenderItem> list = calenderItemService.list(null);
        return list;
    }
    @DeleteMapping("{parentId}")
    public boolean delCalender(@PathVariable String parentId){
        QueryWrapper<CalenderItem> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",parentId);
        boolean remove = calenderItemService.remove(wrapper);
        return remove;
    }

}

