package com.jzgx.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import com.jzgx.app.TestFrame;
import com.jzgx.app.TestPanel;
import com.jzgx.data.DataSource;

public class BtnUpAction implements ActionListener{

	private int index;
	private TestFrame testFrame;
	
	public BtnUpAction(int index,JFrame frame){
		this.index=index;
		this.testFrame=(TestFrame) frame;
	}

	public void actionPerformed(ActionEvent arg0) {
		index--;
		if(index<0){
			index=0;
		}
		TestPanel panel=new TestPanel(index,testFrame);
		testFrame.jsplitPanle.setRightComponent(panel);
		testFrame.jsplitPanle.setDividerLocation(250);
	}
	
}
