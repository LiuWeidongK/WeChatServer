package com.royal.service;

import com.royal.entity.Student;
import com.royal.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class StudentService {
	
	@Autowired
	private StudentMapper studentMapper;
	
	public void select(Model model) {
		List<Student> list = studentMapper.select();
		System.err.println("###" + list);
		model.addAttribute("result", list);
	}
}
