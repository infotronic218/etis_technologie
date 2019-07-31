package com.infotronic.com.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Role implements Serializable{
	private static final long serialVersionUID = 1L;
@Id
  @Column(length=100)
  private String name ;
  private String description;
 public Role() {
	 
 }
public Role(String name, String description) {
	this.name = name;
	this.description = description;
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
  
}
