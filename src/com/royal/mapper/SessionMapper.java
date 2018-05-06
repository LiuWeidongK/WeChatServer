package com.royal.mapper;

import com.royal.entity.SessionDTO;

public interface SessionMapper {
	
	/**
	 * 插入session记录
	 * @param sessionDTO
	 * @return
	 */
	public int insertSession(SessionDTO sessionDTO);
	
	/**
	 * 根据3rdSession获取记录
	 * @param thirdSession
	 * @return
	 */
	public SessionDTO getSessionBy3Session(String thirdSession);
	
	/**
	 * 更新最后登入日期
	 * @param sessionDTO
	 * @return
	 */
	public int updateSessionTime(SessionDTO sessionDTO);
	
}
