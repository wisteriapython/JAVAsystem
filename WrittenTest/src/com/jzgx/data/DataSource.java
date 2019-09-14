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

	//数据缓存实例用来存储科目、题库、测试结果
	private static DataSource dataSource;
	
	//科目
	private List<Course> courses;
	//题库
	private Map<String,List<Exam>> exams;
	
	//单次测试题目集合
	private List<Exam> testExams;
	//单次测试科目
	private String testCourse;
	//单次测试结果
	private TestResult testResult;
	
	//学生答题情况
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
	 * 添加题库内容
	 * @param course 所属科目
	 * @param exam	题目
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
	 * 返回数据源单例
	 * @return
	 */
	public static DataSource getInstance(){
		if(dataSource==null){
			dataSource=new DataSource();
		}
		return dataSource;
	}
}
