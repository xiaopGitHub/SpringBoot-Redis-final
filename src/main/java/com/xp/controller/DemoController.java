package com.xp.controller;

import com.xp.pojo.User;
import com.xp.service.IEmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author xp
 * @CreateTime 2019/02/13  13:33
 * @Function ${VAR}
 */

@RestController
public class DemoController {

    @Autowired
    IEmpService iEmpService;

    @GetMapping("/user/{id}")
    public User getEmployee(@PathVariable("id") Integer id){
        User user=iEmpService.selectByPrimaryKey(id);
        return user;
    }
}
