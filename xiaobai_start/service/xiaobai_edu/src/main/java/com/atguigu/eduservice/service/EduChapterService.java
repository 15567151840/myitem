package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-11-11
 */
public interface EduChapterService extends IService<EduChapter> {

    void deleteChapter(String chapterId);

    void deleteChapterByCourseId(String courseId);

    List<ChapterVo> getChapterVideoByCourseId(String courseId);
}
