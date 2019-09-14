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
	/** �ͻ������� */
	private JLabel labDesc;
	/** ������ip��ַ */
	private JLabel labIp;
	/** �������˿�lab */
	private JLabel labPort;
	/** ���Ӱ�ť */
	private JButton btnConn;
	/** �������˿� */
	private JTextField txtPort;
	/** ������ip */
	private JTextField txtIp;
	
	/** ������Ϣ�ı��� */
	private JTextField txtSend;
	/** ������Ϣ��ť */
	private JButton btnSend;
	/** ��ʾ���յ���Ϣ */
	private JTextArea txtMessage;
	
	
	private Socket socket;
	
	/**
	 * ����ͻ��˴���
	 */
	public Client(){
		init();
	}
	
	public void startReceive(){
		ClientReceiveThread clientReceiveThread=new ClientReceiveThread(this,socket);
		clientReceiveThread.start();
	}
	/**
	 * �����Ϸ�����
	 */
	private void connectServer(){
		String ip=this.txtIp.getText();
		int port=Integer.parseInt(this.txtPort.getText());
		this.btnConn.setEnabled(false);
		try {
			socket=new Socket(ip,port);
			startReceive();
		} catch (UnknownHostException e) {
			System.out.println("�Ҳ���������������������");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("���ӷ����������в���io�쳣������������������");
			e.printStackTrace();
		}
	}
	
	/**
	 * �������������Ϣ
	 */
	private void sendMessage(){
		try {
			OutputStream out=socket.getOutputStream();
			String message=this.txtSend.getText();
			out.write(message.getBytes());
		} catch (IOException e) {
			System.out.println("������Ϣ�����в���IO�쳣����������");
			e.printStackTrace();
		}
	}
	
	public void writeMessage(String str){
		this.txtMessage.setText(this.txtMessage.getText()+"\n"+str);
	}
	
	/**
	 * ��ʼ������
	 */
	private void init(){
		this.setTitle("�ͻ���");
		this.setSize(400,400);
		
		Container container=this.getContentPane();
		container.setLayout(new BorderLayout());
		
		JPanel pn=new JPanel();
		container.add(pn,BorderLayout.NORTH);
		
		labDesc=new JLabel("�ͻ���");
		labIp=new JLabel("IP:");
		labPort=new JLabel("�˿ںţ�");
		txtIp=new JTextField(10);
		txtIp.setText("127.0.0.1");
		txtPort=new JTextField(5);
		txtPort.setText("5555");
		btnConn=new JButton("����");
		
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
		btnSend=new JButton("����");
		pc.add(ps,BorderLayout.SOUTH);
		ps.add(txtSend);
		ps.add(btnSend);
		
		this.setVisible(true);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//���ӷ������¼�����
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
