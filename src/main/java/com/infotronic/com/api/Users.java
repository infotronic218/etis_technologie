package com.infotronic.com.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.infotronic.com.dao.UserDao;
import com.infotronic.com.entities.AppUser;

@RestController
public class Users {
	@Autowired
	UserDao userDao ;
	
	@GetMapping("/api/users/account")
	public Map<String, Object> user(){
		Map<String, Object> map =new  HashMap<String, Object>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		AppUser appUser = userDao.findById(authentication.getName()).get();
		map.put("user", appUser);
		return map ;
	}

	@PostMapping("/api/users/account/update")
	public Map<String, Object> update(@RequestBody UserForm localUser){
		Map<String, Object> map =new  HashMap<String, Object>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		AppUser appUser = userDao.findById(authentication.getName()).get();
		appUser.setEmail(localUser.email);
		appUser.setFirstname(localUser.firstname);
		appUser.setLastname(localUser.lastname);
		appUser= userDao.save(appUser);
		
		System.out.println(localUser.toString());
		map.put("user", appUser);
		return map ;
	}
	
}


  class UserForm{
	public	String username;
	public	String email;
	public	String firstname;
	public	String lastname;
	
	public String toString() {
		return "{ "+email+ " "+ firstname + " "+lastname +" }";
		

		
	}
   
	
}
