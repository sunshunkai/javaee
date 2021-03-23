package com.ssk.cache.jetcache;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.Cached;
import com.alicp.jetcache.anno.CreateCache;
import com.ssk.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @author ssk
 * @date 2021/3/22
 */
@Repository
public class CacheDao {

    @CreateCache(name = "create:cache:")
    private Cache<Long,User> cache;

    @Cached(name = "user:",key = "#id")
    public User findById(Long id){
        User user1 = cache.get(id);
        if(user1 != null){
            return user1;
        }

        User user = new User();
        user.setId(id);
        user.setName("张三");
        user.setSex(1);

        cache.put(id,user);

        return user;
    }
}
