package com.vgg.controller;

import com.vgg.exceptions.impl.NotFoundException;
import com.vgg.model.*;
import com.vgg.service.ActionsService;
import com.vgg.service.ProjectService;
import com.vgg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ActionsService actionsService;

    @PostMapping
    public ApiResponse<Project> saveProject(@Valid @RequestBody ProjectDto projectDto){
        return new ApiResponse<>(HttpStatus.OK.value(), "Project saved successfully.",projectService.save(projectDto));
    }

    @GetMapping
    public ApiResponse<List<Project>> listProject(){
        return new ApiResponse<>(HttpStatus.OK.value(), "Project list fetched successfully.",projectService.findAll());
    }

    @GetMapping("/{projectId}")
    public ApiResponse<Project> getOne(@PathVariable int projectId){
        return new ApiResponse<>(HttpStatus.OK.value(), "Project fetched successfully.",projectService.findById(projectId));
    }

    @PutMapping("/{projectId}")
    public ApiResponse<ProjectDto> update(@PathVariable int projectId, @Valid @RequestBody ProjectDto projectDto) {
        if (projectService.findById(projectId) == null) throw new NotFoundException(HttpStatus.NOT_FOUND.toString(), "Project id not found");
        projectDto.setId(projectId);
        return new ApiResponse<>(HttpStatus.OK.value(), "Project updated successfully.",projectService.update(projectDto));
    }

    @DeleteMapping("/{projectId}")
    public ApiResponse<Void> delete(@PathVariable int projectId) {
        projectService.delete(projectId);
        return new ApiResponse<>(HttpStatus.OK.value(), "Project deleted successfully.", null);
    }

    @PostMapping("/{projectId}/actions")
    public ApiResponse<Actions> saveAction(@PathVariable int projectId, @Valid @RequestBody ActionsDto actionsDto){
        Project project = projectService.findById(projectId);
        if (project == null) throw new NotFoundException(HttpStatus.NOT_FOUND.toString(), "Project not found");
        actionsDto.setProject(project);
        return new ApiResponse<>(HttpStatus.OK.value(), "Actions saved successfully.",actionsService.save(actionsDto));
    }

    @GetMapping("/projects/{projectId}/actions")
    public ApiResponse<Actions> getOneAction(@PathVariable int projectId) {
        Project project = projectService.findById(projectId);
        if (project == null) throw new NotFoundException(HttpStatus.NOT_FOUND.toString(), "Project not found");
        return new ApiResponse<>(HttpStatus.OK.value(), "Project fetched successfully.", actionsService.findByProjectId(projectId));
    }

    @GetMapping("/projects/{projectId}/actions/{actionId}")
    public ApiResponse<Actions> getByProjectAndAction(@PathVariable int projectId, @PathVariable int actionId){
        return new ApiResponse<>(HttpStatus.OK.value(), "Project fetched successfully.",actionsService.findByIdAndProjectId(actionId, projectId));
    }

    @PutMapping("/projects/{projectId}/actions/{actionId}")
    public ApiResponse<Actions> updateByProjectAndAction(@PathVariable int projectId, @PathVariable int actionId, @Valid @RequestBody ActionsDto actionsDto){
        Actions action = actionsService.findByIdAndProjectId(actionId, projectId);
        if (action == null) throw new NotFoundException(HttpStatus.NOT_FOUND.toString(), "Project and Action not found");
        actionsDto.setId(action.getId());
        //actionsDto.setProject(projectId);
        return new ApiResponse<>(HttpStatus.OK.value(), "Project fetched successfully.",actionsService.update(actionsDto));
    }

    @DeleteMapping("/projects/{projectId}/actions/{actionId}")
    public ApiResponse<Void> deleteAction(@PathVariable int projectId, @PathVariable int actionId) {
        Actions action = actionsService.findByIdAndProjectId(actionId, projectId);
        if (action == null) throw new NotFoundException(HttpStatus.NOT_FOUND.toString(), "Project and Action not found");
        actionsService.delete(action.getId());
        return new ApiResponse<>(HttpStatus.OK.value(), "Action deleted successfully.", null);
    }

}
