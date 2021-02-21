package com.atguigu.study.service;

import com.atguigu.study.entity.Statistics;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 课程统计 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-12-04
 */
public interface StatisticsService extends IService<Statistics> {

    Map<String, Object> getEchartInfo(String todayDate);

    Map<String, Object> getPieData(String todayDate);

    Map<String, Object> getBrokenLineData();

    Map<String, Object> getStudy();

    boolean removeInfo(String id);

    Map<String, Object> getPageStatics(long current, long limit);
}
