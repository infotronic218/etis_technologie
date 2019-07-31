package com.infotronic.com.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infotronic.com.entities.Role;

public interface RoleDao extends JpaRepository<Role, String>{

}
