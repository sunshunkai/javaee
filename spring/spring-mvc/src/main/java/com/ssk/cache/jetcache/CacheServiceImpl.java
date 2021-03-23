package com.ssk.cache.jetcache;

import com.ssk.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ssk
 * @date 2021/3/22
 */
@Service
public class CacheServiceImpl implements CacheService{

    @Autowired
    private CacheDao cacheDao;

    @Override
    public User findId(Long id) {
        return cacheDao.findById(id);
    }
}
