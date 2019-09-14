package com.jzgx.chat.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import javax.swing.JFrame;

public class ClientReceiveThread extends Thread{
	private Client client;
	private Socket socket;

	public ClientReceiveThread(JFrame frame,Socket socket){
		this.client=(Client) frame;
		this.socket=socket;
	}
	
	@Override
	public void run() {
		while(true){
			try {
				InputStream in=socket.getInputStream();
				byte[] b=new byte[1024];
				in.read(b);
				String str=new String(b);
				client.writeMessage(str);
			} catch (IOException e) {
				System.out.println("客户端接收线程产生IO异常。。。。。");
				e.printStackTrace();
			}
		}
	}
}
