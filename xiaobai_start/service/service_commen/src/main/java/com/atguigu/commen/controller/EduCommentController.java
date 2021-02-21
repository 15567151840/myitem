package com.atguigu.commen.controller;


import com.atguigu.commen.entity.EduComment;
import com.atguigu.commen.service.EduCommentService;
import com.atguigu.util.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-12-14
 */
@RestController
@RequestMapping("/commen/educomment")
@CrossOrigin
public class EduCommentController {
    @Autowired
    private EduCommentService commentService;
    @PostMapping("insertCommenInfo")
    public R insertCommenInfo(@RequestBody EduComment eduComment){
        boolean save = commentService.save(eduComment);
        if(save){
            return R.ok();
        }else{
            return R.error();
        }
    }
    @PostMapping("getPageComment/{current}/{limit}")
    public R getPageComment(@PathVariable long current,@PathVariable long limit){
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        Page<EduComment> commentPage = new Page<>(current,limit);
        commentService.page(commentPage, wrapper);
        long total = commentPage.getTotal();
        List<EduComment> records = commentPage.getRecords();

        return R.ok().data("total",total).data("pageComment",records);
    }
    @PostMapping("getCommenInfo")
    public R getCommenInfo(){
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        List<EduComment> list = commentService.list(wrapper);
        return R.ok().data("commenList",list);
    }

}

