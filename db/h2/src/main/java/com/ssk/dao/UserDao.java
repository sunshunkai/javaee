package com.ssk.dao;

import com.ssk.entity.User;
import org.springframework.data.repository.CrudRepository;

/**
 * @author ssk
 * @date 2020/8/26
 */
public interface UserDao extends CrudRepository<User,Long> {

    User findByName(String name);

    @Override
    Iterable<User> findAll();

    @Override
    void delete(User user);

}
