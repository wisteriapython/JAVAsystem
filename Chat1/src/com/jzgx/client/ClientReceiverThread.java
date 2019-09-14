package com.jzgx.client;

import java.io.ObjectInputStream;

import javax.swing.JOptionPane;

import com.jzgx.model.MessageContext;

/**
 * �ͻ��˽����߳�
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
				//0����ͨ������Ϣ��1.���ļ�����δ����2.���û���¼��ֻ������ڷ������ˣ�3�Ǵӷ������˷��������û��б�
				if(message.getType()==0){
					client.addMessage(message.getFrom()+"��"+message.getTo()+"˵��"+message.getContext());
				}
				if(message.getType()==3){
					String userListStr=(String) message.getContext();
					String[] userArray=userListStr.split(",");
					client.updateUserList(userArray);
				}
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(client, "�����쳣��ֹ�������ӶϿ�");
				System.exit(0);
			}
		}
	}
}
