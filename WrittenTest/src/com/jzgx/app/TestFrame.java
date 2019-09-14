package com.jzgx.app;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import com.jzgx.data.XmlUtil;

public class TestFrame extends JFrame{
	
	public ExamPanel examPanel;
	public TestPanel testPanel;
	public JSplitPane jsplitPanle;
	public ConfigFrame configFrame;
	
	public ExamTestThread ett;
	
	public TestFrame(JFrame frame){
		configFrame=(ConfigFrame) frame;
		this.setSize(900,600);
		this.setResizable(false);
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				XmlUtil.saveStuScore();
				System.exit(0);
			}
		});
		
		setLocationRelativeTo(null);
		
		
		jsplitPanle=new JSplitPane();
		jsplitPanle.setDividerLocation(250);
		this.getContentPane().add(jsplitPanle,BorderLayout.CENTER);
		
		examPanel=new ExamPanel(this);
		JScrollPane jsp=new JScrollPane();
		jsplitPanle.add(jsp,JSplitPane.LEFT);
		jsp.getViewport().add(examPanel);
		
		testPanel=new TestPanel(0,this);
		jsplitPanle.add(testPanel,JSplitPane.RIGHT);
		
	}
	
	/**
	 * ∑µªÿ≈‰÷√≤‚ ‘¥∞ø⁄
	 */
	public void returnConfigFrame(){
		this.setVisible(false);
		configFrame.setVisible(true);
		XmlUtil.saveStuScore();
		this.ett.flag=false;
	}
	
	

}
