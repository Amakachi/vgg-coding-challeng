package com.vgg.dao;

import com.vgg.model.Project;
import com.vgg.model.User;
import org.springframework.data.repository.CrudRepository;

public interface ProjectDao extends CrudRepository<Project, Integer> {


}
