package com.infotronic.com;

import java.sql.Date;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
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
	private static ConfigurableApplicationContext ctx;
	public static void main(String[] args) {
	ctx=SpringApplication.run(EtisTectnologiesApplication.class, args);
	
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
	
	try {
		restart();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}
	
	 public static void restart() throws InterruptedException {
	        ApplicationArguments args = ctx.getBean(ApplicationArguments.class);
	        Date date = new Date(System.currentTimeMillis());
	        long time =System.currentTimeMillis();
	        Long hour = (date.getTime() % 86400000) / 3600000;
	        System.out.println("Hour is "+hour);
	        
	        Thread thread = new Thread(() -> {
	        	try {
					 
					 if(hour>=6 && hour<=23) {
						 Thread.sleep(25*60*1000);
				        }else {
				        	Thread.sleep(25*60*1000);
				        }
					  ctx.close();
			          ctx = SpringApplication.run(EtisTectnologiesApplication.class, args.getSourceArgs());
			          System.out.println("Application restarted");
			          restart() ;
	        	} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					
				 
				  
				}
	           
	        });
	        
	        thread.setDaemon(false);
	        thread.start();
	        
	    }

	

}
