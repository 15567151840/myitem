package com.atguigu.educenter.controller;


import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.util.JwtUtils;
import com.atguigu.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-11-17
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {
  @Autowired
  private UcenterMemberService memberService;
  //登录
  @PostMapping("login")
    public R login(@RequestBody UcenterMember member){
      //调用service实现登录
      //使用jwt实现返回token
     String token =  memberService.login(member);
      return R.ok().data("token",token);
  }
  //注册
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){
      memberService.register(registerVo);
      return R.ok();
    }
    //根据token获取用户信息
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember memberInfo = memberService.getById(memberId);
        return R.ok().data("userInfo",memberInfo);
    }

    //查询某一天的注册人数
   @PostMapping("countRegister/{day}")
   public R countRegister(@PathVariable String day){
    Integer count =   memberService.countRegisterDay(day);
    return R.ok().data("countRegister",count);
   }
   @GetMapping("setCookie")
    public R setCookie(HttpServletResponse response,HttpServletRequest request){
       Cookie cookie = new Cookie("set","cookie");
       cookie.setMaxAge(3600);
       response.addCookie(cookie);
       Cookie[] cookies = request.getCookies();
       List<String> list = new ArrayList<>();
       for (int i = 0; i < cookies.length; i++) {
           String value = cookie.getValue();
           list.add(value);
       }
       return R.ok().data("cookies",list);
   }
   @PostMapping("getCookie")
    public R getCookie(HttpServletRequest request){
       Cookie[] cookies = request.getCookies();
       return R.ok().data("cookies",cookies);
   }
}

