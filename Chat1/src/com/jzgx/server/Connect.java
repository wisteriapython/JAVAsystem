package com.jzgx.server;

import java.net.Socket;

/**
 * ��������ͻ������ӵ���Ϣ��������¼�ͻ��˵����֣�IP,ͨѶ�õ��׽��֣������߳�
 * @author liyun
 *
 */
public class Connect {
	
	/** �ͻ��˵����� */
	private String name;
	/** �ͻ��˵�IP */
	private String cip;
	/** ͨѶ�õ��׽��� */
	private Socket socket;
	/** �����߳� */
	private ServerReceiverThread serverReceiverThread;
	
	public String getCip() {
		return cip;
	}
	public void setCip(String cip) {
		this.cip = cip;
	}
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ServerReceiverThread getServerReceiverThread() {
		return serverReceiverThread;
	}
	public void setServerReceiverThread(ServerReceiverThread serverReceiverThread) {
		this.serverReceiverThread = serverReceiverThread;
	}
	
}
