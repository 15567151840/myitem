package com.atguigu.eduuser.controller.front;

import com.atguigu.eduuser.service.UserService;
import com.atguigu.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("eduuser/PersonFront")
public class PersonFrontInfoController {
    @Autowired
    private UserService userService;
    @GetMapping("personForntInfo/{id}")
    public R personFrontInfo(@PathVariable Integer id){
       Map<String,Object> map = userService.getPersonInfo(id);
        return R.ok().data(map);
    }
}
