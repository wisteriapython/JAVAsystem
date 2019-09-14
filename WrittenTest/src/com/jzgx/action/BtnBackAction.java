package com.jzgx.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import com.jzgx.app.TestFrame;

public class BtnBackAction implements ActionListener{
	private TestFrame testFrame;
	
	public BtnBackAction(JFrame frame){
		this.testFrame=(TestFrame) frame;
	}

	public void actionPerformed(ActionEvent arg0) {
		this.testFrame.returnConfigFrame();
	}

}
