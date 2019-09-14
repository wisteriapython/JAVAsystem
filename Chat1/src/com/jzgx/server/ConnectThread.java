package com.jzgx.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import com.jzgx.model.MessageContext;

/**
 * 管理连接的线程，每当有客户端请求连接时就会开启一个Socket,并方在Server的集合中
 * @author liyun
 *
 */
public class ConnectThread extends Thread {

	private ServerSocket ss;
	private Server server;
	private boolean flag=true;
	
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public ConnectThread(Server server) {
		this.server = server;
		try {
			ss = new ServerSocket(4000);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (flag) {
			try {
				//等待客户端连接
				Socket socket = ss.accept();
				System.out.println(socket.getInetAddress().getHostAddress()+ "客户端连接成功");
				ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
				MessageContext message=(MessageContext) in.readObject();
				
				//记录客户端登录的信息
				Connect connect = new Connect();
				connect.setCip(socket.getInetAddress().getHostAddress());
				connect.setSocket(socket);
				if(message.getType()==2){
					connect.setName(message.getContext().toString());
				}
				server.addUserList(connect);
				server.setUserNum(server.getConnList().size());
				//一旦连接上，启动一个接收线程，监听来自客户端的消息
				ServerReceiverThread serverReceiverThread=new ServerReceiverThread(server,socket);
				serverReceiverThread.start();
				connect.setServerReceiverThread(serverReceiverThread);
				
				//重新发送用户列表
				sendUserList2Client(server.getConnList());
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 每一个客户端登录或断开与服务器的连接，都需要向重新所有客户端发送当前在线的用户
	 * @param list
	 */
	private void sendUserList2Client(List<Connect> list){
		if(list==null) return;
		try {
			//拼接发送的列表，按一定规则将列表转成字符串在发送
			String userStr="";
			for(Connect conn:list){
				userStr+=conn.getName()+":"+conn.getCip()+",";			
			}
			userStr=userStr.substring(0,userStr.length()-1);
			//构造消息对象，类型3表示是服务器向客户端发送新的用户列表信息
			MessageContext message=new MessageContext();
			message.setType(3);
			message.setContext(userStr);
			//给每个在线的客户端都发送
			for(Connect conn:list){
				Socket socket=conn.getSocket();
				//只给连接在线的发，不在线的是发不了的
				if(!socket.isClosed()){
					ObjectOutputStream out=new ObjectOutputStream(socket.getOutputStream());
					out.writeObject(message);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
