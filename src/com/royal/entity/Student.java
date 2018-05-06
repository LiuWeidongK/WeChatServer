package com.royal.entity;

public class Student {
	private String sOpenid;
	private String sNumber;
	private String sName;
	private String sGender;
	private String sMacAddress;
	private String sSchool;
	private String sClass;
	private Short sState;
	private Double dist;
	
	public String getsOpenid() {
		return sOpenid;
	}
	public void setsOpenid(String sOpenid) {
		this.sOpenid = sOpenid;
	}
	public String getsNumber() {
		return sNumber;
	}
	public void setsNumber(String sNumber) {
		this.sNumber = sNumber;
	}
	public String getsName() {
		return sName;
	}
	public void setsName(String sName) {
		this.sName = sName;
	}
	public String getsGender() {
		return sGender;
	}
	public void setsGender(String sGender) {
		this.sGender = sGender;
	}
	public String getsMacAddress() {
		return sMacAddress;
	}
	public void setsMacAddress(String sMacAddress) {
		this.sMacAddress = sMacAddress;
	}
	public String getsSchool() {
		return sSchool;
	}
	public void setsSchool(String sSchool) {
		this.sSchool = sSchool;
	}
	public String getsClass() {
		return sClass;
	}
	public void setsClass(String sClass) {
		this.sClass = sClass;
	}
	public Short getsState() {
		return sState;
	}
	public void setsState(Short sState) {
		this.sState = sState;
	}
	public Double getDist() {
		return dist;
	}
	public void setDist(Double dist) {
		this.dist = dist;
	}
	@Override
	public String toString() {
		return "Student [sOpenid=" + sOpenid + ", sNumber=" + sNumber + ", sName=" + sName + ", sGender=" + sGender
				+ ", sMacAddress=" + sMacAddress + ", sSchool=" + sSchool + ", sClass=" + sClass + ", sState=" + sState
				+ ", dist=" + dist + "]";
	}
}
