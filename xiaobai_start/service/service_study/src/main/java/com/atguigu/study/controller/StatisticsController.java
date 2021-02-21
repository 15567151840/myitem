package com.atguigu.study.controller;


import com.atguigu.study.entity.Statistics;
import com.atguigu.study.entity.vo.BrokenLineVo;
import com.atguigu.study.service.StatisticsService;
import com.atguigu.util.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程统计 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-12-04
 */
@RestController
@RequestMapping("/study/statistics")
@CrossOrigin
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;
   @PostMapping("insertCourse")
    public R insertCourse(@RequestBody Statistics statistics){
       boolean saveOk = statisticsService.save(statistics);
       if(saveOk){
           return R.ok();
       }else{
           return R.error();
       }
   }
   @GetMapping("getTodayInfo/{todayDate}")
   public R getTodayInfo(@PathVariable String todayDate  ){
     Map<String,Object> map  =  statisticsService.getEchartInfo(todayDate);
     return R.ok().data(map);
   }
   //获得饼状图数据
   @GetMapping("getPieData/{todayDate}")
    public R getPieData(@PathVariable String todayDate){
       Map<String,Object> map = statisticsService.getPieData(todayDate);
       return R.ok().data(map);
   }
   //折线图数据、
    @GetMapping("getBrokenLineData")
     public R getBrokenLineData(){
       Map<String,Object> map = statisticsService.getBrokenLineData();
       return R.ok().data(map);
    }
    //获取studystatics表的数据
    @GetMapping("getStudy")
    public Map<String,Object> getStudy(){
        Map<String,Object>  map = statisticsService.getStudy();
        return map;
    }
    //根据id删除该条数据
    @DeleteMapping("{id}")
    public R delCalender(@PathVariable String id){
        boolean remove = statisticsService.removeInfo(id);
        if(remove){
            return R.ok();
        }else{
            return R.error();
        }
    }
    //分页获取分页studyStatice
    @GetMapping("getPageStatics/{current}/{limit}")
    public R getPageStatics(@PathVariable long current,@PathVariable long limit){
       Map<String,Object> map = statisticsService.getPageStatics(current,limit);
//        Page<Statistics> page = new Page<>(current,limit);
//        statisticsService.page(page,null);
//        List<Statistics> records = page.getRecords();
        return R.ok().data(map);
    }
    //查询最新的一条表数据
    @GetMapping("getOneInfo")
    public R getOneInfo(){
       QueryWrapper<Statistics> wrapper = new QueryWrapper<>();
       wrapper.orderByDesc("gmt_create");
        Statistics one = statisticsService.getOne(wrapper);
        return R.ok().data("oneInfo",one);
    }
}

