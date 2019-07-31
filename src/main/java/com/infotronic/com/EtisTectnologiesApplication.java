package com.infotronic.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.infotronic.com.dao.RoleDao;
import com.infotronic.com.dao.UserDao;
import com.infotronic.com.entities.AppUser;
import com.infotronic.com.entities.Role;

@SpringBootApplication
@Configuration
@ComponentScan("com.infotronic.com")
public class EtisTectnologiesApplication {

	public static void main(String[] args) {
	ApplicationContext ctx=SpringApplication.run(EtisTectnologiesApplication.class, args);
	
	UserDao userDao = ctx.getBean(UserDao.class);
	RoleDao roleDao = ctx.getBean(RoleDao.class);
	PasswordEncoder encoder = ctx.getBean(PasswordEncoder.class);
	if(!roleDao.existsById("ADMIN")) {
		roleDao.save(new Role("ADMIN","For admin authorization"));
	}
	
	if(!roleDao.existsById("DELEGUE")) {
		roleDao.save(new Role("DELEGUE","For delegue authorization"));
	}
	
	if(!userDao.existsById("sous")) {
	   AppUser appUser = new AppUser("sous", encoder.encode("1234"));
	   appUser.setActive(true);
	   appUser.addRole(roleDao.getOne("ADMIN"));
	   appUser.addRole(roleDao.getOne("DELEGUE"));
	   userDao.save(appUser);
	}
	
	
	}
	
	

	

}
