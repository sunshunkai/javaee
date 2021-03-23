package com.ssk.web;

import com.ssk.cache.jetcache.CacheDao;
import com.ssk.cache.jetcache.CacheService;
import com.ssk.entity.User;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @author ssk
 * @date 2021/3/22
 */
@RestController
@RequestMapping("redis")
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CacheDao cacheDao;

    @Autowired
    private CacheService cacheService;

    @RequestMapping
    public String get(){
        redisTemplate.opsForValue().set("ssk","sunshunkai");
        Object ssk = redisTemplate.opsForValue().get("ssk");
        return ssk.toString();
    }

    @RequestMapping("jetcache/{id}")
    public User findUserById(@PathVariable Long id){
//        return cacheDao.findById(id);
        return cacheService.findId(id);
    }

    @RequestMapping("keys")
    public Set<String> keys(){
//        return cacheDao.findById(id);
//        redisTemplate.opsForValue().set("user:"+id,new User());
        Set<String> o = redisTemplate.keys("create:cache:*");
        return  o;
    }

    @RequestMapping("redis/{id}")
    public User findUserByIdV2(@PathVariable Long id){
//        return cacheDao.findById(id);
//        redisTemplate.opsForValue().set("user:"+id,new User());
        Object o = redisTemplate.opsForValue().get("user:" + id);
        return (User) o;
    }

}
