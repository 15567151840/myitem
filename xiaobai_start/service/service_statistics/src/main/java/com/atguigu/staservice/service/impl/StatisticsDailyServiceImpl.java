package com.atguigu.staservice.service.impl;

import com.atguigu.staservice.client.UcenterClient;
import com.atguigu.staservice.entity.StatisticsDaily;
import com.atguigu.staservice.mapper.StatisticsDailyMapper;
import com.atguigu.staservice.service.StatisticsDailyService;
import com.atguigu.util.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-11-18
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
    @Autowired
    private UcenterClient client;
    //查询某一天的注册数据
    @Override
    public void registerCount(String day) {
        //添加表之前删除相同日期的数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);
        //远程调用得到某一天的注册人数
        R registerR = client.countRegister(day);
        Integer countRegister = (Integer) registerR.getData().get("countRegister");

        //把获取的数据添加到统计表里
        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setRegisterNum(countRegister);
        statisticsDaily.setDateCalculated(day);

        statisticsDaily.setVideoViewNum(RandomUtils.nextInt(100,200));
        statisticsDaily.setLoginNum(RandomUtils.nextInt(100,200));
        statisticsDaily.setCourseNum(RandomUtils.nextInt(100,200));
        baseMapper.insert(statisticsDaily);

    }

    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        //根据条件查询对应的数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        wrapper.select("date_calculated",type);
        List<StatisticsDaily> staList = baseMapper.selectList(wrapper);
        //因为返回有两部分数据日期和日期对应的数量
        //前端要求数组json结构，对应后端是list集合
        //创建两个list集合，一个日期list一个数量list
        List<String> date_calculatedList = new ArrayList<>();
        List<Integer> numDataList = new ArrayList<>();
        
        //遍历所有的list进行查询
        for (int i = 0; i < staList.size(); i++) {
            StatisticsDaily daily = staList.get(i);
            //分装日期list集合
            date_calculatedList.add(daily.getDateCalculated());
            //封装对应的数据
            switch(type){
                case"login_num":
                    numDataList.add(daily.getLoginNum());
                    break;
                case"register_num":
                    numDataList.add(daily.getRegisterNum());
                    break;
                case"video_view_num":
                    numDataList.add(daily.getVideoViewNum());
                    break;
                case"course_num":
                    numDataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }

        }
        //把封装之后的两个list集合放到map集合里
        Map<String,Object> map = new HashMap<>();
        map.put("date_calculatedList",date_calculatedList);
        map.put("numDataList",numDataList);
        return map;

    }
}
