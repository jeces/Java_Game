package Common;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import Common.Account;

public class Room implements Serializable {
	private int selectMusic = 0;		// 방에서 선택된 곡
	private String title;			// 방 제목
	private String pass;				// 방 비밀번호
	private List<Account> joiner;	// 방에 입장한 유저목록
	private boolean gameStart;		// 게임중인지 아닌지
	private boolean resultSetting;	// 방에 존재하는 사람이 모두 게임이 끝났는지 확인
	private boolean twoUserRoom;	// 1인용인지 2인용인지 체크
	
	//======== 생성자 ================================
	public Room(Account owner, String title, boolean twouser, String pass) {			// 비밀번호 방 생성자
		joiner = new Vector<>();
		joiner.add(owner);
		this.title = title;
		this.pass = pass;
		gameStart = false;
		resultSetting = false;
		twoUserRoom = twouser;
	}
	
	public Room(Account owner, String title, boolean twouser) {		// 비밀번호가 아닌 방 생성자
		this(owner, title, twouser, "");
		gameStart = false;
	}
	//===============================================
	
	public void enterAccount(Account acc) {			// 방에 입장
		joiner.add(acc);
	}
	
	public boolean leave(Account acc) {				// 방에서 나갈 때
		return joiner.remove(acc);
	}
	
	public boolean isEmpty() {						// 빈 방인지 체크
		return joiner.size()==0;
	}
	
	@Override
	public String toString() {						// 방의 상태
		Account creater = joiner.get(0);
		return  title +" 【" + creater.getNick() +" - "+ joiner.size() +"/2】";
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
