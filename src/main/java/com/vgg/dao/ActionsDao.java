package com.vgg.dao;

import com.vgg.model.Actions;
import com.vgg.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ActionsDao extends JpaRepository<Actions, Integer> {

   //User findUserLoginByUserNameAndPassword(String username, String password);

   List<Actions> findByProjectId(Integer projectId);

   Actions findByIdAndProjectId(Integer Id, Integer projectId);
}
