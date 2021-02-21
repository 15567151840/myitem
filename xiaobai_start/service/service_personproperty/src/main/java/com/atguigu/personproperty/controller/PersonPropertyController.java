package com.atguigu.personproperty.controller;


import com.atguigu.personproperty.entity.PersonProperty;
import com.atguigu.personproperty.service.PersonPropertyService;
import com.atguigu.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 属性 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-11-30
 */
@RestController
@RequestMapping("/personproperty/person-property")
public class PersonPropertyController {
    @Autowired
    private PersonPropertyService personPropertyService;
    @PostMapping("insrtInfo")
    public R insertInfo(@RequestBody PersonProperty personProperty){
        boolean save = personPropertyService.save(personProperty);
        if(save){
            return R.ok();
        }else{
            return R.error();
        }
    }
    //根据父级id查询对应的信息
    @PostMapping("getPersonInfo/{id}")
    public R getPersonInfo(@PathVariable Integer id){
        PersonProperty personInfo = personPropertyService.getPersonInfoByParentId(id);
        return R.ok().data("personInfo",personInfo);
    }
}
