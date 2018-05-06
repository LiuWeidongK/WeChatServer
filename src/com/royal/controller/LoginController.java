package com.royal.controller;

import com.royal.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping(value = "/login")
public class LoginController {
	@Autowired
	private LoginService loginService;
	
	@RequestMapping(value = "/getSession/{code}")
	public @ResponseBody
    Map<String, String> code2session(@PathVariable String code) {
		return loginService.code2session(code);
	}
	
	@RequestMapping(value = "/checkSession")
	public @ResponseBody
    Map<String, String> checkSession(String thirdSession) {
		return loginService.checkSession(thirdSession);
	}
}
