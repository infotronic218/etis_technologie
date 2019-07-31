package com.infotronic.com.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
@Entity
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;
@Id
@Column(columnDefinition="VARCHAR(100)")
private String name;
private String description;
public String getName() {
	return name;
}
@Transient
private Long count ;
public void setName(String name) {
	this.name = name;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public Long getCount() {
	return count;
}
public void setCount(Long count) {
	this.count = count;
}

 
}
