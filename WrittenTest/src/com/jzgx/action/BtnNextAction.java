package com.jzgx.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import com.jzgx.app.TestFrame;
import com.jzgx.app.TestPanel;
import com.jzgx.data.DataSource;

public class BtnNextAction implements ActionListener{

	private int index;
	private TestFrame testFrame;
	public BtnNextAction(int index,JFrame frame){
		this.index=index;
		testFrame=(TestFrame) frame;
	}
	public void actionPerformed(ActionEvent arg0) {
		index++;
		if(index>=DataSource.getInstance().getTestExams().size()){
			index=DataSource.getInstance().getTestExams().size()-1;
		}
		TestPanel panel=new TestPanel(index,testFrame);
		testFrame.jsplitPanle.setRightComponent(panel);
		testFrame.jsplitPanle.setDividerLocation(250);
		
	}

}
