package com.xp;

import com.xp.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRedisApplicationTests {

    //k-v都是对象
    @Autowired
    RedisTemplate redisTemplate;

    //简化操作字符串的redis模板
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate<Object, User> myRedisTemplate;


    /**
     * String(字符串)，List(列表)，Set(集合)，Hash(散列)，Zset(有序集合)
     *
     * stringRedisTemplate.opsForValue();  //操作字符串
     * stringRedisTemplate.opsForHash();   //操作Hash
     * stringRedisTemplate.opsForList();   //操作List
     * stringRedisTemplate.opsForSet();    //操作Set
     * stringRedisTemplate.opsForZSet();   //操作ZSet
     *
     * */
    @Test
    public void test01(){
        //操作字符串,保存数据
//        stringRedisTemplate.opsForValue().append("k1","hello" );
//        String k1 = stringRedisTemplate.opsForValue().get("k1");
//        System.out.println(k1+"   ======================================");

        //保存对象,但保存的是序列化后的数据
//        User u=new User(1,"jack" ,"123456789" ,"jerry" , 11000, 1);
//        redisTemplate.opsForValue().set("user1",u );

        //保存自定义序列化对象
        User u=new User(1,"jack" ,"123456789" ,"jerry" , 11000, 2);
        myRedisTemplate.opsForValue().set("user1",u );
    }

    @Test
    public void contextLoads() {

    }

}
