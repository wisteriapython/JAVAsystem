package com.jzgx.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.jzgx.app.ConfigFrame;
import com.jzgx.app.ExamTestThread;
import com.jzgx.app.TestFrame;
import com.jzgx.data.DataSource;
import com.jzgx.model.Exam;
import com.jzgx.model.ExamTest;
import com.jzgx.model.TestResult;

public class ConfigAction implements ActionListener{
	private ConfigFrame configFrame;
	
	public ConfigAction(JFrame frame){
		configFrame=(ConfigFrame) frame;
	}

	public void actionPerformed(ActionEvent arg0) {
		Object[] values=configFrame.configPanel.courseList.getSelectedValues();
		List<Exam> testExamList=new ArrayList<Exam>();
		String testCourse="";
		if(values!=null&&values.length>0){
			for(int i=0;i<values.length;i++){
				List<Exam> exams=DataSource.getInstance().getExams().get(values[i]);
				testCourse+=values[i]+".";
				testExamList.addAll(exams);
			}
			
			//乱序
			List<Integer> index=this.getSort(testExamList.size());
			List<Exam> newExamList=new ArrayList<Exam>();
			for(int i=0;i<index.size();i++){
				newExamList.add(testExamList.get(index.get(i)));
			}
			DataSource.getInstance().setTestExams(newExamList);
			DataSource.getInstance().setTestCourse(testCourse);
			TestResult tr=new TestResult();
			tr.setTestStu(configFrame.stuName);
			tr.setTestCourse(testCourse);
			tr.setExamNum(newExamList.size());
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String testDate=sdf.format(new Date());
			tr.setTestDate(testDate);
			DataSource.getInstance().setTestResult(tr);
			
			Map<String,ExamTest> stuExamTestMap=new HashMap<String,ExamTest>();
			for(Exam exam:newExamList){
				ExamTest examTest=new ExamTest();
				examTest.setFlag(0);
				examTest.setStuAnswer("");
				examTest.setMsg("");
				examTest.setExam(exam);
				stuExamTestMap.put(exam.getExamId(), examTest);
			}
			
			DataSource.getInstance().setStuExamTestMap(stuExamTestMap);
			
			TestFrame testFrame=new TestFrame(configFrame);
			testFrame.setVisible(true);
			configFrame.setVisible(false);
			
			testFrame.ett=new ExamTestThread(testFrame);
			testFrame.ett.start();
		}else{
			JOptionPane.showMessageDialog(configFrame, "先选择测试的科目,然后开始测试！","操作提示",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private List<Integer> getSort(int size){
		List<Integer> index=new ArrayList<Integer>();
		Random rd=new Random();
		if(size>0){
			while(true){
				int t=(int) (rd.nextDouble()*size);
				if(!index.contains(t)){
					index.add(t);
				}
				if(index.size()==size){
					break;
				}
			}
		}
		return index;
	}
	
	/*public static void main(String[] args) {
		ConfigAction ca=new ConfigAction(null);
		List index=ca.getSort(53);
		System.out.println(index);
	}*/

}
