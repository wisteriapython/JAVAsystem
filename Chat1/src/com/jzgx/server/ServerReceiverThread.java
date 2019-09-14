package com.jzgx.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import com.jzgx.model.MessageContext;

/**
 * �������˴�����Ϣ���յ��̣߳�ÿһ�����Ӷ���һ���ȴ������û�����������߳�
 * ��Ϊ����ȷ���ͻ��˺�ʱ�ᷢ����Ϣ������ֻ�������߳�֮��ȥ����
 * @author liyun
 *
 */
public class ServerReceiverThread extends Thread{
	//���������������
	private Server server;
	private Socket socket;
	private boolean flag=true;
	
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	/**
	 * ͨ�����콫���������󴫵ݽ������Ϳ������߳���ʹ�÷������������������
	 * ������ڷ����������еļ���
	 * @param server
	 * @param socket
	 */
	public ServerReceiverThread(Server server,Socket socket){
		this.server=server;
		this.socket=socket;
	}

	//���տͻ��˷�������Ϣ����Ϣ�Ƿ�װ����MessageContext,�����˷����ߣ������ߣ����ݸ�ʽ������
	public void run(){
		while(flag){
			try {
				ObjectInputStream in=new ObjectInputStream(this.socket.getInputStream());
				MessageContext message=(MessageContext) in.readObject();
				//0����ͨ������Ϣ��1.���ļ�����δ����2.�����û���¼,3.���¿ͻ����û��б�,4:�û��Ͽ����ӵ���Ϣ
				if(message.getType()==0){
					System.out.println(message.getFrom()+"��"+message.getTo()+"˵��"+message.getContext());
					server.queue.add(message);
				}
				if(message.getType()==4){
					Connect conn=server.getConnMap().get(message.getContext().toString());
					server.removeUserList(conn);
					server.setUserNum(server.getConnList().size());
					flag=false;
					System.out.println(conn.getName()+":"+conn.getCip()+"�Ͽ�����");
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
