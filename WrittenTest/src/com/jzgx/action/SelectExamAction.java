package com.jzgx.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.jzgx.app.TestFrame;
import com.jzgx.app.TestPanel;
import com.jzgx.data.DataSource;
import com.jzgx.model.Exam;

public class SelectExamAction implements ActionListener{
	
	private TestFrame testFrame;
	public SelectExamAction(JFrame frame){
		testFrame=(TestFrame) frame;
	}

	public void actionPerformed(ActionEvent event) {
		JButton btn=(JButton) event.getSource();
		String examId=btn.getText();
		int index=0;
		List<Exam> exams=DataSource.getInstance().getTestExams();
		for(int i=0;i<exams.size();i++){
			Exam e=exams.get(i);
			if(e.getExamId().equals(examId)){
				index=i;
				break;
			}
		}
		TestPanel panel=new TestPanel(index,testFrame);
		testFrame.jsplitPanle.setRightComponent(panel);
		testFrame.jsplitPanle.setDividerLocation(250);
	}

}
