package com.infotronic.com.configs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infotronic.com.entities.AppUser;



public class JWTAuthentificationFilter extends UsernamePasswordAuthenticationFilter  {

	private AuthenticationManager authenticationManager ;
	
	public JWTAuthentificationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		setFilterProcessesUrl(SecurityParams.LOGIN_URL);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
	 //System.out.println("User attempt to connexion !");
		try {
			
			AppUser user  = new ObjectMapper().readValue(request.getInputStream(), AppUser.class);
			System.out.println(user.getUsername()+" "+user.getPassword());
			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
    User springUser = (User) authResult.getPrincipal();
    List<String>roles = new ArrayList<>();
    springUser.getAuthorities().forEach(role->{
    	roles.add(role.getAuthority());
    });
		
    Date expiration = new Date(System.currentTimeMillis()+SecurityParams.EXPIRATION);
    System.out.println("Expiration"+ expiration); 
    String jwt = JWT.create()
    		      .withIssuer(request.getRequestURI())
    		      .withSubject(springUser.getUsername())
    		      .withArrayClaim("roles", roles.toArray(new String[roles.size()]))
    		      .withExpiresAt(expiration)
    		      .sign(Algorithm.HMAC256(SecurityParams.JWT_SECRET));
     
    response.addHeader(SecurityParams.JWT_HEADER_NAME,jwt);
     response.addDateHeader("expiration", expiration.getTime());
     
     //System.out.println("Token "+ jwt); 
    // System.out.println(response.getHeader(SecurityParams.JWT_HEADER_NAME));
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		// TODO Auto-generated method stub
		super.unsuccessfulAuthentication(request, response, failed);
	}
	
    
}
