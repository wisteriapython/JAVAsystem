package com.jzgx.model;

public class TestResult {
	private String testStu;		//����ѧ��
	private String testCourse;	//���Կ�Ŀ
	private int examNum;		//������Ŀ����
	private int succNum;		//�������
	private int okNum;			//��ȷ����
	private int errorNum;		//��������
	private String succPercent="0";	//��ȷ�ٷֱ�
	private String okPercent="0";		//����ٷֱ�
	
	private String testDate;		//��������
	private String useTime;			//��ʱ������
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
