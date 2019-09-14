package com.jzgx.data.parse;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import com.jzgx.model.Exam;

public class ChoiceExamRead implements ExamRead {

	public Exam parseElement(Element element) {
		String examId=element.attributeValue("id");
		String question=element.elementText("question");
		String answer=element.elementText("answer");
		String desc=element.elementText("desc");
		
		List childNodes=element.selectNodes("choice/option");
		List options=new ArrayList();
		List values=new ArrayList();
		if(null!=childNodes){
			for(int i=0;i<childNodes.size();i++){
				Element child=(Element) childNodes.get(i);
				options.add(child.getText());
				values.add(child.attributeValue("value"));
			}
		}
		
		Exam exam=new Exam();
		exam.setExamId(examId);
		exam.setQuestion(question);
		exam.setAnswer(answer);
		exam.setDesc(desc);
		exam.setOptions(options);
		exam.setValues(values);
		return exam;
	}

}
