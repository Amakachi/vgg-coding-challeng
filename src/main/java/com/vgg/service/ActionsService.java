package com.vgg.service;

import com.vgg.model.Actions;
import com.vgg.model.ActionsDto;
import com.vgg.model.Project;
import com.vgg.model.ProjectDto;

import java.util.List;

public interface ActionsService {

    Actions save(ActionsDto actionDto);

    List<Actions> findAll();

    void delete(int id);

    Actions findById(int id);

    List<Actions> findByProjectId(int projectId);

    Actions findByIdAndProjectId(int id, int projectId);

    ActionsDto update(ActionsDto actionsDto);
}
