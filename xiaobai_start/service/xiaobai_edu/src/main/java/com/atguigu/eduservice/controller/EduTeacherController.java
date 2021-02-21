package com.atguigu.eduservice.controller;


import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.valid.UpdateGroup;
import com.atguigu.util.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.xml.internal.ws.policy.EffectiveAlternativeSelector;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-11-05
 */
@Api(description="讲师管理模块")
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController{
    @Autowired
    private EduTeacherService teacherService;
    @ApiOperation(value="所有讲师列表")
    @GetMapping("findAllTeacher")
    public R findAllteacher(){
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("list",list);
    }
    //逻辑删除讲师功能
    @ApiOperation(value="逻辑删除讲师")
    @DeleteMapping("{id}")
    public R delTeacher(@ApiParam(name="id",value="讲师Id",required = true) @PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        if (flag) {
           return R.ok();
        } else {
           return  R.error();
        }
    }
    
    //分页查询
    @GetMapping("getPageTeacher/{current}/{limit}")
    public R getPageTeacher(@PathVariable long current,
                            @PathVariable long limit){
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
         teacherService.page(pageTeacher,null);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();

        return R.ok().data("total",total).data("records",records);
    }
    //条件查询带分页方法
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(
            @PathVariable long current,
            @PathVariable long limit,
            @RequestBody(required = false) TeacherQuery teacherQuery
    ){
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        //构建条件对象
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper();
        //多条件组合查询
        //mybatis学过动态sql
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if(!StringUtils.isEmpty(name)){
            //构建条件
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            //条件是大于等于
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            //条件是小于等于
            wrapper.le("gmt_modified",end);
        }

        //调用方法实现条件查询分页
        teacherService.page(pageTeacher,wrapper);
        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords();//数据list集合
        return R.ok().data("total",total).data("rows",records);
    }
    //添加讲师的方法
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = teacherService.save(eduTeacher);
        if(save){
            return R.ok();
        }else{
            return R.error();
        }
    }
    //根据讲师id查询讲师信息
    //@RequiresPermissions("product:brand:save")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@Valid @PathVariable("id") String id ,BindingResult result){
        if(result.hasErrors()){
              Map<String,String> map = new HashMap<>();
            //1.获取校验的错误结果
            result.getFieldErrors().forEach(item->{
                String defaultMessage = item.getDefaultMessage();
                map.put("error",defaultMessage);
            });
            System.out.println(map);
            //return R.ok().data(map);
        }else{

        }
        //return R.ok();
         EduTeacher teacher = teacherService.getById(id);
         return R.ok().data("teacher",teacher);
    }
    //讲师修改
    @PostMapping("updateTeacher")
    public R updateTeacher(@Validated(UpdateGroup.class) @RequestBody EduTeacher eduTeacher,BindingResult result){
        boolean b = result.hasErrors();
        if(b){
            Map<String,String> map = new HashMap<>();
            //1.获取校验的错误结果
            result.getFieldErrors().forEach(item->{
                String defaultMessage = item.getDefaultMessage();
                map.put("error",defaultMessage);
            });
            System.out.println(map);
            return R.error().message(map.get("error"));
        }
        boolean update = teacherService.updateById(eduTeacher);
        if (update) {
            return R.ok();
        } else {
            return R.error();
        }

    }
    //查询所有讲师
    @PostMapping("getTeacherList")
    public R getTeacherList(){
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("list",list);
    }
}

