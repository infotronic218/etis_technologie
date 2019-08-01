package com.infotronic.com.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
	
}
