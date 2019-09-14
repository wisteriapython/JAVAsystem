package com.jzgx.app;

import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import com.jzgx.action.ConfigAction;
import com.jzgx.data.DataSource;
import com.jzgx.model.Course;

public class ConfigPanel extends JPanel{
	
	private JLabel lab1=new JLabel("选择测试的科目");
	public JList courseList=new JList();
	private JScrollPane jscrollPanel=new JScrollPane();
	private ConfigFrame configFrame;
	
	private JButton btn=new JButton("开始测试");
	
	public ConfigPanel(JFrame frame){
		configFrame=(ConfigFrame) frame;
		this.setLayout(null);
		this.setBorder(new TitledBorder("测试选项"));
		this.setBounds(10, 10, 775, 200);
		
		lab1.setBounds(10, 20, 200, 30);
		jscrollPanel.setBounds(10, 55, 200, 135);
		courseList.setBounds(10, 55, 200, 135);
		
		this.add(lab1);
		this.add(jscrollPanel);
		jscrollPanel.getViewport().add(courseList);
		
		List<Course> courses=DataSource.getInstance().getCourses();
		
		DefaultListModel dlm=new DefaultListModel();
		for(int i=0;i<courses.size();i++){
			Course c=courses.get(i);
			dlm.add(i, c.getCourseName());
		}
		courseList.setModel(dlm);
		
		btn.setBounds(230, 55, 530, 135);
		this.add(btn);
		
		btn.addActionListener(new ConfigAction(configFrame));
	}
}
