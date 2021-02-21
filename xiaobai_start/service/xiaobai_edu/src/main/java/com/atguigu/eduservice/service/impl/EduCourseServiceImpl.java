package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.frontvo.CourseFrontVo;
import com.atguigu.eduservice.frontvo.CourseWebVo;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.util.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-11-11
 *
 * 1、整合redisson作为分布式锁等功能的框架
 *  *      ① 引入依赖
 *  *          <dependency>
 *  *              <groupId>org.redisson</groupId>
 *  *              <artifactId>redisson</artifactId>
 *  *              <version>3.12.0</version>
 *  *          </dependency>
 *  *      ②配置redisson MyRedissonConfig
 *  *
 *  * 2、整合springcache 简化缓存开发
 *  *      ① 引入依赖
 *  *          1)、 spring-boot-starter-cache
 *  *          2)、spring-boot-starter-data-redis
 *  *      ② 写配置
 *  *          1)、自动配置了
 *  *              CacheAutoConfiguration会导入RedisCacheConfiguration
 *  *              自动配好了缓存管理器
 *  *          2)、配置使用redis缓存
 *  *                  spring.cache.type=redis
 *  *      ③ 测试使用缓存
 *  *              @Cacheable: Triggers cache population. -> 触发将数据保存到缓存的操作
 *  *              @CacheEvict: Triggers cache eviction.  -> 触发将数据从缓存删除的操作
 *  *              @CachePut: Updates the cache without interfering with the method execution. -> 不影响方法执行更新缓存，双写模式
 *                  添加数据后再给他添加一份
 *  *              @Caching: Regroups multiple cache operations to be applied on a method.     -> 组合以上多个操作
 *  *              @CacheConfig: Shares some common cache-related settings at class-level.     -> 在类级别共享缓存相同配置
 *  *         1)、开启缓存功能
 *  *              @EnableCaching
 *  *
 *  *         2)、只需要使用注解就可使用缓存
 *
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;
    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private EduVideoService videoService;
    @Autowired
    private EduCourseDescriptionService descriptionService;
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        String  cid = eduCourse.getId();
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescription.setId(cid);
        courseDescriptionService.save(courseDescription);
        return cid;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,course);
        int result = baseMapper.updateById(course);
        if(result<=0){
            System.out.println("修改失败");
        }
        //修改描述表
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(description);

    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);
        EduCourseDescription description = courseDescriptionService.getById(courseId);

        courseInfoVo.setDescription(description.getDescription());

        return courseInfoVo;
    }
    //根据id查询课程发布信息
    @Override
    public CoursePublishVo getPublishCourseInfo(String id) {
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    @Override
    public void removeCourse(String courseId) {
        //根据课程删除章节
        chapterService.deleteChapterByCourseId(courseId);
        //根据课程删除小节
         videoService.deleteVideoByCourseId(courseId);
        //根据课程删除描述
         descriptionService.removeById(courseId);
        //删除课程本身
        baseMapper.deleteById(courseId);
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //判断条件是否为空，不为空拼接

        if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())){//一级分类
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())){
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())){//关注度
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {//最新
            wrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {//价格
            wrapper.orderByDesc("price");
        }
        baseMapper.selectPage(pageCourse,wrapper);
        List<EduCourse> records = pageCourse.getRecords();
        long current = pageCourse.getCurrent();
        long pages = pageCourse.getPages();
        long size = pageCourse.getSize();
        long total = pageCourse.getTotal();
        boolean hasNext = pageCourse.hasNext();//是否有下一页
        boolean hasPrevious = pageCourse.hasPrevious();//是否有上一页

        Map<String,Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return null;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
       return baseMapper.getBaseCourseInfo(courseId);
    }

    //根据课程id查出课程课程信息、章节信息、以及小节信息
    @Override
    public Map<String, Object> getCourseChapterVideo(String id) {
        Map<String,Object> map = new HashMap<>();
        EduCourse course = baseMapper.selectById(id);
        EduChapter chapter = chapterService.getById(id);
        EduVideo video = videoService.getById(id);
        map.put("course",course);
        map.put("chapter",chapter);
        map.put("video",video);
        return map;
    }

}
