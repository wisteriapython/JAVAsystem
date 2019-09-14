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
	
	/** ���������� */
	private JLabel labDesc;
	/** ������״̬lab */
	private JLabel labStatus;
	/** �������˿�lab */
	private JLabel labPort;
	/** ������������ť */
	private JButton btnStart;
	/** �������˿� */
	private JTextField txtPort;
	
	/** ������Ϣ�ı��� */
	private JTextField txtSend;
	/** ������Ϣ��ť */
	private JButton btnSend;
	/** ��ʾ���յ���Ϣ */
	private JTextArea txtMessage;
	
	private Map map=new HashMap();
	
	public Map getMap() {
		return map;
	}

	/**
	 * �������������
	 */
	public Server(){
		init();
	}
	
	/**
	 * ��ʼ������
	 */
	private void init(){
		this.setTitle("������");
		this.setSize(400,400);
		
		Container container=this.getContentPane();
		container.setLayout(new BorderLayout());
		
		JPanel pn=new JPanel();
		container.add(pn,BorderLayout.NORTH);
		
		labDesc=new JLabel("������");
		labStatus=new JLabel("ֹͣ");
		labStatus.setForeground(Color.red);
		labPort=new JLabel("�˿ںţ�");
		txtPort=new JTextField(5);
		txtPort.setText("5555");
		btnStart=new JButton("����");
		
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
		btnSend=new JButton("����");
		pc.add(ps,BorderLayout.SOUTH);
		ps.add(txtSend);
		ps.add(btnSend);
		
		this.setVisible(true);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		btnStart.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//�������������ӵ��̣߳��ȴ��ͻ�������
				startConnectThread();
			}});
		
		btnSend.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//�ӷ�����������Ϣ���ͻ���
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
	 * �������������ӵȴ����߳�
	 */
	private void startConnectThread(){
		this.btnStart.setEnabled(false);
		this.labStatus.setText("����");
		ConnectThread connectThread=new ConnectThread(Server.this);
		connectThread.start();
	}
	
	/**
	 * ���ط������˿�
	 * @return
	 */
	public JTextField getTxtPort() {
		return txtPort;
	}
	
	public static void main(String[] args) {
		Server server=new Server();
	}
}
