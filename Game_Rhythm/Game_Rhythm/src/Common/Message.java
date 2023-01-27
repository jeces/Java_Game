package Common;

import java.io.Serializable;

public class Message implements Serializable {
	boolean send;
	String name;
	String ment;
	boolean read;
	String date;
	
	public Message(boolean send, String name, String ment, boolean read, String date) {
		this.send = send;
		this.name = name;
		this.ment = ment;
		this.read = read;
		this.date = date;
	}
	
	public boolean isSend() {
		return send;
	}

	public void setSend(boolean send) {
		this.send = send;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMent() {
		return ment;
	}

	public void setMent(String ment) {
		this.ment = ment;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public String sendToString() {
		return send ? "발신" : "수신";
	}
	
	
	
}
