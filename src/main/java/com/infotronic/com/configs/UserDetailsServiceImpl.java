package com.infotronic.com.configs;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.infotronic.com.dao.UserDao;
import com.infotronic.com.entities.AppUser;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
	private UserDao userDao;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser user = null;
		if(userDao.existsById(username))
		 user = userDao.findById(username).get();
		if(user==null)
			throw new UsernameNotFoundException("The user does not exists");
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role->{
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		});
		return 
		new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
	}

}
