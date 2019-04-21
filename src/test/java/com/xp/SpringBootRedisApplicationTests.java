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

    //简化操作字符串的redis模板
    @Autowired
    StringRedisTemplate stringRedisTemplate;//简化操作字符串,操作的k-v都是字符串的

    /* @Autowired
    RedisTemplate redisTemplate;//操作的k-v都是对象*/

   //注入我们自定义json序列化其的redisTemplate
    @Autowired
    RedisTemplate<Object,User> redisTemplate;


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
/*        stringRedisTemplate.opsForValue().append("k1","hello" );
        String k1 = stringRedisTemplate.opsForValue().get("k1");
        System.out.println(k1+"   ======================================");*/

        //保存对象,但保存的是序列化后的数据
/*        User u=new User(1,"jack" ,"123456789" ,"jerry" , 11000, 1);
        redisTemplate.opsForValue().set("user1",u );*/

        //保存自定义序列化对象
        //方法1:自己将对象转换为json
        //方法2:RedisTemplate默认序列化器为JdkSerializationRedisSerializer,
        // 无法将对象序列化后保存为json格式,需要自定义配置json的序列化器
        User u=new User(1,"jack" ,"123456789" ,"jerry" , 11000, 2);
        redisTemplate.opsForValue().set("user1",u );
    }

}
