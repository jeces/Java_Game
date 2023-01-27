package Common;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import Common.Account;

public class Room implements Serializable {
	private int selectMusic = 0;		// �濡�� ���õ� ��
	private String title;			// �� ����
	private String pass;				// �� ��й�ȣ
	private List<Account> joiner;	// �濡 ������ �������
	private boolean gameStart;		// ���������� �ƴ���
	private boolean resultSetting;	// �濡 �����ϴ� ����� ��� ������ �������� Ȯ��
	private boolean twoUserRoom;	// 1�ο����� 2�ο����� üũ
	
	//======== ������ ================================
	public Room(Account owner, String title, boolean twouser, String pass) {			// ��й�ȣ �� ������
		joiner = new Vector<>();
		joiner.add(owner);
		this.title = title;
		this.pass = pass;
		gameStart = false;
		resultSetting = false;
		twoUserRoom = twouser;
	}
	
	public Room(Account owner, String title, boolean twouser) {		// ��й�ȣ�� �ƴ� �� ������
		this(owner, title, twouser, "");
		gameStart = false;
	}
	//===============================================
	
	public void enterAccount(Account acc) {			// �濡 ����
		joiner.add(acc);
	}
	
	public boolean leave(Account acc) {				// �濡�� ���� ��
		return joiner.remove(acc);
	}
	
	public boolean isEmpty() {						// �� ������ üũ
		return joiner.size()==0;
	}
	
	@Override
	public String toString() {						// ���� ����
		Account creater = joiner.get(0);
		return  title +" ��" + creater.getNick() +" - "+ joiner.size() +"/2��";
	}

	// Getter and Setter =====================================
	public int getSelectMusic() {
		return selectMusic;
	}

	public void setSelectMusic(int selectMusic) {
		this.selectMusic = selectMusic;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public List<Account> getJoiner() {
		return joiner;
	}

	public void setJoiner(List<Account> joiner) {
		this.joiner = joiner;
	}

	public boolean isGameStart() {
		return gameStart;
	}

	public void setGameStart(boolean gameStart) {
		this.gameStart = gameStart;
	}
	
	public boolean isResultSetting() {
		return resultSetting;
	}

	public void setResultSetting(boolean resultSetting) {
		this.resultSetting = resultSetting;
	}
	
	public boolean isTwoUserRoom() {
		return twoUserRoom;
	}

	public void setTwoUserRoom(boolean twoUserRoom) {
		this.twoUserRoom = twoUserRoom;
	}
	// ===================================================
	
}
