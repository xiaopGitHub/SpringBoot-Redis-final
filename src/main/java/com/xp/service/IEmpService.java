package com.xp.service;

import com.xp.pojo.User;

/**
 * @Author xp
 * @CreateTime 2019/03/20  23:44
 * @Function ${VAR}
 */
public interface IEmpService {
    User selectByPrimaryKey(Integer id);
}
