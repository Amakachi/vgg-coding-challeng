package com.vgg.service.impl;

import com.vgg.dao.ProjectDao;
import com.vgg.dao.UserDao;
import com.vgg.model.Project;
import com.vgg.model.ProjectDto;
import com.vgg.model.User;
import com.vgg.model.UserDto;
import com.vgg.service.ProjectService;
import com.vgg.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service(value = "projectService")
public class ProjectServiceImpl implements ProjectService {
	
	@Autowired
	private ProjectDao projectDao;

	public  List<Project> findAll() {
		List<Project> list = new ArrayList<>();
		projectDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete(int id) {
		projectDao.deleteById(id);
	}



	@Override
	public Project findById(int id) {
		Optional<Project> optionalProject = projectDao.findById(id);
		return optionalProject.isPresent() ? optionalProject.get() : null;
	}

    @Override
    public ProjectDto update(ProjectDto projectDto) {
        Project project = findById(projectDto.getId());
        if(project != null) {
            BeanUtils.copyProperties(projectDto, project);
            projectDao.save(project);
        }
        return projectDto;
    }

    @Override
    public Project save(ProjectDto projectDto) {
	    Project project = new Project();
		BeanUtils.copyProperties(projectDto, project);
        return projectDao.save(project);
    }
}
