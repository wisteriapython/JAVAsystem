package com.jzgx.server;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.jzgx.model.MessageContext;

public class Server extends JFrame{
	
	//��Ϣ���У����������յ��ͻ��˷���������Ϣ�����ȴ��ڶ����У�ͨ��ServerSendThread��Ϣ�����̷߳���
	public static List<MessageContext> queue=new LinkedList<MessageContext>();
	
	//����������Ҫ�Ŀؼ�
	private JLabel label;
	private JList userList;
	private DefaultListModel dm;
	private JTextField msg;
	private JButton btn;

	//��ΪҪ���Ͻ��տͻ������ӷ�������ÿһ���������Ϳͻ������һ�ԣ�
	//���԰ѵȴ��ͽ��տͻ������ӵ�����һ���߳�
	private ConnectThread connectThread;
	
	//�������˺�ת����Ϣ���̣߳��ͻ��˷�����Ϣ����һ���ͻ���Ҫ����������ת��
	//������ת���߳̾��Ǵ�queue������ȡ����ת������Ϣ������������Ŀ�ĵ�
	private ServerSendThread serverSendThread;
	
	//����ͻ������ӷ�������Ӻ����е���Ϣ�����̣߳�
	//��Ϊ��ϢͨѶ���뱣�����ӵĴ���,Ҳ����socket����һֱ�����ŷ�������ͻ���
	//һ������ֻ����һ����������һ���ͻ��ˣ���Щ�����ڷ��������ü���������
	private Map<String,Connect> connMap=new HashMap<String,Connect>();
	private List<Connect> connList=new ArrayList<Connect>();
	
	//������ṩ���ʹ������ӵļ���
	public Map<String,Connect> getConnMap(){
		return connMap;
	}
	public List<Connect> getConnList(){
		return connList;
	}
	
	/**
	 * ���ô�������û�������ʾ��Ϣ�������û�����ʱ�ı���ʾ
	 * �÷����ڹ������ӵ��߳��б����ã���һ���û������������߶Ͽ����ӣ�����Ҫ���贰���ϵ���ʾ
	 * @param n
	 */
	public void setUserNum(int n){
		this.label.setText("��ǰ��"+n+"���û������Ϸ�����");
	}
	
	/**
	 * �û����ӳɹ�����ӵ��û��б�
	 * ���˴����ϵ���ʾ�������й�������Ӷ���Ҳ��Ҫ��ӵ�������
	 * �������Ⱥ����Ϣ����ת����Ϣ��ʹ��
	 * @param connect
	 */
	public void addUserList(Connect connect){
		connList.add(connect);
		connMap.put(connect.getName()+":"+connect.getCip(), connect);
		dm.addElement(connect.getName()+":"+connect.getCip());
	}
	
	/**
	 * �û��˳����죬�Ͽ����ӣ����û��б����Ƴ�
	 * @param connect
	 */
	public void removeUserList(Connect connect){
		connList.remove(connect);
		connMap.remove(connect);
		dm.removeElement(connect.getName()+":"+connect.getCip());
	}
	
	
	/**
	 * �������������
	 */
	public Server(){
		//���ý���
		this.setTitle("������");
		label=new JLabel();
		userList=new JList();
		dm=new DefaultListModel();
		userList.setModel(dm);
		msg=new JTextField(10);
		btn=new JButton("����");
		
		this.add(label,BorderLayout.NORTH);
		JScrollPane sp=new JScrollPane();
		this.add(sp,BorderLayout.CENTER);
		sp.getViewport().add(userList);
		JPanel p=new JPanel();
		this.add(p,BorderLayout.SOUTH);
		p.add(msg);
		p.add(btn);
		
		this.setSize(300, 500);
		
		//�����������ӵ��߳�
		System.out.println("����������....");
		connectThread=new ConnectThread(this);
		connectThread.start();
		
		//����ת���ͻ�����Ϣ�ķ�����Ϣ���߳�
		serverSendThread=new ServerSendThread(this);
		serverSendThread.start();
		
		//ע�ᴰ��رգ����رմ���ʱ����Ҫ���������߳�ȫ��ֹͣ���Ͽ�����
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				for(Connect c:connList){
					//�ر����еķ����������߳�
					c.getServerReceiverThread().setFlag(false);
				}
				//�رչ������ӵ��߳�
				connectThread.setFlag(false);
				//�رչ�����Ϣת�����߳�
				serverSendThread.setFlag(false);
				System.exit(0);
			}
		});
		
		btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				sendMessage();
			}
		});
	}
	
	public static void main(String[] args) {
		Server s=new Server();
		s.setVisible(true);
	}
	
	/**
	 * �ӷ���������ͻ���Ⱥ����Ϣ
	 */
	public void sendMessage() {
		try {
			MessageContext messageContext=new MessageContext();
			messageContext.setFrom("ϵͳ�㲥");
			messageContext.setTo("��");
			messageContext.setType(0);
			messageContext.setContext(this.msg.getText());
			for(Connect conn:connList){
				ObjectOutputStream out=new ObjectOutputStream(conn.getSocket().getOutputStream());
				out.writeObject(messageContext);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
