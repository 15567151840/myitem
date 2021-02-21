package com.atguigu.eduuser.controller;


import com.atguigu.eduuser.entity.Persons;
import com.atguigu.eduuser.service.PersonsService;
import com.atguigu.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-11-30
 */
@RestController
@RequestMapping("/eduuser/persons")
public class PersonsController {
    @Autowired
    private PersonsService personsService;
    @PostMapping("getPersons")
    public R getPersons(){
        List<Persons> personList = personsService.list(null);
        return R.ok().data("personList",personList);
    }
    @PostMapping("getWantInfo/{id}")
    public String getWantInfo(@PathVariable Integer id){
        String wantInfo = personsService.getWantInfo(id);
        return wantInfo;
    }

}

