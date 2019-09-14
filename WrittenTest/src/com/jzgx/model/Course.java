package com.jzgx.model;

public class Course {

	private String courseName;
	private String filePath;
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	@Override
	public String toString() {
		return "Course [courseName=" + courseName + ", filePath=" + filePath
				+ "]\n";
	}
	
}
