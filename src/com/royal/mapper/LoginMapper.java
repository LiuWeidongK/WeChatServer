package com.royal.mapper;

import com.royal.entity.Student;
import com.royal.entity.Teacher;

import java.util.List;

public interface LoginMapper {
	
	public List<Student> selectStudentByOpenid(String openid);
	
	public List<Teacher> selectTeacherByOpenid(String openid);

}
