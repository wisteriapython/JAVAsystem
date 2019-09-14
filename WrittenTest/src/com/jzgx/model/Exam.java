package com.jzgx.model;

import java.util.List;

public class Exam {

	private String examId;
	private String question;
	private List<String> values;
	private List<String> options;
	private String answer;
	private String desc;
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public List<String> getValues() {
		return values;
	}
	public void setValues(List<String> values) {
		this.values = values;
	}
	public List<String> getOptions() {
		return options;
	}
	public void setOptions(List<String> options) {
		this.options = options;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	@Override
	public String toString() {
		return "Exam [answer=" + answer + ", desc=" + desc + ", examId="
				+ examId + ", options=" + options + ", question=" + question
				+ ", values=" + values + "]\n";
	}
	
}
