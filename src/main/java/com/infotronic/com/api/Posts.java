package com.infotronic.com.api;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.slugify.Slugify;
import com.infotronic.com.dao.CategoryDao;
import com.infotronic.com.dao.PostDao;
import com.infotronic.com.dao.UserDao;
import com.infotronic.com.entities.AppUser;
import com.infotronic.com.entities.Category;
import com.infotronic.com.entities.Post;
@CrossOrigin
@RestController
public class Posts {
	@Autowired
	private PostDao postDao ;
	
	@Autowired
	private UserDao userDao ;
	
	
	@Autowired
	 private CategoryDao daoCat;
	/*For all user*/
	@GetMapping("/api/posts")
	public Page<Post> usersList(@RequestParam(required=false, defaultValue="0")int page ,@RequestParam(required=false, defaultValue="2" )int size){
		Page<Post> list =  postDao.findByActive(true,PageRequest.of(page, size, new Sort(Sort.Direction.DESC,"created")));
		
		list.forEach(post ->{
			  post.getAuthor().setUsername("null");
			  post.getAuthor().setEmail("");
			  post.getAuthor().setRoles(null);
			
			 
		  });
		
		return  list;
	}
	
	@GetMapping("/api/posts/{categoryId}/category")
	public Page<Post> postByCategory(
			@PathVariable(name="categoryId",required=true)String categoryId,
			@RequestParam(required=false, defaultValue="0")int page ,
			@RequestParam(required=false, defaultValue="2" )int size){
		Category cat  = daoCat.findById(categoryId).get();
		return postDao.findByActive(true,PageRequest.of(page, size));
	}
	
	/*Get total post in a category*/
	 @GetMapping("/api/pcategories")
	 List<Category> getCategoriesWithPosts(){
	    List<Category> list=   daoCat.findAll();
	     list.forEach(cat->{
	        	cat.setCount(postDao.countByCategory(cat));
	      });
	    return list;
	    }
	
	
	/*For all user*/
	@GetMapping("/api/posts/{slug}")
	public Post upostSlug(@PathVariable(name="slug")String slug){
		return postDao.findBySlugAndActive(slug, true);
	}
	
	@GetMapping("/api/admin/posts")
	public List<Post> getList(){
		return postDao.findAll();
	}
    /* Create unique slug in the database */
	public String createUniqueSlug(String title) {
		Slugify slg = new Slugify();
		String slug = slg.slugify(title);
		return slug ;
	}
	
	@PostMapping("/api/admin/posts/{category}")
	public Post addPost(@RequestBody Post post, 
			@PathVariable(name="category", required= true)String category) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		AppUser author = userDao.findById(authentication.getName()).get();
		
		post.setAuthor(author);
		post.setSlug(createUniqueSlug(post.getTitle()));
		System.out.println(post.toString());
		post.setCategory(daoCat.findById(category).get());
		System.out.println(category);
		post.setCreated(new Date());
		post.setUpdated(new Date());
		
		return postDao.save(post) ;
	}
	@GetMapping("/api/admin/posts/{id}")
	public Post post(@PathVariable(name="id")Long id) {
		return postDao.getOne(id);
	}
	
	@PutMapping("/api/admin/posts/{id}/{category}")
	public Post update(@PathVariable(name="id")Long id, @PathVariable(name="category")String category, @RequestBody Post newPost ) {
		 if(postDao.existsById(id)) 
		 {
			Post post = postDao.findById(id).get();
			post.setContent(newPost.getContent());
			post.setDescription(newPost.getDescription());
			post.setTitle(newPost.getTitle());
			post.setPhotoUrl(newPost.getPhotoUrl());
			post.setActive(newPost.isActive());
			post.setUpdated(new Date());
			post.setSlug(createUniqueSlug(post.getTitle()));
			if(post.getAuthor()==null) {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				AppUser author = userDao.findById(authentication.getName()).get();
				post.setAuthor(author);
			}
			if(daoCat.existsById(category)) {
				post.setCategory(daoCat.findById(category).get());
			}
			return  postDao.save(post) ;
		 }
		 newPost.setId(id);
		 return postDao.save(newPost) ;
		
	}
	
	@PutMapping("/api/admin/posts/{id}/activate")
	public Post activatePost(@PathVariable(name="id")Long id) {
		if(postDao.existsById(id)) {
			Post post= postDao.findById(id).get();
			post.setActive(!post.isActive());
			return postDao.save(post) ;
		}
		return null;
		
	}
	
	@DeleteMapping("/api/admin/posts/{id}")
	public boolean delete(@PathVariable(name="id")Long id) {
		if(postDao.existsById(id)) {
			postDao.deleteById(id);
			return true ;
		}
		return false;
		
	}
}
