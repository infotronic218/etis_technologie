package com.infotronic.com.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.infotronic.com.dao.CategoryDao;
import com.infotronic.com.entities.Category;
@CrossOrigin()
@RestController
public class Categories {
	
    @Autowired
    private CategoryDao daoCat;
    
   /*For all users */
    @GetMapping("/api/categories")
	List<Category> userslist(){
	return	daoCat.findAll();
	}
    
   
    
    @GetMapping("/api/admin/categories")
	List<Category> list(){
	return	daoCat.findAll();
	}
    
    @PostMapping("/api/admin/categories")
    Category post (@RequestBody Category cat) {
    	return daoCat.save(cat);
    }
    
    @PostMapping("/api/admin/categories/{id}")
    Category update (@RequestBody Category newCat, @PathVariable(name="id")String id) {
    	Category oldCat = null;
    	if(daoCat.findById(id).isPresent()) {
    		 oldCat = daoCat.findById(id).get();
    		 oldCat.setDescription(newCat.getDescription());
    		 oldCat.setName(newCat.getName());
    		return daoCat.save(oldCat);
    	}
    	return oldCat ;
    }
    
   @DeleteMapping("/api/admin/categories/{id}")
   boolean delete ( @PathVariable(name="id")String id) {
    	if(daoCat.findById(id).isPresent()) {
    		 daoCat.deleteById(id);
    		 return true;
      }
     return false ;
   }
}
