package com.vgg.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Project")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;
	private String description;
	private boolean completed;
//	@OneToMany(mappedBy = "project")
//	private List<Actions> actionsList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

//	public List<Actions> getActionsList() {
//		return actionsList;
//	}
//
//	public void setActionsList(List<Actions> actionsList) {
//		this.actionsList = actionsList;
//	}
}
