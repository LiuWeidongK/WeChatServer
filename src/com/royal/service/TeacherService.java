package com.royal.service;

import com.royal.entity.*;
import com.royal.mapper.LoginMapper;
import com.royal.mapper.SessionMapper;
import com.royal.mapper.TeacherMapper;
import com.royal.util.CustomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private SessionMapper sessionMapper;
    @Autowired
    private LoginMapper loginMapper;

    /**
     * 教师注册模块
     *
     * @param name    姓名
     * @param gender  为转换的性别 0 / 1
     * @param school  为转换的学校 暂默认CCUT
     * @param session 小程序本地缓存的3rdsession
     * @return 结果集
     */
    public Map<String, Integer> register(String name, String gender, String school, String session) {
        Map<String, Integer> result = new HashMap<>();
        // 根据3rdsession得到openid
        SessionDTO sessionDTO = sessionMapper.getSessionBy3Session(session);
        String openid = sessionDTO.getOpenid();
        // 构造teacher对象
        Teacher teacher = new Teacher();
        teacher.settName(name);
        teacher.settGender(gender.equals("0") ? "男" : "女");
        teacher.settSchool("CCUT");
        teacher.settOpenid(openid);
        // 根据openid判断是否已经注册过
        if (checkRepetByOpenid(openid)) {
            // 未注册过 插入teacher对象到数据库 返回结果集
            teacherMapper.registerTeacher(teacher);
            result.put("state", 1);
        } else {
            // 已经注册过 返回结果集
            result.put("state", 0);
        }
        return result;
    }

    /**
     * 根据openid查询student和teacher表 查看是否存在数据
     *
     * @param openid
     * @return 若两个表都是空 则证明为注册过 反之注册过
     */
    private Boolean checkRepetByOpenid(String openid) {
        List<Student> students = loginMapper.selectStudentByOpenid(openid);
        List<Teacher> teachers = loginMapper.selectTeacherByOpenid(openid);

        return students.isEmpty() && teachers.isEmpty();
    }

    /**
     * 根据 3rdsession 获取 openid
     * 根据 openid 查询课程列表
     *
     * @param session
     * @return 课程list
     */
    public List<Lesson> selectLesson(String session) {
        SessionDTO sessionDTO = sessionMapper.getSessionBy3Session(session);
        String openid = sessionDTO.getOpenid();
        List<Lesson> lessons = teacherMapper.selectLessonByTId(openid);
        for (Lesson lesson : lessons) {
            if (lesson.getsId() == null) {
                lesson.setBindNumbers(0);
            }
        }
        return lessons;
    }

    /**
     * 按课程id查询学生
     *
     * @param lessonId
     * @return 学生list
     */
    public List<Student> selectStudentByLId(Integer lessonId) {
        return teacherMapper.selectStudentByLId(lessonId);
    }

    /**
     * 按学生id查询学生
     *
     * @param studentId
     * @return 学生list
     */
    public Student selectStudentBySId(String studentId) {
        return teacherMapper.selectStudentBySId(studentId);
    }

    /**
     * 添加课程
     *
     * @param lessonName   课程名称
     * @param lessonNumber 课程人数
     * @param session      3rdSession -> openid -> teacherid
     * @return 结果集
     */
    public Map<String, Integer> insertLesson(String lessonName, String lessonNumber, String session) {
        Map<String, Integer> result = new HashMap<>();
        SessionDTO sessionDTO = sessionMapper.getSessionBy3Session(session);
        if (sessionDTO == null) {
            result.put("state", 0);
            return result;
        }
        String openid = sessionDTO.getOpenid();

        Teacher teacher = teacherMapper.selectTeacherByOpenid(openid);

        Lesson lesson = new Lesson();
        lesson.setlName(lessonName);
        lesson.setlNumbers(Integer.valueOf(lessonNumber));
        lesson.settId(teacher.gettId());

        teacherMapper.insertLesson(lesson);
        result.put("state", 1);
        return result;
    }

    /**
     * 更新课程之前 回显数据
     *
     * @param lessonId
     * @return 课程dto
     */
    public Lesson updateLessonQuery(String lessonId) {
        return teacherMapper.selectLessonByLId(Integer.valueOf(lessonId));
    }

    /**
     * 更新课程 构造dto 调用mapper
     *
     * @param lessonId
     * @param lessonName
     * @param lessonNumber
     * @return 结果集
     */
    public Map<String, Integer> updateLesson(Integer lessonId, String lessonName, Integer lessonNumber) {
        Map<String, Integer> result = new HashMap<>();
        Lesson lesson = new Lesson();
        lesson.setlId(lessonId);
        lesson.setlName(lessonName);
        lesson.setlNumbers(lessonNumber);
        int count = teacherMapper.updateLesson(lesson);
        if (count > 0) {
            result.put("state", 1);
        } else {
            result.put("state", 0);
        }
        return result;
    }

    /**
     * 删除课程之前 查询数据
     * 查询历史记录和学生关联信息
     *
     * @param lessonId
     * @return 历史记录个数和学生关联个数
     */
    public Map<String, Integer> deleteLessonQuery(Integer lessonId) {
        Map<String, Integer> result = new HashMap<>();
        List<Logging> loggings = teacherMapper.selectLoggingByLId(lessonId);
        List<Student> students = teacherMapper.selectStu2LessByLId(lessonId);
        result.put("logNumbers", loggings.size());
        result.put("studentNumbers", students.size());
        return result;
    }

    /**
     * 删除课程
     *
     * @param lessonId
     * @return 结果集
     */
    public Map<String, Integer> deleteLesson(Integer lessonId) {
        Map<String, Integer> result = new HashMap<>();
        int count = teacherMapper.deleteLesson(lessonId);
        result.put("state", count);
        return result;
    }

    /**
     * 根据学生id和课程id 移除学生和课程的绑定关系
     *
     * @param studentId
     * @param lessonId
     * @return
     */
    public Map<String, Integer> deleteStudent(String studentId, Integer lessonId) {
        Map<String, Integer> result = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        params.put("studentId", studentId);
        params.put("lessonId", lessonId);
        int count = teacherMapper.deleteStudent(params);
        result.put("state", count);
        return result;
    }

    /**
     * 在课程中绑定学生
     *
     * @param studentId
     * @param lessonId
     * @return -1 学生重复 即已存在于课程之中
     * -2 课程可绑定的学生已满
     * >0 成功
     */
    public Map<String, Integer> bindStudent(String studentId, Integer lessonId) {
        Map<String, Integer> result = new HashMap<>();

        List<Student> students = teacherMapper.selectStu2LessByLId(lessonId);
        Lesson lesson = teacherMapper.selectLessonByLId(lessonId);

        for (Student student : students) {
            if (student.getsNumber().equals(studentId)) {
                result.put("state", -1);
                return result;
            }
        }

        if (students.size() >= lesson.getlNumbers()) {
            result.put("state", -2);
            return result;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("studentId", studentId);
        params.put("lessonId", lessonId);
        int count = teacherMapper.bindStudent(params);
        result.put("state", count);
        return result;
    }

    /**
     * 根据session获取此教师的课程名称和id
     * 用于开始点名之前生成ActionSheet(因为actionSheet只能传入一个参数的列表)
     *
     * @param session
     * @return
     */
    public Map<String, Object> getLessonNames(String session) {
        Map<String, Object> result = new HashMap<>();
        List<String> lessonName = new ArrayList<>();
        List<Integer> lessonId = new ArrayList<>();
        List<Lesson> lessons = selectLesson(session);
        for (Lesson lesson : lessons) {
            lessonName.add(lesson.getlName());
            lessonId.add(lesson.getlId());
        }
        result.put("lessonName", lessonName);
        result.put("lessonId", lessonId);
        return result;
    }

    /**
     * 1. 根据课程id 获取课程dto
     * 2. 根据课程id 获取课程已绑定人数
     * 3. 根据课程id 生成密文Code
     * 如果存在记录 则Code不变 即不重新生成 而是获取
     * 4. 根据课程id 获取自动绑定状态码
     * -1 ：未查到此条数据
     * 0 ：禁止学生加入课程
     * 1 ：允许学生加入课程
     *
     * @param lessonId
     * @return 返回结果集
     */
    public Map<String, Object> lessonDetail(Integer lessonId) {
        Map<String, Object> result = new HashMap<>();
        Lesson lesson = teacherMapper.selectLessonByLId(lessonId);
        List<Student> students = teacherMapper.selectStu2LessByLId(lessonId);
        result.put("lesson", lesson);
        result.put("bindStudents", students.size());
        return result;
    }

    /**
     * 课程详细信息中switch按钮状态改变事件
     * 1. 根据课程id查找LessonDTO -> 查看是否存在BindCode
     * 存在获取 不存在生成并插入
     * 2. 根据switch按钮的状态 改变JoinState
     *
     * @param lessonId
     * @param checked
     * @return
     */
    public Map<String, Object> allowBindChange(Integer lessonId, Boolean checked) {
        Map<String, Object> result = new HashMap<>();
        Lesson lesson = teacherMapper.selectLessonByLId(lessonId);
        if (checked) {
            if (lesson.getBindCode() == null) {
                String encrypt = CustomUtils.encrypt(String.valueOf(lessonId), 8);
                lesson.setBindCode(encrypt);
            }
            lesson.setJoinState(1);
            result.put("encrypt", lesson.getBindCode());
        } else {
            lesson.setJoinState(0);
            result.put("encrypt", null);
        }
        teacherMapper.updateLessonState(lesson);
        return result;
    }

    /**
     * 根据课程id 获取已绑定的学生人数
     *
     * @param lessonId
     * @return
     */
    public Map<String, Integer> getStudentNumbers(Integer lessonId) {
        Map<String, Integer> result = new HashMap<>();
        List<Student> students = teacherMapper.selectStu2LessByLId(lessonId);
        result.put("bindStudents", students.size());
        return result;
    }

    /**
     * 开始点名之前 检查是否有未完成的记录
     *
     * @param lessonId
     * @return
     */
    public Integer beforeCall(Integer lessonId) {
        return teacherMapper.selectLoggingByLIdAndState(lessonId).size();
    }

    /**
     * 将未完成的记录置为已完成
     *
     * @param lessonId
     * @return
     */
    public Integer cleanLoggingState(Integer lessonId) {
        return teacherMapper.updateLogStateByLessonId(lessonId);
    }

    /**
     * 开始点名
     *
     * @param lessonId
     * @param latitude  经度
     * @param longitude 纬度
     * @param altitude  高度
     * @return
     */
    public Map<String, Object> startCall(Integer lessonId, Double latitude, Double longitude, Double altitude) {
        Map<String, Object> result = new HashMap<>();
        Logging logging = new Logging();
        logging.setLogUuid(CustomUtils.encrypt(String.valueOf(lessonId), 8));
        logging.setlId(lessonId);
        logging.setLatitude(latitude);
        logging.setLongitude(longitude);
        logging.setAltitude(altitude);
        int count = teacherMapper.insertLogging(logging);
        if (count > 0)
            result.put("uuid", logging.getLogUuid());
        else
            result.put("uuid", null);
        return result;
    }

    /**
     * 点名中 动态查询未到学生列表
     *
     * @param uuid
     * @return
     */
    public List<Student> duringCall(String uuid) {
        return teacherMapper.selectNotComeStudents(uuid);
    }

    /**
     * 点名结束 更改状态
     *
     * @param uuid
     * @return
     */
    public Map<String, Integer> endCall(String uuid) {
        Map<String, Integer> result = new HashMap<>();
        int count = teacherMapper.updateLogStateByUuid(uuid);
        result.put("state", count);
        return result;
    }

    /**
     * 点名结束后 改变学生的状态
     *
     * @param uuid
     * @param studentId
     * @param state
     * @return
     */
    public Integer changeStudentState(String uuid, String studentId, Short state) {
        Logging logging = new Logging();
        logging.setLogUuid(uuid);
        logging.setsNumber(Integer.valueOf(studentId));
        logging.setsState(state);
        return teacherMapper.insertLogDetail(logging);
    }

    /**
     * 根据课程id 查询所有记录
     *
     * @param lessonId
     * @return
     */
    public List<Logging> selectAllLoggings(Integer lessonId) {
        List<Logging> loggings = teacherMapper.selectAllLoggingByLId(lessonId);
        for (Logging logging : loggings) {
            logging.setNotComeStudents(teacherMapper.selectNotComeStudents(logging.getLogUuid()).size());
            logging.setAllStudents(teacherMapper.selectStu2LessByLId(logging.getlId()).size());
            logging.setTransDate(Arrays.asList(CustomUtils.formatDate(logging.getOptionTime()).split(" ")));
        }
        return loggings;
    }

    /**
     * 根据一条记录的UUID 查询记录详情
     *
     * @param uuid
     * @return
     */
    public Map<String, List<Student>> selectLogDetail(String uuid) {
        Map<String, List<Student>> result = new HashMap<>();
        List<Student> nStudents = teacherMapper.selectNotComeStudents(uuid);
        for (Student student : nStudents) {
            if (student.getsState() == null) {
                student.setsState((short) 0);
            }
        }
        List<Student> cStudents = teacherMapper.selectComeStudent(uuid);
        result.put("notCome", nStudents);
        result.put("coming", cStudents);
        return result;
    }

    public Integer deleteLogging(String uuid, Integer lessonId) {
        Map<String, Object> params = new HashMap<>();
        params.put("uuid", uuid);
        params.put("lessonId", lessonId);
        return teacherMapper.deleteLogging(params);
    }
}

