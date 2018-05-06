package com.royal.controller;

import com.royal.entity.Lesson;
import com.royal.entity.Logging;
import com.royal.entity.Student;
import com.royal.service.TeacherService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/teacher")
public class TeacherController {
	@Autowired
	TeacherService teacherService;
	
	/**
	 * 教师注册 解析json 调用service
	 * @param json
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public @ResponseBody
    Map<String, Integer> register(String json, String session) {
		JSONObject jsonObject = new JSONObject(json);
		String name = jsonObject.getString("name");
		String gender = jsonObject.getString("gender");
		String school = jsonObject.getString("school");
		return teacherService.register(name, gender, school, session);
	}
	
	/**
	 * 根据3rdsession查询课程
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/selectLesson/{session}")
	public @ResponseBody
    List<Lesson> selectLesson(@PathVariable String session) {
		return teacherService.selectLesson(session);
	}
	
	/**
	 * 根据课程id查询student
	 * @param lessonId
	 * @return
	 */
	@RequestMapping(value = "/selectStudentByLId/{lessonId}")
	public @ResponseBody
    List<Student> selectStudentByLId(@PathVariable Integer lessonId) {
		return teacherService.selectStudentByLId(lessonId);
	}
	
	/**
	 * 根据学生id查询student
	 * @param studentId
	 * @return
	 */
	@RequestMapping(value = "/selctStudentBySId/{studentId}")
	public @ResponseBody
    Student selectStudentBySId(@PathVariable String studentId) {
		return teacherService.selectStudentBySId(studentId);
	}
	
	/**
	 * 新增课程
	 * @param json
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/insertLesson")
	public @ResponseBody
    Map<String, Integer> insertLesson(String json, String session) {
		JSONObject jsonObject = new JSONObject(json);
		String lessonName = jsonObject.getString("lessonName");
		String lessonNumber = jsonObject.getString("lessonNumber");
		return teacherService.insertLesson(lessonName, lessonNumber, session);
	}
	
	/**
	 * 更新课程之前 回显数据
	 * @param lessonId
	 * @return
	 */
	@RequestMapping(value = "/updateLessonQuery/{lessonId}")
	public @ResponseBody
    Lesson updateLessonQuery(@PathVariable String lessonId) {
		return teacherService.updateLessonQuery(lessonId);
	}
	
	/**
	 * 更新课程
	 * @param lessonId
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/updateLesson")
	public @ResponseBody
    Map<String, Integer> updateLesson(Integer lessonId, String json) {
		JSONObject jsonObject = new JSONObject(json);
		String lessonName = jsonObject.getString("lessonName");
		Integer lessonNumber = jsonObject.getInt("lessonNumber");
		return teacherService.updateLesson(lessonId, lessonName, lessonNumber);
	}
	
	/**
	 * 删除课程之前 查询数据
	 * @param lessonId
	 * @return
	 */
	@RequestMapping(value = "/deleteLessonQuery/{lessonId}")
	public @ResponseBody
    Map<String, Integer> deleteLessonQuery(@PathVariable String lessonId) {
		return teacherService.deleteLessonQuery(Integer.valueOf(lessonId));
	}
	
	/**
	 * 删除课程
	 * @param lessonId
	 * @return
	 */
	@RequestMapping(value = "/deleteLesson/{lessonId}")
	public @ResponseBody
    Map<String, Integer> deleteLesson(@PathVariable String lessonId) {
		return teacherService.deleteLesson(Integer.valueOf(lessonId));
	}
	
	/**
	 * 删除学生
	 * @param options
	 * @return
	 */
	@RequestMapping(value = "/deleteStudent")
	public @ResponseBody
    Map<String, Integer> deleteStudent(String options) {
		JSONObject jsonObject = new JSONObject(options);
		String studentId = jsonObject.getString("studentId");
		String lessonId = jsonObject.getString("lessonId");
		return teacherService.deleteStudent(studentId, Integer.valueOf(lessonId));
	}
	
	@RequestMapping(value = "/bindStudent")
	public @ResponseBody
    Map<String, Integer> bindStudent(String studentId, Integer lessonId) {
		return teacherService.bindStudent(studentId, lessonId);
	}
	
	@RequestMapping(value = "/getLessonNames/{session}")
	public @ResponseBody
    Map<String, Object> getLessonNames(@PathVariable String session) {
		return teacherService.getLessonNames(session);
	}
	
	/**
	 * 课程详情界面 根据课程id查询一些数据
	 * @param lessonId
	 * @return
	 */
	@RequestMapping(value = "/lessonDetail/{lessonId}")
	public @ResponseBody
    Map<String, Object> lessonDetail(@PathVariable String lessonId) {
		return teacherService.lessonDetail(Integer.valueOf(lessonId));
	}
	
	@RequestMapping(value = "/allowBindChange/{lessonId}")
	public @ResponseBody
    Map<String, Object> allowBindChange(@PathVariable String lessonId, Boolean checked) {
		return teacherService.allowBindChange(Integer.valueOf(lessonId), checked);
	}
	
	/**
	 * 根据lessonid查询 课程已经绑定的学生人数
	 * @param lessonId
	 * @return
	 */
	@RequestMapping(value = "/getBindStudentNumbers/{lessonId}")
	public @ResponseBody
    Map<String, Integer> getStudentNumbers(@PathVariable String lessonId) {
		return teacherService.getStudentNumbers(Integer.valueOf(lessonId));
	}
	
	@RequestMapping(value = "/beforeCall/{lessonId}")
	public @ResponseBody
    Integer beforeCall(@PathVariable String lessonId) {
		return teacherService.beforeCall(Integer.valueOf(lessonId));
	}
	
	@RequestMapping(value = "/cleanState/{lessonId}")
	public @ResponseBody
    Integer cleanState(@PathVariable String lessonId) {
		return teacherService.cleanLoggingState(Integer.valueOf(lessonId));
	}
	
	@RequestMapping(value = "/startCall/{lessonId}")
	public @ResponseBody
    Map<String, Object> startCall(@PathVariable String lessonId, Double latitude, Double longitude, Double altitude) {
		return teacherService.startCall(Integer.valueOf(lessonId), latitude, longitude, altitude);
	}
	
	@RequestMapping(value = "/duringCall/{uuid}")
	public @ResponseBody
    List<Student> duringCall(@PathVariable String uuid) {
		return teacherService.duringCall(uuid);
	}
	
	@RequestMapping(value = "/endCall/{uuid}")
	public @ResponseBody
    Map<String, Integer> endCall(@PathVariable String uuid) {
		return teacherService.endCall(uuid);
	}
	
	@RequestMapping(value = "/changeStudentState")
	public @ResponseBody
    Integer changeStudentState(String uuid, String studentId, Short state) {
		return teacherService.changeStudentState(uuid, studentId, state);
	}
	
	@RequestMapping(value = "/selectAllLoggings/{lessonId}")
	public @ResponseBody
    List<Logging> selectAllLoggings(@PathVariable String lessonId) {
		return teacherService.selectAllLoggings(Integer.valueOf(lessonId));
	}
	
	@RequestMapping(value = "/selectLogDetail/{uuid}")
	public @ResponseBody
    Map<String, List<Student>> selectLogDetail(@PathVariable String uuid) {
		return teacherService.selectLogDetail(uuid);
	}
	
	@RequestMapping(value = "/deleteLogging")
	public @ResponseBody
    Integer deleteLogging(String uuid, String lessonId) {
		System.err.println(uuid + lessonId);
		return teacherService.deleteLogging(uuid, Integer.valueOf(lessonId));
	}
}
