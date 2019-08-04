package com.infotronic.com.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.infotronic.com.entities.Category;
import com.infotronic.com.entities.Post;

public interface PostDao  extends JpaRepository<Post, Long>{
 List<Post>findByActive(boolean active);
 Page<Post>findByActive(boolean active,  Pageable pageable);
 Page<Post>findByActiveAndCategory(boolean active, Category cat , Pageable pageable);
 Post findByIdAndActive(Long id,boolean active);
 Post findBySlugAndActive(String slug,boolean active);
 Long countByCategoryAndActive(Category cat, boolean active ) ;
 List<Post> findByCategory(Category cat);

}
