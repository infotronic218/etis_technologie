package com.infotronic.com.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infotronic.com.entities.AppUser;

public interface UserDao extends JpaRepository<AppUser, String>{

}
