package com.infotronic.com.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.infotronic.com.dao.CategoryDao;
import com.infotronic.com.dao.RoleDao;
import com.infotronic.com.dao.UserDao;
import com.infotronic.com.entities.AppUser;

@RestController
public class Users {
	@Autowired
	UserDao userDao ;
	@Autowired
	private RoleDao roleDao ;
	
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
	@PostMapping("/register")
	public Map<String, Object> register(@RequestBody UserCreateForm createForm) {
		Map<String, Object> map =new  HashMap<String, Object>();
		
		map.put("user", createForm);
		return map;
	}
	
	public AppUser userFormToAppUser(UserCreateForm createForm) {
		AppUser appUser = new AppUser();
		appUser.setFirstname(createForm.firstname);
		appUser.setLastname(createForm.lastname);
		appUser.setUsername(createForm.username);
		appUser.setPassword(createForm.password);
		appUser.setEmail(createForm.email);
		appUser.addRole(roleDao.findById("DELEGUE").get());;
		return appUser;
	}
	
	@PostMapping("/api/users/account/reset")
	public String reset() {
		return "";
	}
}


class UserCreateForm{
	public	String username;
	public	String email;
	public	String firstname;
	public	String lastname;
	public  String password ;
	public String  confirm;
	
	public String toString() {
		return "{ "+email+ " "+ firstname + " "+lastname +" }";
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
