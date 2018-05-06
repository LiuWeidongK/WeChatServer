package com.royal.service;

import com.royal.entity.SessionDTO;
import com.royal.entity.Student;
import com.royal.entity.Teacher;
import com.royal.mapper.LoginMapper;
import com.royal.mapper.SessionMapper;
import com.royal.util.CustomUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoginService {
	
	@Autowired
	private LoginMapper loginMapper;
	@Autowired
	private SessionMapper sessionMapper;
	
	final String appid = "wx655fd94fb66a36ab";
	final String secret = "19f17d6eb4c515d770e63dc13a314380";
	final String grant_type = "authorization_code";
	
	/**
	 * 根据 code 构造URL
	 * 		https://api.weixin.qq.com/sns/jscode2session?appid=...&secret=...&js_code=...&grant_type=authorization_code
	 * 得到 JSON 数据包
	 * 判断数据包格式 返回Map结果集
	 * 正常结果集操作(state -> 1):
	 * 		1. 生成 3rdsession 写入缓存并添加到结果集
	 * 		2. 判断用户类型 并添加到结果集 
	 * 
	 * @param session: HttpSesssion 对象
	 * @param code:    传入 wx.login 生成的 code
	 * 
	 * @return Map结果集 state状态码:
	 * 		0: 错误时返回的JSON数据包
	 * 			{"errcode":41008,"errmsg":"missing code, hints: [ req_id: piSgva0681th44 ]"}
	 * 		1: 正常返回的JSON数据包
	 * 			{"session_key":"iFDmsBIsge7eX+6GG89KBg==","expires_in":7200,"openid":"o0ST70OwjmIJux943SqccU9ncEB4"}
	 */
	public Map<String, String> code2session(String code) {
		
		String url = "https://api.weixin.qq.com/sns/jscode2session";
		StringBuffer param = new StringBuffer();
		param.append("appid=").append(appid)
			.append("&secret=").append(secret)
			.append("&js_code=").append(code)
			.append("&grant_type=").append(grant_type);
			
		String json = CustomUtils.sendGet(url, param.toString());
		
		JSONObject jsonObject = new JSONObject(json);
		
		Map<String, String> result = new HashMap<>();

		if(jsonObject.has("errcode")) {
			result.put("state", "0");
			result.put("errcode", String.valueOf(jsonObject.getInt("errcode")));
			result.put("errmsg", jsonObject.getString("errmsg"));
		} else if(jsonObject.has("openid")) {
			result.put("state", "1");
			
			String sessionKey = jsonObject.getString("session_key");
			String openid = jsonObject.getString("openid");
			
			String _3rdSession = create3rdSession(code, sessionKey);
			saveSession(_3rdSession, sessionKey, openid);
			result.put("_3rdsession", _3rdSession);

			Integer type = getUserType(openid);
			result.put("type", String.valueOf(type));
		} else {
			result.put("state", "-1");
		}
		return result;
	}
	
	/**
	 * 判断用户类型
	 * @param json
	 * @return 1:首次登入 5:教师 6:学生 0:双类型(错误)
	 */
	private Integer getUserType(String openid) {
		
		List<Student> students = loginMapper.selectStudentByOpenid(openid);
		List<Teacher> teachers = loginMapper.selectTeacherByOpenid(openid);
		
		if(students.isEmpty() && teachers.isEmpty()) {
			// first Login
			return 1;
		} else if(students.isEmpty()) {
			// teacher Type
			return 5;
		} else if(teachers.isEmpty()) {
			// student Type
			return 6;
		} else {
			// double Type Error
			return 0;
		}
		
	}
	
	/**
	 * 以3rdsession为主键 将session_key和openid存入数据库
	 * 若记录已存在 则更新最后登入时间
	 * @param _3rdSession
	 * @param sessionKey
	 * @param openid
	 */
	private void saveSession(String _3rdSession, String sessionKey, String openid) {
		SessionDTO sessionDTO = sessionMapper.getSessionBy3Session(_3rdSession);
		if(sessionDTO == null) {
			SessionDTO newSession = new SessionDTO();
			newSession.setThirdSession(_3rdSession);
			newSession.setSessionKey(sessionKey);
			newSession.setOpenid(openid);
			newSession.setLastLoginTime(new Timestamp(new Date().getTime()));
			sessionMapper.insertSession(newSession);
		} else {
			sessionDTO.setLastLoginTime(new Timestamp(new Date().getTime()));
			sessionMapper.updateSessionTime(sessionDTO);
		}
	}
	
	/**
	 * 以客户端发来的code+获取的session_key+random随机数生成MD5
	 * @param code
	 * @param session
	 * @return
	 */
	private String create3rdSession(String code, String sessionKey) {
		String key = code + sessionKey + String.valueOf(Math.random());
		String _3rdSession = CustomUtils.getMD5(key);
		return _3rdSession;
	}
	
	/**
	 * 判断session是否存在
	 * @param thirdSession
	 * @return 存在根据openid返回类型
	 */
	public Map<String, String> checkSession(String thirdSession) {
		Map<String, String> result = new HashMap<>();
		SessionDTO sessionDTO = sessionMapper.getSessionBy3Session(thirdSession);
		
		if(sessionDTO != null) {
			result.put("state", "1");
			result.put("type", String.valueOf(getUserType(sessionDTO.getOpenid())));
			sessionDTO.setLastLoginTime(new Timestamp(new Date().getTime()));
			sessionMapper.updateSessionTime(sessionDTO);
		} else {
			// session不存在 需重新登入
			result.put("state", "0");
		}
		return result;
	}
}
