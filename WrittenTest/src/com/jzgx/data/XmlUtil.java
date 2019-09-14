package com.jzgx.data;

import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.jzgx.data.parse.ChoiceExamRead;
import com.jzgx.data.parse.ExamRead;
import com.jzgx.model.Course;
import com.jzgx.model.Exam;
import com.jzgx.model.TestResult;


public class XmlUtil {
	
	private static SAXReader reader=new SAXReader();
	
	/**
	 * 加载科目
	 */
	public static void readCourse(){
		try {
			
			Document doc=reader.read("doc/course.xml");
			List nodes=doc.selectNodes("courses/course");
			for(int i=0;i<nodes.size();i++){
				Element element=(Element) nodes.get(i);
				Course course=new Course();
				course.setCourseName(element.attributeValue("name"));
				course.setFilePath(element.attributeValue("file"));
				DataSource.getInstance().getCourses().add(course);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 加载题库
	 */
	public static void readExam(){
		List<Course> courses=DataSource.getInstance().getCourses();
		for(Course course:courses){
			try {
				String courseName=course.getCourseName();
				String filePath=course.getFilePath();
				if(!"".equals(filePath)){
					Document doc=reader.read("doc/"+course.getFilePath());
					List nodes=doc.selectNodes("root/exam");
					for(int i=0;i<nodes.size();i++){
						Element element=(Element) nodes.get(i);
						ExamRead choiceParse=new ChoiceExamRead();
						Exam exam=choiceParse.parseElement(element);
						//System.out.println(exam);
						DataSource.getInstance().addExams(courseName, exam);
					}
				}else{
					DataSource.getInstance().getExams().put(courseName, new ArrayList<Exam>());
				}
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		//System.out.println(DataSource.getInstance().getExams());
	}
	
	public static void saveStuScore(){
		TestResult tr=DataSource.getInstance().getTestResult();
		
		Document doc=null;
		Element rootElement=null;
		
		SAXReader saxReader=new SAXReader();
		try {
			doc=saxReader.read("doc/record.xml");
			rootElement=doc.getRootElement();
		} catch (DocumentException e) {
			doc=DocumentHelper.createDocument();
			rootElement=DocumentHelper.createElement("record");
			doc.add(rootElement);
		}
		Element element=DocumentHelper.createElement("TestResult");
		rootElement.add(element);
		
		Attribute attriStuName=DocumentHelper.createAttribute(element, "stuName", tr.getTestStu());
		element.add(attriStuName);
		
		Attribute attriTestCourse=DocumentHelper.createAttribute(element, "testCourse", tr.getTestCourse());
		element.add(attriTestCourse);
		
		Attribute attriTestDate=DocumentHelper.createAttribute(element, "testDate", tr.getTestDate());
		element.add(attriTestDate);
		
		Attribute attriUseTime=DocumentHelper.createAttribute(element, "useTime", tr.getUseTime());
		element.add(attriUseTime);
		
		Attribute attriExamNum=DocumentHelper.createAttribute(element, "examNum", tr.getExamNum()+"");
		element.add(attriExamNum);
		
		Attribute attriSuccPercent=DocumentHelper.createAttribute(element, "succPercent", tr.getSuccPercent());
		element.add(attriSuccPercent);
		
		Attribute attriOkPercent=DocumentHelper.createAttribute(element, "okPercent", tr.getOkPercent());
		element.add(attriOkPercent);
		try {
			Writer xmlFile=null;
			xmlFile = new FileWriter("doc/record.xml");
			OutputFormat format= OutputFormat.createPrettyPrint();
			format.setEncoding("GBK");

			XMLWriter xmlout=new XMLWriter(xmlFile,format);
			xmlout.write(doc);
			xmlout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static List<TestResult> readStuScore(){
		List<TestResult> list=new ArrayList<TestResult>();
		SAXReader saxReader=new SAXReader();
		try {
			Document doc=saxReader.read("doc/record.xml");
			Element rootElement=doc.getRootElement();
			List<Element> childs=rootElement.elements();
			for(Element child:childs){
				TestResult tr=new TestResult();
				tr.setTestStu(child.attributeValue("stuName"));
				tr.setTestCourse(child.attributeValue("testCourse"));
				tr.setTestDate(child.attributeValue("testDate"));
				tr.setUseTime(child.attributeValue("useTime"));
				tr.setExamNum(Integer.parseInt(child.attributeValue("examNum")));
				tr.setSuccPercent(child.attributeValue("succPercent"));
				tr.setOkPercent(child.attributeValue("okPercent"));
				list.add(tr);
			}
		} catch (DocumentException e) {
			System.out.println("暂时没有记录。。。");
		}
		return list;
		
	}
}
