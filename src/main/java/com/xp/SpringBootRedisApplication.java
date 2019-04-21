package com.xp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

/**
 * redis的starter,在容器中默认保存的是RedisCacheManager,
 * 帮我们创建RedisCache缓存组件，该组件通过操作redis来缓存数据。
 *
 * redis缓存查询后的数据默认是缓存的序列化后的数据，
 * 因为springboot是默认使用的redisTemplate来缓存数据的，
 * 而redisTemplate使用的是默认的jdk序列化机制,故无法以json格式缓存序列化后的数据
 * 我们要缓存保存为json格式:
 * 1.需要自定义CacheManager,替换掉默认的RedisCacheManager
 *
 * */
@MapperScan("com.xp.dao")
@SpringBootApplication
@EnableCaching
public class SpringBootRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRedisApplication.class, args);
    }

}
