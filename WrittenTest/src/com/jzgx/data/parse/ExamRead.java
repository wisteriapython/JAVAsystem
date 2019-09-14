package com.jzgx.data.parse;

import org.dom4j.Element;

import com.jzgx.model.Exam;

public interface ExamRead {

	public Exam parseElement(Element element);
}
