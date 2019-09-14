package com.jzgx.server;

import java.net.Socket;

/**
 * 服务器与客户端连接的信息描述，记录客户端的名字，IP,通讯用的套接字，接收线程
 * @author liyun
 *
 */
public class Connect {
	
	/** 客户端的名字 */
	private String name;
	/** 客户端的IP */
	private String cip;
	/** 通讯用的套接字 */
	private Socket socket;
	/** 接收线程 */
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
