package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-11-11
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService videoService;
    @Override
    public void deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);
        if(count>0){//查询出小节不进行删除
            System.out.println("下面有小节，没法删除");
        }else{//没查出小节进行删除
            baseMapper.deleteById(chapterId);
        }


    }

    @Override
    public void deleteChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //更具courseid查询课程里面的所有章节
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        List<EduChapter> eduChaptersList = baseMapper.selectList(wrapper);
        //根据课程id查询里面的所有小节
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id", courseId);
        List<EduVideo> eduVideoList = videoService.list(videoWrapper);
        //创建list集合用于最终封装数据
        List<ChapterVo> finalList  = new ArrayList<>();
        for (int i = 0; i < eduChaptersList.size(); i++) {
            EduChapter eduChapter =eduChaptersList.get(i);
            ChapterVo chapterVo = new ChapterVo();
            //eduChapter复制到ChapterVo中
            BeanUtils.copyProperties(eduChapter,chapterVo);
            //把chapterVO放到最终集合里
            finalList.add(chapterVo);
            //创建list用于封装小节
            List<VideoVo> videoList = new ArrayList<>();

            //遍历查询小节
            for (int m = 0; m < eduVideoList.size(); m++) {
                EduVideo eduVideo = eduVideoList.get(m);
                //判断小节里面的chapterid和章节中的id是否相同
                if(eduVideo.getChapterId().equals(eduChapter.getId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    videoList.add(videoVo);
                }

            }
            chapterVo.setChildren(videoList);
        }

        return finalList;
    }
}
