package com.royal.mapper;

import com.royal.entity.Lesson;
import com.royal.entity.Logging;
import com.royal.entity.Student;
import com.royal.entity.Teacher;

import java.util.List;
import java.util.Map;

public interface TeacherMapper {
	
	/**
	 * 教师注册 插入数据
	 * @param teacher
	 * @return
	 */
	public int registerTeacher(Teacher teacher);
	
	/**
	 * 按 openid 查询 teacher
	 * @param openid
	 * @return
	 */
	public Teacher selectTeacherByOpenid(String openid);
	
	/**
	 * 根据教师openid查询课程
	 * @param openid
	 * @return
	 */
	public List<Lesson> selectLessonByTId(String openid);
	
	/**
	 * 根据课程id查询学生
	 * @param lessonId
	 * @return
	 */
	public List<Student> selectStudentByLId(Integer lessonId);
	
	/**
	 * 课程模块的增删改查
	 * @param lessonId
	 * @return
	 */
	public Lesson selectLessonByLId(Integer lessonId);
	
	public int insertLesson(Lesson lesson);
	
	public int deleteLesson(Integer lessonId);
	
	public int updateLesson(Lesson lesson);
	
	public int updateLessonState(Lesson lesson);
	
	/**
	 * 学生模块的增删查
	 * @param studentId
	 * @return
	 */
	public Student selectStudentBySId(String studentId);
	
	public int bindStudent(Map<String, Object> params);
	
	public int deleteStudent(Map<String, Object> params);
	
	/**
	 * 根据课程id查询 记录(logging)表
	 * @param lessonId
	 * @return
	 */
	public List<Logging> selectLoggingByLId(Integer lessonId);
	
	/**
	 * 根据课程id查询 学生-课程(stu2lesson)表
	 * @param lessonId
	 * @return
	 */
	public List<Student> selectStu2LessByLId(Integer lessonId);
	
	/**
	 * 开始签到之前 检查未结束的签到记录
	 * @param lessonId
	 * @return
	 */
	public List<Logging> selectLoggingByLIdAndState(Integer lessonId);
	
	/**
	 * 根据lessonid将所有state为1的记录改为0
	 * 用于开始签到之前 检查完之后 更新
	 * @param lessonId
	 * @return
	 */
	public int updateLogStateByLessonId(Integer lessonId);
	
	/**
	 * 开始签到 插入一条记录到logging表
	 * @param logging
	 * @return
	 */
	public int insertLogging(Logging logging);
	
	/**
	 * 签到中 查询未到的学生信息
	 * @param uuid
	 * @return
	 */
	public List<Student> selectNotComeStudents(String uuid);
	
	/**
	 * 结束签到 更新logging表中的状态
	 * @param uuid
	 * @return
	 */
	public int updateLogStateByUuid(String uuid);
	
	/**
	 * 用于在教师端改变未到的学生记录
	 * 将未到的学生状态改为 正常、迟到、病假或事假
	 * @param logging
	 * @return
	 */
	public int insertLogDetail(Logging logging);
	
	/**
	 * 根据课程id查询所有记录
	 * @param lessonId
	 * @return
	 */
	public List<Logging> selectAllLoggingByLId(Integer lessonId);
	
	/**
	 * 根据uuid查询logdetail表中的学生记录
	 * @param uuid
	 * @return
	 */
	public List<Student> selectComeStudent(String uuid);
	
	public int deleteLogging(Map<String, Object> params);
}
