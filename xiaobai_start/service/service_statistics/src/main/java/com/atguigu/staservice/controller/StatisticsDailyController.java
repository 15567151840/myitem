package com.atguigu.staservice.controller;


import com.atguigu.staservice.service.StatisticsDailyService;
import com.atguigu.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-11-18
 */
@RestController
@RequestMapping("/staservice/daily")
public class StatisticsDailyController {
  @Autowired
   private StatisticsDailyService dailyService;


    //统计某一天的注册人数生成统计数据
  @PostMapping("registerCount/{day}")
  public R registerCount(@PathVariable String day){
      dailyService.registerCount(day);
      return R.ok();
  }
  //图表显示部分数据，日期json数组、数量json数组
   @PostMapping("showData/{type}/{begin}/{end}")
   public R showData(@PathVariable String type,@PathVariable String begin ,
                     @PathVariable String end){
      Map<String,Object> map =dailyService.getShowData(type,begin,end);
      return R.ok().data(map);
   }

}

