package com.jzgx.model;

/**
 * 封装消息的格式
 * @author liyun
 *
 */
public class MessageContext implements java.io.Serializable{

	/** 发送者 */
	private String from;
	/** 接收者 */
	private String to;
	/** 消息类型 */
	//0:普通文字消息，1:文件，2：用户登录消息 3：用户列表消息,4:用户断开连接消息
	private int type;
	
	/** 消息内容 */
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
