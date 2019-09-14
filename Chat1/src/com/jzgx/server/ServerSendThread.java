package com.jzgx.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.jzgx.model.MessageContext;

/**
 * 消息转发线程，转发客户端发来的消息
 * @author liyun
 *
 */
public class ServerSendThread extends Thread{
	
	private Server server;
	private boolean flag=true;
	
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	public ServerSendThread(Server server){
		this.server=server;
	}
	
	public void run(){
		try {
			while(flag){
				if(server.queue.size()>0){
					MessageContext message=server.queue.get(0);
					String key=message.getTo();
					if("所有人".equals(key)){
						for(Connect conn:server.getConnList()){
							sendMessage(conn.getSocket(),message);
						}
					}else{
						Connect conn=server.getConnMap().get(key);
						sendMessage(conn.getSocket(),message);
					}
					server.queue.remove(message);
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void sendMessage(Socket socket,MessageContext message) throws IOException{
		if(!socket.isClosed()){
			ObjectOutputStream out=new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(message);
		}
	}

}
