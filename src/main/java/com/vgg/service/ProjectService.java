package com.vgg.service;

import com.vgg.model.Project;
import com.vgg.model.ProjectDto;
import com.vgg.model.User;
import com.vgg.model.UserDto;

import java.util.List;

public interface ProjectService {

    Project save(ProjectDto project);
    List<Project> findAll();
    void delete(int id);

    Project findById(int id);

    ProjectDto update(ProjectDto projectDto);
}
