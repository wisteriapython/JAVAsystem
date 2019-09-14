package com.jzgx.model;

public class TestResult {
	private String testStu;		//测试学生
	private String testCourse;	//测试科目
	private int examNum;		//测试题目数量
	private int succNum;		//完成数量
	private int okNum;			//正确数量
	private int errorNum;		//错误数量
	private String succPercent="0";	//正确百分比
	private String okPercent="0";		//错误百分比
	
	private String testDate;		//测试日期
	private String useTime;			//用时多少秒
	public int getExamNum() {
		return examNum;
	}
	public String getTestStu() {
		return testStu;
	}
	public void setTestStu(String testStu) {
		this.testStu = testStu;
	}
	public String getTestCourse() {
		return testCourse;
	}
	public void setTestCourse(String testCourse) {
		this.testCourse = testCourse;
	}
	public void setExamNum(int examNum) {
		this.examNum = examNum;
	}
	public int getSuccNum() {
		return succNum;
	}
	public void setSuccNum(int succNum) {
		this.succNum = succNum;
	}
	public int getOkNum() {
		return okNum;
	}
	public void setOkNum(int okNum) {
		this.okNum = okNum;
	}
	public int getErrorNum() {
		return errorNum;
	}
	public void setErrorNum(int errorNum) {
		this.errorNum = errorNum;
	}
	public String getSuccPercent() {
		return succPercent;
	}
	public void setSuccPercent(String succPercent) {
		this.succPercent = succPercent;
	}
	public String getOkPercent() {
		return okPercent;
	}
	public void setOkPercent(String okPercent) {
		this.okPercent = okPercent;
	}
	public String getTestDate() {
		return testDate;
	}
	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}
	public String getUseTime() {
		return useTime;
	}
	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}
}
