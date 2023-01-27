package Common;

import java.io.Serializable;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

public class Account implements Serializable{
	private String nick;	 	// 아이디
	private String pass;		// 비밀번호
	private String name;		// 이름
	private SocketAddress socketAddress;	// 접속한 ip와 port
	private int win;			
	private int lose;
	private int draw;
	private int joinRoomIndex;			// 접속중인 방
	private List<Message> messagelist;	// 보유중인 쪽지
	private int maxScore;
	
	//======== 생성자 ================================
	public Account(String nick, String pass, String name) {
		this.nick = nick;
		this.pass = pass;
		this.name = name;
		win = lose = draw = 0;
		joinRoomIndex = -1;		// 방에 들어가지 않은 상태
		this.socketAddress = null;
		messagelist = new ArrayList<>();
		maxScore = 0;
	}
	
	//==============================================
	
	
	@Override
	public String toString() {
		return "["+nick+"] " +win +"승 " +draw +"무 " +lose+"패" + "("+maxScore+"점)";
	}
	
	public String tell() {			// 전적만 뽑기
		return win +"승 " + draw +"무 " + lose+"패" + "("+maxScore+"점)";
	}
	
	
	public int hashCode() {				// 객체 비교를 위한 hashCode
		return 1;
	}
	
	public boolean equals(Object obj) {	// 객체 비교를 위한 equals(아이디로 비교)
		if (obj instanceof Account) {
			Account new_name = (Account) obj;
			return this.nick.equals(new_name.nick);
		}
		return false;
	}


	// Getter and Setter ===============================
	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SocketAddress getSocketAddress() {
		return socketAddress;
	}

	public void setSocketAddress(SocketAddress socketAddress) {
		this.socketAddress = socketAddress;
	}

	public int getWin() {
		return win;
	}

	public void setWin(int win) {
		this.win = win;
	}

	public int getLose() {
		return lose;
	}

	public void setLose(int lose) {
		this.lose = lose;
	}

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public int getJoinRoomIndex() {
		return joinRoomIndex;
	}

	public void setJoinRoomIndex(int idx) {
		this.joinRoomIndex = idx;
	}

	public List<Message> getMessagelist() {
		return messagelist;
	}

	public void setMessagelist(List<Message> messagelist) {
		this.messagelist = messagelist;
	}
	
	public int getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(int maxScore) {
		this.maxScore = maxScore;
	}
	//=================================================
}