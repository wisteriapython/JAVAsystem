package com.jzgx.app;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import com.jzgx.data.XmlUtil;


public class ConfigFrame extends JFrame{
	
	/* ����ѧ������ */
	public String stuName;
	
	public ConfigPanel configPanel;
	public HistoryPanel historyPanel;

	public ConfigFrame(String stuName){
		super();
		this.stuName=stuName;
		
		this.setSize(800,600);
		this.setResizable(false);
		this.setTitle("��ӭ "+stuName+" ʹ�ñ�����ϰϵͳ,���͡�����");
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
		//��ʼ������Դ
		XmlUtil.readCourse();
		XmlUtil.readExam();
		
		//��ʼ���������
		configPanel=new ConfigPanel(this);
		this.getContentPane().add(configPanel);
		
		//��ʼ����ʷ�ɼ����
		historyPanel=new HistoryPanel();
		this.getContentPane().add(historyPanel);
		
	}
}
