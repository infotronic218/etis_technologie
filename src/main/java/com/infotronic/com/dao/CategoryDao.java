package com.infotronic.com.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infotronic.com.entities.Category;

public interface CategoryDao extends JpaRepository<Category, String> {
	
 
}
