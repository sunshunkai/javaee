package com.ssk.dao;


import com.ssk.mode.UserDO;

/**
 * @author ssk
 * @date 2020/8/21
 */
public interface UserDao {

    UserDO findById(Long id);

}
