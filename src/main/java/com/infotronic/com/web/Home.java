package com.infotronic.com.web;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Home {
	@GetMapping("/")
	public String home() {
		return "index";
	}
    
//	@RequestMapping("/api/login")
//	public String login() {
//		return "redirect:/login";
//	}
	@PostMapping("/push")
	public String push(HttpRequest request) {
	  System.out.println("Helllo from push");
		return "ok";
	}
	
}
