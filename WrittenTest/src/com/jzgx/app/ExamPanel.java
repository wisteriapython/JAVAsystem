package com.jzgx.app;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jzgx.action.SelectExamAction;
import com.jzgx.data.DataSource;
import com.jzgx.model.Exam;

public class ExamPanel extends JPanel{
	
	private TestFrame testFrame;

	public JButton[] examBtn;
	private List<Exam> testExamList;
	
	public ExamPanel(JFrame frame){
		this.testFrame=(TestFrame) frame;
		testExamList=DataSource.getInstance().getTestExams();
		int rows=0;
		if(testExamList.size()/3==0){
			rows=testExamList.size()/3;
		}else{
			rows=testExamList.size()/3+1;
		}
		this.setLayout(new GridLayout(rows,3));
		examBtn=new JButton[testExamList.size()];
		for(int i=0;i<testExamList.size();i++){
			Exam exam=(Exam) testExamList.get(i);
			examBtn[i]=new JButton(exam.getExamId());
			examBtn[i].addActionListener(new SelectExamAction(testFrame));
			this.add(examBtn[i]);
		}
	}
}
