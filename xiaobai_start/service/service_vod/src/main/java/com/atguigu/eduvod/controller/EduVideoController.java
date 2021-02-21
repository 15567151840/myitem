package com.atguigu.eduvod.controller;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.eduvod.Utils.ConstantVodUtils;
import com.atguigu.eduvod.Utils.InitVodClient;
import com.atguigu.eduvod.entity.EduVideo;
import com.atguigu.eduvod.service.EduVideoService;
import com.atguigu.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-12-10
 */
@RestController
@RequestMapping("/eduvod/eduvideo")
@CrossOrigin
public class EduVideoController {
    @Autowired
    EduVideoService videoService;
    //上传视频到阿里云
    @PostMapping("upVideo")
    public R upVideo(MultipartFile file){
        //返回上传视频的id
       String id =  videoService.upVideo(file);
        return R.ok().data("videoId",id);
    }
    //根据视频id删除视频
    @DeleteMapping("removeVideo/{id}")
    public R removeVideo(@PathVariable String id){
        try{
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID,ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建删除视频的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //向request对象里面设置视频id
            request.setVideoIds(id);
            //使用初始化对象的方法进行删除
            client.getAcsResponse(request);
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            return R.error();
        }
    }
    //根据视频id获取播放凭证
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id){
        try{
            //创建初始化对象
            DefaultAcsClient client =
                    InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID,ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建获取凭证的request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //向request设置视屏id
            request.setVideoId(id);
            //调用方法的到视屏凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth",playAuth);
        }catch(Exception e){
            e.printStackTrace();
            return R.error();
        }
    }
    //添加video信息
    @PostMapping("saveVideoInfo")
    public R saveVideoInfo(@RequestBody EduVideo eduVideo){
        boolean save = videoService.save(eduVideo);
        if(save){
            return R.ok();
        }else{
            return R.error();
        }
    }

    //根据视频id获取视频url
    @PostMapping("getVideoUrl/{vid}")
    public R getVideoUrl(@PathVariable String vid){
        String videoUrl = videoService.getVideoUrl(vid);
        return R.ok().data("videoUrl",videoUrl);
    }

    //查询课程信息
    @GetMapping("getVodInfo")
    public R getVodInfo(){
        List<EduVideo> list = videoService.list(null);
        return R.ok().data("vodInfo",list);
    }
}

