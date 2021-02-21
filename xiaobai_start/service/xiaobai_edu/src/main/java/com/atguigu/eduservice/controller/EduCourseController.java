package com.atguigu.eduservice.controller;


import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.util.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-11-11
 */
@RestController
@RequestMapping("/eduservice/course")
public class EduCourseController implements Serializable {
    @Autowired
    private EduCourseService courseService;

    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }
    //修改课程信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
       courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }
    //根据课程id查询课程的基本信息
    //@Cacheable(value = "course")
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
       CourseInfoVo courseInfoVo =courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    //根据课程id查询课程发布的基本信息
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id){

        CoursePublishVo coursePublishVo = courseService.getPublishCourseInfo(id);
        return R.ok().data("coursePublishVo",coursePublishVo);
    }
    //课程最终发布
    //修改课程状态
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        courseService.updateById(eduCourse);
        return R.ok();
    }
    //查询课程的基本列表
    //查询课程的基本信息,就用这个方法就行
    @GetMapping
    public R getCourseList(){
        List<EduCourse> list = courseService.list(null);
        return R.ok().data("list",list);
    }
    //删除课程
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId){
        courseService.removeCourse(courseId);
        return R.ok();
    }
    //根据课程id只查询课程一张表的信息
    @Cacheable(value = {"course"},key = "#root.method.name")
    @GetMapping("getCourse/{id}")
    public EduCourse getCourse(@PathVariable String id){
        EduCourse course = courseService.getById(id);
        return course;
    }
    //自测方法
    //根据课程id查出课程信息，以及章节信息、小节信息
    @GetMapping("getCourChapterVideo/{id}")
    public R getCourseChapterVideo(@PathVariable("id") String id){
       Map<String,Object> map =  courseService.getCourseChapterVideo(id);
        return R.ok().data(map);
    }
    //自测方法
    //分页查询课程
    @GetMapping("getPageCourse/{page}/{limit}")
    public R getPageCourse(@PathVariable long page,@PathVariable long limit){
        Page<EduCourse> pageCourse = new Page<>(page,limit);
        courseService.page(pageCourse,null);
        Long total = pageCourse.getTotal();
        List<EduCourse> records = pageCourse.getRecords();
        return R.ok().data("total",total).data("records",records);
    }
    //自测方法
    //课程修改
    @PostMapping("updateCourse")
    public R updateCourse(@RequestBody EduCourse eduCourse){
        boolean b = courseService.updateById(eduCourse);
        if(b){
            return R.ok();
        }else{
            return R.error();
        }
    }
}

