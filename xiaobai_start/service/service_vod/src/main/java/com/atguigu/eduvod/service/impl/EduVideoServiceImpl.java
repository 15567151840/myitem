package com.atguigu.eduvod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.*;
import com.atguigu.eduvod.Utils.ConstantVodUtils;
import com.atguigu.eduvod.Utils.InitVodClient;
import com.atguigu.eduvod.entity.EduVideo;
import com.atguigu.eduvod.mapper.EduVideoMapper;
import com.atguigu.eduvod.service.EduVideoService;
import com.atguigu.util.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-12-10
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Override
    public String upVideo(MultipartFile file) {
        try{
            //fileName上传文件的原始名称
            String fileName = file.getOriginalFilename();

            //title上传之后显示的名称
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            //inputStream上传文件输入流
            InputStream inputStream = file.getInputStream();

            UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
            String videoId =null;
            if (response.isSuccess()) {
                videoId =  response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }
            return videoId;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getVideoUrl(String id) {
        try{
            //创建初始化对象
            DefaultAcsClient client =
                    InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID,ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建获取凭证的request和response对象
            GetPlayInfoRequest request = new GetPlayInfoRequest();
            request.setVideoId(id);
            GetPlayInfoResponse response = client.getAcsResponse(request);
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            String videoUrl = null;
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                videoUrl = playInfo.getPlayURL();
            }
            return videoUrl;
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("获取播放地址失败");
        }
        return null;
    }
}
