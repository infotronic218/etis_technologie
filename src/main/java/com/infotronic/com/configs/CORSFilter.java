package com.infotronic.com.configs;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
//
//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORSFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpResponse.setHeader("Access-Control-Allow-Methods", "*");
//        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");

        httpResponse.setHeader("Access-Control-Allow-Headers", "*");
//        httpResponse.setHeader("Access-Control-Allow-Headers",
//                "Origin, X-Requested-With, Content-Type, Accept, X-Auth-Token, X-Csrf-Token, Authorization");

        httpResponse.setHeader("Access-Control-Allow-Credentials", "false");
        httpResponse.setHeader("Access-Control-Max-Age", "3600");

        if("OPTIONS".equalsIgnoreCase(httpServletRequest.getMethod()))
        {
        	httpResponse.setStatus(HttpServletResponse.SC_OK);
        }else if(httpServletRequest.getRequestURI().equals("/login")){
        	System.out.println("Login page request");
        	chain.doFilter(request, response);
        }else {
        
        	chain.doFilter(httpServletRequest, httpResponse);
        }
        System.out.println("********** CORS Configuration Completed **********");
		
	}

}
