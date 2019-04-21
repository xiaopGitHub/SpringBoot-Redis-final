package com.xp.service.impl;

import com.xp.dao.UserMapper;
import com.xp.pojo.User;
import com.xp.service.IEmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

/**
 * @Author xp
 * @CreateTime 2019/03/20  23:45
 * @Function @EnableCaching +  @Cacheable(value = {"user","temp"})实现boot缓存功能
 *           springboot默认使用ConcurrentMapCacheManager缓存管理器,由他创建ConcurrentCache,将缓存数据
 *           保存在一个ConcurrentMap中,实际开发中多使用缓存中间件,例如 redis,ehcache
 */
@CacheConfig(cacheNames = "user") //@CacheConfig抽取缓存的公共配置,缓存名统一为user
@Service
public class EmpService implements IEmpService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    CacheManager JsonCacheManager;

    /**
     * 1. @Cacheable(value = "user",key = "#id")
     *
     * 将方法的运行结果缓存，当在获取相同的结果时,直接查询缓存
     * 属性：
     * value：指定缓存名称（每一个缓存都有自己的名字）
     * key：缓存数据时,使用的key。默认使用方法参数的值
     * 在缓存区域开辟了一块名称为user的缓存空间,缓存数据的key为输入的参数id值
     * key = "#root.args[0]
     * key = "#root.args[0]
     * @Cacheable(value = {"user","temp"}) 将查询结果存在多个缓存中
     *
     * */
    @Override
    @Cacheable(value = "user",key = "#id")
    public User selectByPrimaryKey(Integer id) {
        System.out.println("第二次进入此方法,没有查询缓存............");

        //通过缓存管理器进行api调用操作缓存,获取的为json格式数据
        Cache cache = JsonCacheManager.getCache("user");
        //添加缓存
        cache.put("user:1", new User(20,"lucas" ,"4567893" , "aaa", 10000, 3));
        return userMapper.selectByPrimaryKey(id);
    }

    /**
     * 2. @CachePut(value = "user" , key = "#result.id")
     *
     * 修改数据库后,同步更新 key 指定缓存,方法运行之后调用
     *
     * */
    @CachePut(/*value = "user" ,*/ key = "#result.id")
    public User updateByPrimaryKey(User user){
        userMapper.updateByPrimaryKey(user);
        return user;
    }

    /**
     * 3. @CacheEvict(value = "user" , key = "#id")
     *
     * 删除数据后,同步删除 key 指定缓存
     * allEntries = true 表示删除该user缓存中所有的数据
     * beforeInvocation = true :表示缓存清除在方法执行之后执行,默认在方法执行之前删除,
     *                          无论方法是否出现异常,缓存都将被清除。
     *                          之后执行避免了方法执行过程中出错时误删缓存的可能
     *
     * */
    @CacheEvict(/*value = "user" ,*/ key = "#id",allEntries = true,beforeInvocation = true)
    public void deleteCache(Integer id){
        userMapper.deleteByPrimaryKey(id);
        System.out.println("删除缓存........");
    }


    /**
     * 4. @Caching相当于 Cacheable , CachePut , CacheEvict的组合注解
     *    可绑定多个缓存规则
     */

    @Caching(
            cacheable = {
                    @Cacheable(/*value = "user",*/key = "#userName")
            },
            put = {
                    //当查询出数据后用userName作为key缓存数据,
                    //加了put组成组合注解后,此时按照id查询数据时,直接查缓存
                    @CachePut(/*value = "user",*/key = "#result.id"),
                    @CachePut(/*value = "user",*/key = "#result.realname")
            }
    )
    public User selectByPrimaryName(String userName){
        return userMapper.selectByPrimaryName(userName);
    }

}
