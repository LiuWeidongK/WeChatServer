package com.royal.entity;

import java.sql.Timestamp;
import java.util.List;

public class Logging {
	private String logUuid;
	private Timestamp optionTime;
	private Integer lId;
	private Integer sNumber;
	private Short logState;
	private Short sState;
	private Double latitude;
	private Double longitude;
	private Double altitude;
	private Double dist;
	private Integer allStudents;
	private Integer notComeStudents;
	private List<String> transDate;
	public String getLogUuid() {
		return logUuid;
	}
	public void setLogUuid(String logUuid) {
		this.logUuid = logUuid;
	}
	public Timestamp getOptionTime() {
		return optionTime;
	}
	public void setOptionTime(Timestamp optionTime) {
		this.optionTime = optionTime;
	}
	public Integer getlId() {
		return lId;
	}
	public void setlId(Integer lId) {
		this.lId = lId;
	}
	public Integer getsNumber() {
		return sNumber;
	}
	public void setsNumber(Integer sNumber) {
		this.sNumber = sNumber;
	}
	public Short getLogState() {
		return logState;
	}
	public void setLogState(Short logState) {
		this.logState = logState;
	}
	public Short getsState() {
		return sState;
	}
	public void setsState(Short sState) {
		this.sState = sState;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getAltitude() {
		return altitude;
	}
	public void setAltitude(Double altitude) {
		this.altitude = altitude;
	}
	public Double getDist() {
		return dist;
	}
	public void setDist(Double dist) {
		this.dist = dist;
	}
	public Integer getAllStudents() {
		return allStudents;
	}
	public void setAllStudents(Integer allStudents) {
		this.allStudents = allStudents;
	}
	public Integer getNotComeStudents() {
		return notComeStudents;
	}
	public void setNotComeStudents(Integer notComeStudents) {
		this.notComeStudents = notComeStudents;
	}
	public List<String> getTransDate() {
		return transDate;
	}
	public void setTransDate(List<String> transDate) {
		this.transDate = transDate;
	}
	@Override
	public String toString() {
		return "Logging [logUuid=" + logUuid + ", optionTime=" + optionTime + ", lId=" + lId + ", sNumber=" + sNumber
				+ ", logState=" + logState + ", sState=" + sState + ", latitude=" + latitude + ", longitude="
				+ longitude + ", altitude=" + altitude + ", dist=" + dist + ", allStudents=" + allStudents
				+ ", notComeStudents=" + notComeStudents + ", transDate=" + transDate + "]";
	}
}
