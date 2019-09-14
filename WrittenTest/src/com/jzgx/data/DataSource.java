package com.jzgx.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jzgx.model.Course;
import com.jzgx.model.Exam;
import com.jzgx.model.ExamTest;
import com.jzgx.model.TestResult;

public class DataSource {

	//���ݻ���ʵ�������洢��Ŀ����⡢���Խ��
	private static DataSource dataSource;
	
	//��Ŀ
	private List<Course> courses;
	//���
	private Map<String,List<Exam>> exams;
	
	//���β�����Ŀ����
	private List<Exam> testExams;
	//���β��Կ�Ŀ
	private String testCourse;
	//���β��Խ��
	private TestResult testResult;
	
	//ѧ���������
	private Map<String,ExamTest> stuExamTestMap;
	
	public TestResult getTestResult() {
		return testResult;
	}

	public void setTestResult(TestResult testResult) {
		this.testResult = testResult;
	}

	public String getTestCourse() {
		return testCourse;
	}

	public void setTestCourse(String testCourse) {
		this.testCourse = testCourse;
	}

	public Map<String, ExamTest> getStuExamTestMap() {
		return stuExamTestMap;
	}

	public void setStuExamTestMap(Map<String, ExamTest> stuExamTestMap) {
		this.stuExamTestMap = stuExamTestMap;
	}

	public List<Exam> getTestExams() {
		return testExams;
	}

	public void setTestExams(List<Exam> testExams) {
		this.testExams = testExams;
	}

	public Map<String, List<Exam>> getExams() {
		return exams;
	}

	public void setExams(Map<String, List<Exam>> exams) {
		this.exams = exams;
	}
	
	/**
	 * ����������
	 * @param course ������Ŀ
	 * @param exam	��Ŀ
	 */
	public void addExams(String course,Exam exam){
		List<Exam> list=exams.get(course);
		if(null==list){
			list=new ArrayList<Exam>();
			exams.put(course, list);
		}
		list.add(exam);
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	private DataSource(){
		courses=new ArrayList<Course>();
		exams=new HashMap<String, List<Exam>>();
	}
	
	/**
	 * ��������Դ����
	 * @return
	 */
	public static DataSource getInstance(){
		if(dataSource==null){
			dataSource=new DataSource();
		}
		return dataSource;
	}
}
