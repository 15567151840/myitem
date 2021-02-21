package com.atguigu.eduuser.controller;


import com.atguigu.eduuser.entity.User;
import com.atguigu.eduuser.service.UserService;
import com.atguigu.util.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-11-30
 */
@RestController
@RequestMapping("/eduuser/user")
public class UserController {
  @Autowired
    UserService userService;
  @PostMapping("getUser/{id}")
    public R getUser(@PathVariable Integer id){
      User user = userService.getById(id);
      return R.ok().data("user",user);
  }
  @PostMapping("getAllUserMap")
  public Map<String,Object> getAllUserMap(){
    List<User> list = userService.list(null);
    Map<String,Object> map = new HashMap<>();
    map.put("userList",list);
    return map;
  }
  @PostMapping("getALlUserList")
  public R getAllUserList(){
    List<User> list = userService.list(null);
    return R.ok().data("list",list);
  }
  @GetMapping("getNameFrUser")
  public List<User> getNameFrUser(){
    QueryWrapper<User> wrapper = new QueryWrapper<>();
    wrapper.select("name");
    List<User> list = userService.list(wrapper);
    return list;
  }
}

