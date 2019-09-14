package com.jzgx.chat.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JFrame;

public class ServerReceiveThread extends Thread{
	
	private Server server;
	private Socket socket;

	public ServerReceiveThread(JFrame frame,Socket socket){
		this.server=(Server) frame;
		this.socket=socket;
	}
	
	@Override
	public void run() {
		while(true){
			try {
				InputStream in=socket.getInputStream();
				byte[] b=new byte[1024];
				int len=0;
				in.read(b);
				String message=new String(b);
				this.server.writeMessage(message);
				
			} catch (IOException e) {
				System.out.println("服务器断接收消息线程IO异常.......");
				e.printStackTrace();
			}
		}
	}
	
	public void sendMessage(String str){
		try {
			OutputStream out=socket.getOutputStream();
			out.write(str.getBytes());
		} catch (IOException e) {
			System.out.println("发送消息过程中产生IO异常。。。。。");
			e.printStackTrace();
		}
	}
}
