package com.jzgx.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.jzgx.model.MessageContext;

public class Client extends JFrame {

	//定义客户端界面
	private JTextField msg;
	private JButton btn;
	private JTextArea txt;
	private JComboBox userList;
	
	private String userName;

	//定义连接用的套接字
	private Socket socket;
	//定义客户端接收消息的线程，和服务器端一样，不能确定什么时候接收到新消息，只能放到主线程之外
	private ClientReceiverThread clientReceiverThread;
	
	public Socket getSocket(){
		return socket;
	}
	
	/**
	 * 发送用户登录的用户名到服务器
	 * @param socket
	 */
	private void sendUserInfo(Socket socket){
		try {
			ObjectOutputStream out=new ObjectOutputStream(socket.getOutputStream());
			MessageContext messageContext=new MessageContext();
			messageContext.setType(2);
			messageContext.setContext(userName);
			out.writeObject(messageContext);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新用户列表
	 * @param user
	 */
	public void updateUserList(String[] user){
		//每次更新，先清除原来的combox中的用户，再重新增加进去
		if(null!=userList){
			this.userList.removeAllItems();
		}
		for(int i=0;i<user.length;i++){
			userList.addItem(user[i]);
		}
		this.userList.addItem("所有人");
	}
	
	public Client(String userName) {
		this.userName=userName;
		this.setTitle(userName);
		msg = new JTextField(20);
		btn = new JButton("发送");
		txt = new JTextArea();
		userList=new JComboBox();
		
		JScrollPane p=new JScrollPane();
		this.add(p, BorderLayout.CENTER);
		p.getViewport().add(txt);
		JPanel panel = new JPanel();
		this.add(panel, BorderLayout.SOUTH);
		panel.add(userList);
		panel.add(msg);
		panel.add(btn);
		
		this.setSize(500, 500);
		btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				sendMessage();
			}});
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				closeConnect();
			}
		});
		
		try {
			//连接服务器
			socket = new Socket("127.0.0.1", 4000);
			//发送用户名到服务器
			sendUserInfo(socket);
			//再启动接收线程
			clientReceiverThread = new ClientReceiverThread(this);
			clientReceiverThread.start();
		} catch(Exception e){
			JOptionPane.showMessageDialog(this, "服务器异常，连接不上");
			e.printStackTrace();
			System.exit(0);
		}
	}

	
	/**
	 * 获得登录的用户名
	 * @return
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * 当客户发送消息是调用这个方法
	 */
	public void sendMessage() {
		try {
			//构造对象输出流，发送的消息封装到MessageContext对象中再发送
			ObjectOutputStream out=new ObjectOutputStream(socket.getOutputStream());
			MessageContext messageContext=new MessageContext();
			messageContext.setFrom(userName);
			messageContext.setTo(userList.getSelectedItem().toString());
			messageContext.setType(0);
			messageContext.setContext(this.msg.getText());
			out.writeObject(messageContext);
			addMessage(messageContext.getFrom()+"对"+messageContext.getTo()+"说："+messageContext.getContext());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将接收到的消息添加到窗体的多行文本框中显示
	 * @param str
	 */
	public void addMessage(String str){
		String context=this.txt.getText();
		context=context+"\r\n"+str;
		this.txt.setText(context);
	}
	
	/**
	 * 关闭窗体是，要发送和服务器断开连接的消息请求，还要关闭消息接收线程
	 */
	public void closeConnect(){
		try {
			//先发送一个客户端下线的通知，断开与服务器的连接
			ObjectOutputStream out=new ObjectOutputStream(socket.getOutputStream());
			MessageContext message=new MessageContext();
			message.setType(4);
			message.setContext(userName+":"+socket.getLocalAddress().getHostAddress());
			out.writeObject(message);
			out.flush();
			clientReceiverThread.setFlag(false);
			clientReceiverThread=null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
