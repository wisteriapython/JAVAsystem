package com.jzgx.chat.server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Server extends JFrame{
	
	/** 服务器描述 */
	private JLabel labDesc;
	/** 服务器状态lab */
	private JLabel labStatus;
	/** 服务器端口lab */
	private JLabel labPort;
	/** 启动服务器按钮 */
	private JButton btnStart;
	/** 服务器端口 */
	private JTextField txtPort;
	
	/** 发送消息文本框 */
	private JTextField txtSend;
	/** 发送消息按钮 */
	private JButton btnSend;
	/** 显示接收的消息 */
	private JTextArea txtMessage;
	
	private Map map=new HashMap();
	
	public Map getMap() {
		return map;
	}

	/**
	 * 构造服务器窗口
	 */
	public Server(){
		init();
	}
	
	/**
	 * 初始化界面
	 */
	private void init(){
		this.setTitle("服务器");
		this.setSize(400,400);
		
		Container container=this.getContentPane();
		container.setLayout(new BorderLayout());
		
		JPanel pn=new JPanel();
		container.add(pn,BorderLayout.NORTH);
		
		labDesc=new JLabel("服务器");
		labStatus=new JLabel("停止");
		labStatus.setForeground(Color.red);
		labPort=new JLabel("端口号：");
		txtPort=new JTextField(5);
		txtPort.setText("5555");
		btnStart=new JButton("启动");
		
		pn.add(labDesc);
		pn.add(labStatus);
		pn.add(labPort);
		pn.add(txtPort);
		pn.add(btnStart);
		
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
		
		btnStart.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//启动服务器连接的线程，等待客户端连接
				startConnectThread();
			}});
		
		btnSend.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//从服务器发送消息到客户端
				sendMessage();
			}});
		
	}
	
	private void sendMessage(){
		ServerReceiveThread receiveThread=(ServerReceiveThread) map.get("aaa");
		receiveThread.sendMessage(this.txtSend.getText());
	}
	
	public void writeMessage(String str){
		this.txtMessage.setText(this.txtMessage.getText()+"\n"+str);
	}
	
	/**
	 * 启动服务器连接等待的线程
	 */
	private void startConnectThread(){
		this.btnStart.setEnabled(false);
		this.labStatus.setText("运行");
		ConnectThread connectThread=new ConnectThread(Server.this);
		connectThread.start();
	}
	
	/**
	 * 返回服务器端口
	 * @return
	 */
	public JTextField getTxtPort() {
		return txtPort;
	}
	
	public static void main(String[] args) {
		Server server=new Server();
	}
}
