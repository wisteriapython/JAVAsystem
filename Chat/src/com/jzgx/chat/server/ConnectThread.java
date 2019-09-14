package com.jzgx.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;

public class ConnectThread extends Thread{
	
	private Server server;
	
	private ServerSocket serverSocket;
	
	private Socket socket;

	public ConnectThread(JFrame frame){
		this.server=(Server) frame;
		int port=Integer.parseInt(server.getTxtPort().getText());
		try {
			serverSocket=new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("�˿ڱ�ռ�á�������");
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(true){
			try {
				System.out.println("�ȴ��ͻ������ӡ���������");
				socket=serverSocket.accept();
				System.out.println("�ͻ������ӳɹ�,"+socket.getInetAddress().getHostAddress());
				ServerReceiveThread receiveThread=new ServerReceiveThread(this.server,socket);
				receiveThread.start();
				server.getMap().put("aaa", receiveThread);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
