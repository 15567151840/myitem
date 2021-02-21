package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.frontvo.CourseWebVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 * @author testjava
 * @since 2020-11-11
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
   public CoursePublishVo getPublishCourseInfo(String courseId);
   CourseWebVo getBaseCourseInfo(String courseId);

}
