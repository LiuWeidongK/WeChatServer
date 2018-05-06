package com.royal.entity;

public class Lesson {
	private Integer lId;
	private Integer sId;
	private String lName;
	private Integer bindNumbers;
	private Integer lNumbers;
	private Integer tId;
	private Integer joinState;
	private String bindCode;
	
	public Integer getlId() {
		return lId;
	}
	public void setlId(Integer lId) {
		this.lId = lId;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public Integer getlNumbers() {
		return lNumbers;
	}
	public void setlNumbers(Integer lNumbers) {
		this.lNumbers = lNumbers;
	}
	public Integer gettId() {
		return tId;
	}
	public void settId(Integer tId) {
		this.tId = tId;
	}
	public Integer getJoinState() {
		return joinState;
	}
	public void setJoinState(Integer joinState) {
		this.joinState = joinState;
	}
	public String getBindCode() {
		return bindCode;
	}
	public void setBindCode(String bindCode) {
		this.bindCode = bindCode;
	}
	public Integer getBindNumbers() {
		return bindNumbers;
	}
	public void setBindNumbers(Integer bindNumbers) {
		this.bindNumbers = bindNumbers;
	}
	public Integer getsId() {
		return sId;
	}
	public void setsId(Integer sId) {
		this.sId = sId;
	}
	@Override
	public String toString() {
		return "Lesson [lId=" + lId + ", sId=" + sId + ", lName=" + lName + ", bindNumbers=" + bindNumbers
				+ ", lNumbers=" + lNumbers + ", tId=" + tId + ", joinState=" + joinState + ", bindCode=" + bindCode
				+ "]";
	}
}
