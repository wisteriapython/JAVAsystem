package com.jzgx.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import com.jzgx.model.MessageContext;

/**
 * �������ӵ��̣߳�ÿ���пͻ�����������ʱ�ͻῪ��һ��Socket,������Server�ļ�����
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
				//�ȴ��ͻ�������
				Socket socket = ss.accept();
				System.out.println(socket.getInetAddress().getHostAddress()+ "�ͻ������ӳɹ�");
				ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
				MessageContext message=(MessageContext) in.readObject();
				
				//��¼�ͻ��˵�¼����Ϣ
				Connect connect = new Connect();
				connect.setCip(socket.getInetAddress().getHostAddress());
				connect.setSocket(socket);
				if(message.getType()==2){
					connect.setName(message.getContext().toString());
				}
				server.addUserList(connect);
				server.setUserNum(server.getConnList().size());
				//һ�������ϣ�����һ�������̣߳��������Կͻ��˵���Ϣ
				ServerReceiverThread serverReceiverThread=new ServerReceiverThread(server,socket);
				serverReceiverThread.start();
				connect.setServerReceiverThread(serverReceiverThread);
				
				//���·����û��б�
				sendUserList2Client(server.getConnList());
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * ÿһ���ͻ��˵�¼��Ͽ�������������ӣ�����Ҫ���������пͻ��˷��͵�ǰ���ߵ��û�
	 * @param list
	 */
	private void sendUserList2Client(List<Connect> list){
		if(list==null) return;
		try {
			//ƴ�ӷ��͵��б���һ�������б�ת���ַ����ڷ���
			String userStr="";
			for(Connect conn:list){
				userStr+=conn.getName()+":"+conn.getCip()+",";			
			}
			userStr=userStr.substring(0,userStr.length()-1);
			//������Ϣ��������3��ʾ�Ƿ�������ͻ��˷����µ��û��б���Ϣ
			MessageContext message=new MessageContext();
			message.setType(3);
			message.setContext(userStr);
			//��ÿ�����ߵĿͻ��˶�����
			for(Connect conn:list){
				Socket socket=conn.getSocket();
				//ֻ���������ߵķ��������ߵ��Ƿ����˵�
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
