package com.jzgx.app;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import com.jzgx.data.XmlUtil;


public class ConfigFrame extends JFrame{
	
	/* 测试学生姓名 */
	public String stuName;
	
	public ConfigPanel configPanel;
	public HistoryPanel historyPanel;

	public ConfigFrame(String stuName){
		super();
		this.stuName=stuName;
		
		this.setSize(800,600);
		this.setResizable(false);
		this.setTitle("欢迎 "+stuName+" 使用笔试练习系统,加油。。。");
		this.getContentPane().setLayout(null);
		
		init();
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		setLocationRelativeTo(null);
	}
	
	private void init(){
		//初始化数据源
		XmlUtil.readCourse();
		XmlUtil.readExam();
		
		//初始化配置面板
		configPanel=new ConfigPanel(this);
		this.getContentPane().add(configPanel);
		
		//初始化历史成绩面板
		historyPanel=new HistoryPanel();
		this.getContentPane().add(historyPanel);
		
	}
}
