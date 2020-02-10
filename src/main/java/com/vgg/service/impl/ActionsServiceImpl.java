package com.vgg.service.impl;

import com.vgg.dao.ActionsDao;
import com.vgg.dao.ProjectDao;
import com.vgg.model.Actions;
import com.vgg.model.ActionsDto;
import com.vgg.model.Project;
import com.vgg.model.ProjectDto;
import com.vgg.service.ActionsService;
import com.vgg.service.ProjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service(value = "actionsService")
public class ActionsServiceImpl implements ActionsService {
	
	@Autowired
	private ActionsDao actionsDao;

	public  List<Actions> findAll() {
		List<Actions> list = new ArrayList<>();
		actionsDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete(int id) {
		actionsDao.deleteById(id);
	}

	@Override
	public Actions findById(int id) {
		Optional<Actions> optionalActions = actionsDao.findById(id);
		return optionalActions.isPresent() ? optionalActions.get() : null;
	}

	@Override
	public Actions findByIdAndProjectId(int id, int projectId) {
		Actions actions = actionsDao.findByIdAndProjectId(id, projectId);
		return actions != null ? actions : null;
	}

	@Override
	public List<Actions> findByProjectId(int projectId) {
		List<Actions> actionsList = actionsDao.findByProjectId(projectId);
		return actionsList != null ? actionsList : null;
	}

    @Override
    public ActionsDto update(ActionsDto actionsDto) {
        Actions actions = findById(actionsDto.getId());
        if(actions != null) {
            BeanUtils.copyProperties(actionsDto, actions);
            actionsDao.save(actions);
        }
        return actionsDto;
    }

    @Override
    public Actions save(ActionsDto actionsDto) {
	    Actions actions = new Actions();
		BeanUtils.copyProperties(actionsDto, actions);
        return actionsDao.save(actions);
    }
}
