package com.jzgx.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;

/**
 * 客户端登录，输入用户名
 * @author liyun
 *
 */
public class ClientLogin extends JDialog{

	private JTextField userName;
	private JButton loginBtn;
	
	public ClientLogin(){
		this.setTitle("客户端登录");
		userName=new JTextField();
		loginBtn=new JButton("登录");
		this.setLayout(null);
		userName.setBounds(10, 10, 200, 30);
		loginBtn.setBounds(10, 40, 200, 30);
		this.add(userName);
		this.add(loginBtn);
		
		this.setSize(250, 150);
		this.setVisible(true);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		
		loginBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				login();
			}});
	}
	
	public void login(){
		Client client=new Client(this.userName.getText());
		client.setVisible(true);
		this.dispose();
	}
	
	public static void main(String[] args) {
		ClientLogin login=new ClientLogin();
	}
}
