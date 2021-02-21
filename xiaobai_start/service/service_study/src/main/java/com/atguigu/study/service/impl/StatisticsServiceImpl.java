package com.atguigu.study.service.impl;

import com.atguigu.study.client.CalenderClient;
import com.atguigu.study.entity.CalenderItem;
import com.atguigu.study.entity.Statistics;
import com.atguigu.study.entity.vo.BrokenLineVo;
import com.atguigu.study.entity.vo.PieVo;
import com.atguigu.study.entity.vo.TableVo;
import com.atguigu.study.mapper.StatisticsMapper;
import com.atguigu.study.service.StatisticsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 课程统计 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-12-04
 */
@Service
public class StatisticsServiceImpl extends ServiceImpl<StatisticsMapper, Statistics> implements StatisticsService {

    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    private CalenderClient calenderClient;
    @Override
    public Map<String, Object> getEchartInfo(String todayDate) {

        QueryWrapper<Statistics> wrapper = new QueryWrapper<>();
        wrapper.eq("today_date",todayDate);
        List<Statistics> list = statisticsService.list(wrapper);
        Map<String,Object> map = new HashMap<>();
        List<String> listEchart = new ArrayList<>();
        List<Integer> count  = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String name = list.get(i).getName();
            listEchart.add(name);
            Integer todayDuration = list.get(i).getTodayDuration();
            count.add(todayDuration);
        }
        map.put("echart",listEchart);
        map.put("count",count);
        return map;
    }
    //获得饼状图数据
    @Override
    public Map<String, Object> getPieData(String todayDate) {
        QueryWrapper<Statistics> wrapper = new QueryWrapper<>();
        wrapper.eq("today_date",todayDate);
        List<Statistics> PieList = baseMapper.selectList(wrapper);
        List<Object> list = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        for (int i = 0; i < PieList.size(); i++) {
            PieVo pie = new PieVo();
            Integer todayDuration = PieList.get(i).getTodayDuration();
            String name = PieList.get(i).getName();
            pie.setValue(todayDuration);
            pie.setName(name);
            list.add(pie);
        }

        map.put("pieData",list);
        return map;
    }
     //获取折线图的方法
    @Override
    public Map<String, Object> getBrokenLineData() {
        SimpleDateFormat tDate = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = tDate.format(new Date());//今天日期
        //获取昨天的日期
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(calendar.DATE,-1);
        String beforeOneDate = tDate.format(calendar.getTime());

        QueryWrapper<Statistics> todayWrapper = new QueryWrapper<>();
        todayWrapper.eq("today_date",todayDate);
        List<Statistics> todayList = baseMapper.selectList(todayWrapper);
        List<Object> bList = new ArrayList<>();
        for (int i = 0; i < todayList.size(); i++) {
            BrokenLineVo brokenLineVo = new BrokenLineVo();
            brokenLineVo.setName(todayList.get(i).getName());
            brokenLineVo.setType("line");
            brokenLineVo.setStack("总量");
            List<Integer> lineList = new ArrayList<>();
            lineList.add(todayList.get(i).getTodayDuration());
             brokenLineVo.setData(lineList);
             bList.add(brokenLineVo);

            QueryWrapper<Statistics> beforeWrapper = new QueryWrapper<>();
            beforeWrapper.eq("today_date",beforeOneDate);
            List<Statistics> beforeList = baseMapper.selectList(beforeWrapper);
              beforeList.forEach(item->{
                  if(item.getName().equals(brokenLineVo.getName())){
                      lineList.add(item.getTodayDuration());
                      brokenLineVo.setData(lineList);
                  }
              });
            }
        Map<String,Object> map = new HashMap<>();
        map.put("beforeDate",bList);
        return map;
    }
    //获取studyTable的数据
    @Override
    public Map<String, Object> getStudy() {
        QueryWrapper<Statistics> wrapper = new QueryWrapper<>();
        wrapper.select().orderByDesc("gmt_create");
        List<Statistics> statistics = baseMapper.selectList(wrapper);
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < statistics.size(); i++) {
            TableVo tableVo = new TableVo();
            tableVo.setName(statistics.get(i).getName());
            tableVo.setTodayDuration(statistics.get(i).getTodayDuration());
            tableVo.setDuration(statistics.get(i).getDuration());
            tableVo.setId(statistics.get(i).getId());
            List<CalenderItem> calenderInfo = calenderClient.getCalenderInfo();
            for (int m = 0; m < calenderInfo.size(); m++) {
                if(calenderInfo.get(m).getParentId().equals(statistics.get(i).getId())){
                    tableVo.setRemark(calenderInfo.get(m).getRemark());
                    tableVo.setStudyDescription(calenderInfo.get(m).getStudyDescription());
                    tableVo.setTodayDate(calenderInfo.get(m).getParentDate());
                };
            }
            list.add(tableVo);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("table",list);
        return map;
    }

    @Override
    public boolean removeInfo(String id) {
//        QueryWrapper<Statistics> wrapper = new QueryWrapper<>();
//        wrapper.eq("id",id);
//        int delete = baseMapper.delete(wrapper);
        int i = baseMapper.deleteById(id);
        Boolean aBoolean = calenderClient.delCalender(id);
        if(i>1 || aBoolean){
            return true;
        }else{
            return false;
        }
    }

    //获取分页statics表数据
   @Override
    public Map<String, Object> getPageStatics(long current, long limit) {
       Page<Statistics> statisticsPage = new Page<>(current,limit);
       QueryWrapper wrapper = new QueryWrapper();
       //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
       //String nowDate = df.format(new Date());// new Date()为获取当前系统时间
       wrapper.orderByDesc("today_date");
        statisticsService.page(statisticsPage, wrapper);
       List<Statistics> records = statisticsPage.getRecords();
       long total = statisticsPage.getTotal();
       //IPage<Statistics> statisticsIPage = baseMapper.selectPage(page, null);
       //long total = statisticsIPage.getTotal();
       List<Object> list = new ArrayList<>();
       //List<Statistics> records = statisticsIPage.getRecords();
       for (int i=0; i<records.size(); i++) {
           TableVo tabVo = new TableVo();
           tabVo.setName(records.get(i).getName());
           tabVo.setTodayDuration(records.get(i).getTodayDuration());
           tabVo.setDuration(records.get(i).getDuration());
           tabVo.setId(records.get(i).getId());
           tabVo.setTodayDate(records.get(i).getTodayDate());
           List<CalenderItem> calenderInfo = calenderClient.getCalenderInfo();
           for (int m = 0; m < calenderInfo.size(); m++) {
               if(calenderInfo.get(m).getParentId().equals(records.get(i).getId())){
                   tabVo.setRemark(calenderInfo.get(m).getRemark());
                   tabVo.setStudyDescription(calenderInfo.get(m).getStudyDescription());
               };
           }
           list.add(tabVo);
       }
       Map<String,Object> map = new HashMap<>();
       map.put("pageTable",list);
       map.put("total",total);
       return map;
    }
}
