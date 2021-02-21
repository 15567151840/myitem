package com.atguigu.eduservice.controller;

import com.atguigu.util.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("eduservice/user")
@CrossOrigin//解决跨域
public class EduLoginController {
    @PostMapping("login")
    public R login(){
        return R.ok().data("token","token");
    }
    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://dss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2268597717,1247359014&fm=26&gp=0.jpg");
    }
}
