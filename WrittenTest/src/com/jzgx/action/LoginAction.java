package com.jzgx.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.jzgx.app.ConfigFrame;
import com.jzgx.app.LoginFrame;

public class LoginAction implements ActionListener{
	
	private LoginFrame frame;
	public LoginAction(JFrame frame){
		this.frame=(LoginFrame) frame;
	}

	public void actionPerformed(ActionEvent e) {
		String stuName=frame.txtUser.getText();
		if(null!=stuName&&!"".equals(stuName.trim())){
			ConfigFrame configFrame=new ConfigFrame(stuName);
			configFrame.setVisible(true);
			this.frame.setVisible(false);
		}else{
			JOptionPane.showMessageDialog(frame, "������μӲ���ѧ������", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}

}
