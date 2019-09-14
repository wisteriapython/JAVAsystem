package com.jzgx.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import com.jzgx.model.MessageContext;

/**
 * 服务器端处理消息接收的线程，每一个连接都有一个等待接收用户的输入接收线程
 * 因为不能确定客户端何时会发送消息过来，只能在主线程之外去接收
 * @author liyun
 *
 */
public class ServerReceiverThread extends Thread{
	//服务器对象的引用
	private Server server;
	private Socket socket;
	private boolean flag=true;
	
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	/**
	 * 通过构造将服务器对象传递进来，就可以在线程中使用服务器对象的所有属性
	 * 比如放在服务器对象中的集合
	 * @param server
	 * @param socket
	 */
	public ServerReceiverThread(Server server,Socket socket){
		this.server=server;
		this.socket=socket;
	}

	//接收客户端发来的消息，消息是封装过的MessageContext,包含了发送者，接收者，内容格式、内容
	public void run(){
		while(flag){
			try {
				ObjectInputStream in=new ObjectInputStream(this.socket.getInputStream());
				MessageContext message=(MessageContext) in.readObject();
				//0是普通文字消息，1.是文件，暂未处理，2.接收用户登录,3.更新客户端用户列表,4:用户断开连接的消息
				if(message.getType()==0){
					System.out.println(message.getFrom()+"对"+message.getTo()+"说："+message.getContext());
					server.queue.add(message);
				}
				if(message.getType()==4){
					Connect conn=server.getConnMap().get(message.getContext().toString());
					server.removeUserList(conn);
					server.setUserNum(server.getConnList().size());
					flag=false;
					System.out.println(conn.getName()+":"+conn.getCip()+"断开连接");
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
