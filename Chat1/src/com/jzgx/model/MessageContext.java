package com.jzgx.model;

/**
 * ��װ��Ϣ�ĸ�ʽ
 * @author liyun
 *
 */
public class MessageContext implements java.io.Serializable{

	/** ������ */
	private String from;
	/** ������ */
	private String to;
	/** ��Ϣ���� */
	//0:��ͨ������Ϣ��1:�ļ���2���û���¼��Ϣ 3���û��б���Ϣ,4:�û��Ͽ�������Ϣ
	private int type;
	
	/** ��Ϣ���� */
	private Object context;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Object getContext() {
		return context;
	}

	public void setContext(Object context) {
		this.context = context;
	}
	
}
