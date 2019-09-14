package com.jzgx.client;

import java.io.ObjectInputStream;

import javax.swing.JOptionPane;

import com.jzgx.model.MessageContext;

/**
 * 客户端接收线程
 * @author liyun
 *
 */
public class ClientReceiverThread extends Thread{

	private Client client;
	private boolean flag=true;
	
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public ClientReceiverThread(Client client){
		this.client=client;
	}
	
	public void run(){
		while(flag){
			try {
				ObjectInputStream in=new ObjectInputStream(client.getSocket().getInputStream());
				MessageContext message=(MessageContext) in.readObject();
				//0是普通文字消息，1.是文件，暂未处理，2.是用户登录，只会出现在服务器端，3是从服务器端发送来的用户列表
				if(message.getType()==0){
					client.addMessage(message.getFrom()+"对"+message.getTo()+"说："+message.getContext());
				}
				if(message.getType()==3){
					String userListStr=(String) message.getContext();
					String[] userArray=userListStr.split(",");
					client.updateUserList(userArray);
				}
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(client, "连接异常终止或者连接断开");
				System.exit(0);
			}
		}
	}
}
