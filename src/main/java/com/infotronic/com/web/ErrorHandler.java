package com.infotronic.com.web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class ErrorHandler implements ErrorController {

	@RequestMapping("/error")
	public String error() {
		return "index";
	}
	@Override
	public String getErrorPath() {
	
		return "/error";
	}

}
