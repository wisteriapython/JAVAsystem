package com.jzgx.app;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.jzgx.action.LoginAction;

public class LoginFrame extends JFrame{
	
	private JLabel labUser=new JLabel("学生姓名");
	public JTextField txtUser=new JTextField();
	private JButton btnLogin=new JButton("进入系统");

	public LoginFrame(){
		
		this.setTitle("国信笔试选择题测试练习系统");
		this.setSize(330,160);
		this.setResizable(false);
		this.getContentPane().setLayout(null);
		labUser.setBounds(20, 20, 80, 30);
		txtUser.setBounds(90, 20, 200, 30);
		btnLogin.setBounds(20, 60, 270, 40);
		
		this.getContentPane().add(labUser);
		this.getContentPane().add(txtUser);
		this.getContentPane().add(btnLogin);
		
		btnLogin.addActionListener(new LoginAction(this));
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {
		LoginFrame app=new LoginFrame();
		app.setVisible(true);
	}
}
