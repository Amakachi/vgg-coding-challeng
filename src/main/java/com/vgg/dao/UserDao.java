package com.vgg.dao;

import com.vgg.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Integer> {

   //User findUserLoginByUserNameAndPassword(String username, String password);

   User findByUsername(String username);
}
