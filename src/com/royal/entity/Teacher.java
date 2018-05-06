package com.royal.entity;

public class Teacher {
	private String tOpenid;
	private Integer tId;
	private String tName;
	private String tGender;
	private String tMacAddress;
	private String tSchool;
	
	public String gettOpenid() {
		return tOpenid;
	}
	public void settOpenid(String tOpenid) {
		this.tOpenid = tOpenid;
	}
	public Integer gettId() {
		return tId;
	}
	public void settId(Integer tId) {
		this.tId = tId;
	}
	public String gettName() {
		return tName;
	}
	public void settName(String tName) {
		this.tName = tName;
	}
	public String gettGender() {
		return tGender;
	}
	public void settGender(String tGender) {
		this.tGender = tGender;
	}
	public String gettMacAddress() {
		return tMacAddress;
	}
	public void settMacAddress(String tMacAddress) {
		this.tMacAddress = tMacAddress;
	}
	public String gettSchool() {
		return tSchool;
	}
	public void settSchool(String tSchool) {
		this.tSchool = tSchool;
	}
	@Override
	public String toString() {
		return "Teacher [tOpenid=" + tOpenid + ", tId=" + tId + ", tName=" + tName + ", tGender=" + tGender
				+ ", tMacAddress=" + tMacAddress + ", tSchool=" + tSchool + "]";
	}
}
