package com.atguigu.eduservice.controller;


import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.subject.OneSubject;
import com.atguigu.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-11-10
 */
@RestController
@RequestMapping("/eduservice/subject")
public class EduSubjectController {
    @Autowired
    private EduSubjectService subjectService;
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }

    @PostMapping("getAllSubject")
    public R getAllSubject(){
       List<OneSubject> list =  subjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }
}

