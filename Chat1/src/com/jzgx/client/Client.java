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

	//����ͻ��˽���
	private JTextField msg;
	private JButton btn;
	private JTextArea txt;
	private JComboBox userList;
	
	private String userName;

	//���������õ��׽���
	private Socket socket;
	//����ͻ��˽�����Ϣ���̣߳��ͷ�������һ��������ȷ��ʲôʱ����յ�����Ϣ��ֻ�ܷŵ����߳�֮��
	private ClientReceiverThread clientReceiverThread;
	
	public Socket getSocket(){
		return socket;
	}
	
	/**
	 * �����û���¼���û�����������
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
	 * �����û��б�
	 * @param user
	 */
	public void updateUserList(String[] user){
		//ÿ�θ��£������ԭ����combox�е��û������������ӽ�ȥ
		if(null!=userList){
			this.userList.removeAllItems();
		}
		for(int i=0;i<user.length;i++){
			userList.addItem(user[i]);
		}
		this.userList.addItem("������");
	}
	
	public Client(String userName) {
		this.userName=userName;
		this.setTitle(userName);
		msg = new JTextField(20);
		btn = new JButton("����");
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
			//���ӷ�����
			socket = new Socket("127.0.0.1", 4000);
			//�����û�����������
			sendUserInfo(socket);
			//�����������߳�
			clientReceiverThread = new ClientReceiverThread(this);
			clientReceiverThread.start();
		} catch(Exception e){
			JOptionPane.showMessageDialog(this, "�������쳣�����Ӳ���");
			e.printStackTrace();
			System.exit(0);
		}
	}

	
	/**
	 * ��õ�¼���û���
	 * @return
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * ���ͻ�������Ϣ�ǵ����������
	 */
	public void sendMessage() {
		try {
			//�����������������͵���Ϣ��װ��MessageContext�������ٷ���
			ObjectOutputStream out=new ObjectOutputStream(socket.getOutputStream());
			MessageContext messageContext=new MessageContext();
			messageContext.setFrom(userName);
			messageContext.setTo(userList.getSelectedItem().toString());
			messageContext.setType(0);
			messageContext.setContext(this.msg.getText());
			out.writeObject(messageContext);
			addMessage(messageContext.getFrom()+"��"+messageContext.getTo()+"˵��"+messageContext.getContext());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �����յ�����Ϣ��ӵ�����Ķ����ı�������ʾ
	 * @param str
	 */
	public void addMessage(String str){
		String context=this.txt.getText();
		context=context+"\r\n"+str;
		this.txt.setText(context);
	}
	
	/**
	 * �رմ����ǣ�Ҫ���ͺͷ������Ͽ����ӵ���Ϣ���󣬻�Ҫ�ر���Ϣ�����߳�
	 */
	public void closeConnect(){
		try {
			//�ȷ���һ���ͻ������ߵ�֪ͨ���Ͽ��������������
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
