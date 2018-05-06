package com.royal.controller;

import com.royal.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/student")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@RequestMapping(value = "/index")
	public String index(Model model) {
		studentService.select(model);
		return "student/student_home";
	}
	
}
