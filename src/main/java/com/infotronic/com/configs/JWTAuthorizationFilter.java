package com.infotronic.com.configs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//System.out.println("Page requested by me :");
		
		
		
		if(request.getMethod().equals("OPTIONS")) {
			response.setStatus(HttpServletResponse.SC_OK);
			//System.out.println("OPTIONS METHOD");
			
		}else if(request.getRequestURI().equals("/login")|| request.getRequestURI().equals("/api/login")) {
			filterChain.doFilter(request, response);
			System.out.println("Login next filter");
			return ;
		}else {
		String jwtToken= request.getHeader(SecurityParams.JWT_HEADER_NAME);
		//System.out.println("Token "+ jwtToken);
		if(jwtToken==null|| !jwtToken.startsWith(SecurityParams.JWT_HEADER_PREFIX)) {
			filterChain.doFilter(request, response);
			return ;
		}
		// EXCEPTION IN THE TOKEN VERIFICATIOn
		try {
			JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SecurityParams.JWT_SECRET)).build();		
			String jwt = jwtToken.substring(SecurityParams.JWT_HEADER_PREFIX.length());
			DecodedJWT decodedJWT = verifier.verify(jwt);
			String username = decodedJWT.getSubject();
			List<String> roles = decodedJWT.getClaims().get("roles").asList(String.class);
			//System.out.println("User role"+roles.toString());
			Collection<GrantedAuthority> authorities = new ArrayList<>();
			roles.forEach(rn->{
				authorities.add(new SimpleGrantedAuthority(rn));
			});
			
			UsernamePasswordAuthenticationToken user = 
					new UsernamePasswordAuthenticationToken(username,null, authorities);
			SecurityContextHolder.getContext().setAuthentication(user);
			filterChain.doFilter(request, response);
		}catch (Exception e) {
			System.out.println(e);
			filterChain.doFilter(request, response);
			return;
		}

		}
		
		}

	

}
