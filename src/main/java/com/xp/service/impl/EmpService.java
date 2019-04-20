package com.xp.service.impl;

import com.xp.dao.UserMapper;
import com.xp.pojo.User;
import com.xp.service.IEmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @Author xp
 * @CreateTime 2019/03/20  23:45
 * @Function ${VAR}
 */
@Service
public class EmpService implements IEmpService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    CacheManager JsonCacheManager;

    @Override
    @Cacheable(value = "user")  //在缓存区域开辟了一块名称为user的缓存空间
    public User selectByPrimaryKey(Integer id) {
        System.out.println("第二次进入此方法,没有查询缓存............");

        //通过缓存管理器进行api调用操作缓存,获取的为json格式数据
        Cache cache = JsonCacheManager.getCache("user");
        //添加缓存
        cache.put("user:1", new User(20,"lucas" ,"4567893" , "aaa", 10000, 3));
        return userMapper.selectByPrimaryKey(id);
    }
}
