package com.ssk.cache.jetcache;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.ssk.entity.User;

/**
 * @author ssk
 * @date 2021/3/22
 */
public interface CacheService {

//    @Cached(name = "cache",cacheType = CacheType.LOCAL)
    User findId(Long id);

}
