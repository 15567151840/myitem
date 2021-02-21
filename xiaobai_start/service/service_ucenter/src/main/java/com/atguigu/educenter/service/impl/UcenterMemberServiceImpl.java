package com.atguigu.educenter.service.impl;

import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.util.JwtUtils;
import com.atguigu.util.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-11-17
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public String login(UcenterMember member) {
        String mobile = member.getMobile();
        String password = member.getPassword();
        //手机号和密码非空判断
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            System.out.println("登录失败");
        }
        //判断手机号码是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
        //判断查询的对象是否为空
        if(mobileMember == null){
            //没有这个手机号码，查询失败
            System.out.println("没有此手机号!");
        }
        //判断密码
        //因为存到数据库的密码肯定是加密的
        //再把输入的密码和数据库的密码进行比对
        //加密方式MD5
        if(!MD5.encrypt(password).equals(mobileMember.getPassword())){
           System.out.println("密码错误");
        }
        //判断用户是否禁用
        if(mobileMember.getIsDisabled()){
            System.out.println("用户被禁用登陆失败");
        }
        //登录成功生成token字符串
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(),mobileMember.getNickname());
        return jwtToken;
    }
    //注册
    @Override
    public void register(RegisterVo registerVo) {
        String mobile = registerVo.getMobile();
        String code = registerVo.getCode();
        String password = registerVo.getPassword();
        String nickname = registerVo.getNickname();
        //非空判断
        if(StringUtils.isEmpty( mobile) || StringUtils.isEmpty(code)
        || StringUtils.isEmpty(password) || StringUtils.isEmpty(nickname)){
            System.out.println("注册失败");
        }
        //验证码判断
        String registerCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(registerCode)){
            System.out.println("注册失败");
        }
        //判断手机号是否崇重复
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count>0){
            System.out.println("该手机号已经存在");
        }
        //将数据添加到数据库
        UcenterMember member = new UcenterMember();
        member.setNickname(nickname);
        member.setMobile(mobile);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/jJHyeM0EN2jhB70LntI3k8fEKe7W6CwykrKMgDJM4VZqCpcxibVibX397p0vmbKURGkLS4jxjGB0GpZfxCicgt07w/132");
        baseMapper.insert(member);


    }

    //查询某一天的注册人数
    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }
}
