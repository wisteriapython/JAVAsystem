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
	
	//消息队列，服务器接收到客户端发送来的消息对象，先存在队列中，通过ServerSendThread消息发送线程发送
	public static List<MessageContext> queue=new LinkedList<MessageContext>();
	
	//界面上所需要的控件
	private JLabel label;
	private JList userList;
	private DefaultListModel dm;
	private JTextField msg;
	private JButton btn;

	//因为要不断接收客户端连接服务器，每一个服务器和客户端组成一对，
	//所以把等待和接收客户端连接单独做一个线程
	private ConnectThread connectThread;
	
	//服务器端和转发消息的线程，客户端发送消息到另一个客户端要经过服务器转发
	//服务器转发线程就是从queue队列中取出待转发的消息，发往真正的目的地
	private ServerSendThread serverSendThread;
	
	//管理客户端连接服务的连接和运行的消息接收线程，
	//因为消息通讯必须保持连接的存在,也就是socket对象一直是连着服务器与客户端
	//一个连接只能连一个服务器和一个客户端，这些对象在服务器中用集合来管理
	private Map<String,Connect> connMap=new HashMap<String,Connect>();
	private List<Connect> connList=new ArrayList<Connect>();
	
	//对外界提供访问管理连接的集合
	public Map<String,Connect> getConnMap(){
		return connMap;
	}
	public List<Connect> getConnList(){
		return connList;
	}
	
	/**
	 * 设置窗体界面用户数量提示信息，当有用户连上时改变显示
	 * 该方法在管理连接的线程中被调用，当一个用户连接上来或者断开连接，都需要重设窗体上的显示
	 * @param n
	 */
	public void setUserNum(int n){
		this.label.setText("当前有"+n+"个用户连接上服务器");
	}
	
	/**
	 * 用户连接成功，添加到用户列表
	 * 除了窗体上的显示，集合中管理的连接对象也需要添加到集合中
	 * 方便后面群发消息或者转发消息是使用
	 * @param connect
	 */
	public void addUserList(Connect connect){
		connList.add(connect);
		connMap.put(connect.getName()+":"+connect.getCip(), connect);
		dm.addElement(connect.getName()+":"+connect.getCip());
	}
	
	/**
	 * 用户退出聊天，断开连接，从用户列表中移除
	 * @param connect
	 */
	public void removeUserList(Connect connect){
		connList.remove(connect);
		connMap.remove(connect);
		dm.removeElement(connect.getName()+":"+connect.getCip());
	}
	
	
	/**
	 * 构造服务器对象
	 */
	public Server(){
		//设置界面
		this.setTitle("服务器");
		label=new JLabel();
		userList=new JList();
		dm=new DefaultListModel();
		userList.setModel(dm);
		msg=new JTextField(10);
		btn=new JButton("发送");
		
		this.add(label,BorderLayout.NORTH);
		JScrollPane sp=new JScrollPane();
		this.add(sp,BorderLayout.CENTER);
		sp.getViewport().add(userList);
		JPanel p=new JPanel();
		this.add(p,BorderLayout.SOUTH);
		p.add(msg);
		p.add(btn);
		
		this.setSize(300, 500);
		
		//启动管理连接的线程
		System.out.println("服务器启动....");
		connectThread=new ConnectThread(this);
		connectThread.start();
		
		//启动转发客户端消息的发送消息的线程
		serverSendThread=new ServerSendThread(this);
		serverSendThread.start();
		
		//注册窗体关闭，当关闭窗体时，需要将启动的线程全部停止，断开网络
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				for(Connect c:connList){
					//关闭所有的服务器接收线程
					c.getServerReceiverThread().setFlag(false);
				}
				//关闭管理连接的线程
				connectThread.setFlag(false);
				//关闭管理消息转发的线程
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
	 * 从服务器端向客户端群发消息
	 */
	public void sendMessage() {
		try {
			MessageContext messageContext=new MessageContext();
			messageContext.setFrom("系统广播");
			messageContext.setTo("你");
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
