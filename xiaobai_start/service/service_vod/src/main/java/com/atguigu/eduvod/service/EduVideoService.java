package com.atguigu.eduvod.service;

import com.atguigu.eduvod.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-12-10
 */
public interface EduVideoService extends IService<EduVideo> {

    String upVideo(MultipartFile file);

    String getVideoUrl(String id);
}
