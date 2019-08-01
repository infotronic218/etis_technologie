package com.infotronic.com.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
@Entity
@Table(name="tusers")
public class AppUser implements Serializable{
  private static final long serialVersionUID = 1L;
  @Id
  @Column(length=100)
  private String username;
  @JsonProperty(access=Access.WRITE_ONLY)
  private String password ;
  @Transient
  @JsonIgnore
  private String confirm;
  private String email;
  private String firstname ;
  private String lastname ;
  private boolean active ;
  @ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
  private Collection<Role>roles ;
  public AppUser() {
}
public AppUser(String username, String password) {
	this.username =username;
	this.password = password ;
	
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getConfirm() {
	return confirm;
}
public void setConfirm(String confirm) {
	this.confirm = confirm;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getFirstname() {
	return firstname;
}
public void setFirstname(String firstname) {
	this.firstname = firstname;
}
public String getLastname() {
	return lastname;
}
public void setLastname(String lastname) {
	this.lastname = lastname;
}
public boolean isActive() {
	return active;
}
public void setActive(boolean active) {
	this.active = active;
}
public Collection<Role> getRoles() {
	return roles;
}
public void setRoles(Collection<Role> roles) {
	this.roles = roles;
}

public void addRole(Role role) {
	if(roles==null)
		this.roles = new ArrayList<>();
	 if(role!=null)
		 this.roles.add(role);
}
  
}
