package com.jzgx.chat.client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client extends JFrame{
	/** 客户端描述 */
	private JLabel labDesc;
	/** 服务器ip地址 */
	private JLabel labIp;
	/** 服务器端口lab */
	private JLabel labPort;
	/** 连接按钮 */
	private JButton btnConn;
	/** 服务器端口 */
	private JTextField txtPort;
	/** 服务器ip */
	private JTextField txtIp;
	
	/** 发送消息文本框 */
	private JTextField txtSend;
	/** 发送消息按钮 */
	private JButton btnSend;
	/** 显示接收的消息 */
	private JTextArea txtMessage;
	
	
	private Socket socket;
	
	/**
	 * 构造客户端窗口
	 */
	public Client(){
		init();
	}
	
	public void startReceive(){
		ClientReceiveThread clientReceiveThread=new ClientReceiveThread(this,socket);
		clientReceiveThread.start();
	}
	/**
	 * 连接上服务器
	 */
	private void connectServer(){
		String ip=this.txtIp.getText();
		int port=Integer.parseInt(this.txtPort.getText());
		this.btnConn.setEnabled(false);
		try {
			socket=new Socket(ip,port);
			startReceive();
		} catch (UnknownHostException e) {
			System.out.println("找不到服务器。。。。。。");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("连接服务器过程中产生io异常。。。。。。。。。");
			e.printStackTrace();
		}
	}
	
	/**
	 * 向服务器发送消息
	 */
	private void sendMessage(){
		try {
			OutputStream out=socket.getOutputStream();
			String message=this.txtSend.getText();
			out.write(message.getBytes());
		} catch (IOException e) {
			System.out.println("发送消息过程中产生IO异常。。。。。");
			e.printStackTrace();
		}
	}
	
	public void writeMessage(String str){
		this.txtMessage.setText(this.txtMessage.getText()+"\n"+str);
	}
	
	/**
	 * 初始化界面
	 */
	private void init(){
		this.setTitle("客户端");
		this.setSize(400,400);
		
		Container container=this.getContentPane();
		container.setLayout(new BorderLayout());
		
		JPanel pn=new JPanel();
		container.add(pn,BorderLayout.NORTH);
		
		labDesc=new JLabel("客户端");
		labIp=new JLabel("IP:");
		labPort=new JLabel("端口号：");
		txtIp=new JTextField(10);
		txtIp.setText("127.0.0.1");
		txtPort=new JTextField(5);
		txtPort.setText("5555");
		btnConn=new JButton("连接");
		
		pn.add(labDesc);
		pn.add(labIp);
		pn.add(txtIp);
		pn.add(labPort);
		pn.add(txtPort);
		pn.add(btnConn);
		
		JPanel pc=new JPanel();
		container.add(pc,BorderLayout.CENTER);
		
		JScrollPane scorllPane=new JScrollPane();
		pc.setLayout(new BorderLayout());
		pc.add(scorllPane,BorderLayout.CENTER);
		
		txtMessage=new JTextArea();
		scorllPane.getViewport().add(txtMessage);
		JPanel ps=new JPanel();
		
		txtSend=new JTextField(20);
		btnSend=new JButton("发送");
		pc.add(ps,BorderLayout.SOUTH);
		ps.add(txtSend);
		ps.add(btnSend);
		
		this.setVisible(true);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//连接服务器事件处理
		this.btnConn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				connectServer();
			}
		});
		
		this.btnSend.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				sendMessage();
			}
		});
	}
	
	public static void main(String[] args) {
		Client client=new Client();
	}
}
