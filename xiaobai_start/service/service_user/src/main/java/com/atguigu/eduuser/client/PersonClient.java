package com.atguigu.eduuser.client;

import com.atguigu.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient("service-personproperty")
@Component
public interface PersonClient {
    @PostMapping("/personproperty/person-property/getPersonInfo/{id}")
    public R getPersonInfo(@PathVariable("id") Integer id);

}
