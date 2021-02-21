package com.atguigu.calendar.controller;


import com.atguigu.calendar.entity.CalenderIndex;
import com.atguigu.calendar.service.CalenderIndexService;
import com.atguigu.util.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 主页备忘 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-12-09
 */
@RestController
@RequestMapping("/calendar/calenderIndex")
@CrossOrigin
public class CalenderIndexController {
    @Autowired
    private CalenderIndexService indexService;
    @PostMapping("saveIndexCalender")
    public R saveIndexCalender(@RequestBody CalenderIndex calenderIndex){
        boolean save = indexService.save(calenderIndex);
        if(save){
            return R.ok();
        }else{
            return R.error();
        }
    }
    @GetMapping("getIndexCalenderInfo")
    public R getIndexCalenderInfo(){
        List<CalenderIndex> list = indexService.list(null);
        return R.ok().data("indexList",list);
    }

    @PostMapping("updateIndexIndo")
    public R updateIndexInfo( @RequestBody CalenderIndex calenderIndex){
         QueryWrapper<CalenderIndex> wrapper = new QueryWrapper<>();
         wrapper.eq("idate",calenderIndex.getIdate());
        boolean b = indexService.update(calenderIndex,wrapper);
        if(b){
            return R.ok();
        }else{
            return R.error();
        }
    }
}

