package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import Common.Account;

public class UserAccountPool {
	private Map<String,Account> accountMap;		// 유저의 계정 보관
	private Set<Account> currentUser;			// 현재 접속중인 유저
	private File address;						
	
	// 생성자 ==========================================================
	public UserAccountPool() {
		
		// 서버에 저장된 계정 파일 호출 =====================================
		File file = new File(System.getProperty("user.home") , "dropthebeat_Account");
		if(!file.exists()) {
			file.mkdirs();
		}
		address = new File(file.toString() + "\\Account.txt");
		if(!address.exists()) {
			accountMap = new HashMap<>();
		} else {
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(address));
				accountMap = (Map<String, Account>)ois.readObject();
			}catch(Exception e){
				System.out.println(e.toString());
			}
		}
		// ============================================================
		
		currentUser = new HashSet<>();
	}
	// ================================================================
	
	public String create(String id, String pass, String name) {			// 아이디 생성 
		// Server에서 받은 id, pass, name을 받아서
		if(accountMap.containsKey(id)) {
			// 저장되어있는 id와 받은 id가 있으면 들어온다.
			return "false#이미 아이디가 존재합니다.";
			// false#~로 리턴
		} else {
			// 없으면
			accountMap.put(id, new Account(id, pass, name));
			// Map에 저장한다.
			System.out.println(accountMap);
			fileOutput();
			// 파일로 내보내준다.
			return "true";
			// true로 리턴
		}
	}
	
	public String login(String id, String pass, SocketAddress sa) {		// 로그인
		// id와 pass 접속하려는 사람의 주소를 받아서
		if(accountMap.containsKey(id)) {
			// Map에 id가 있는지 확인(회원가입된 아이디인지)
			System.out.println("getcontains");
			if(!currentUser.contains(new Account(id, "", ""))) {
				// 현재 접속중인 유저에 id가 포함이 안되어있으면 들어온다.
				if(accountMap.get(id).getPass().equals(pass)) {
					// 비밀번호가 맞으면 들어온다.
					currentUser.add(accountMap.get(id));
					// 현재 접속중인 Set컬렉션에 id를 저장하고
					accountMap.get(id).setSocketAddress(sa);
					// Map에 주소 저장
					System.out.println("???");
					fileOutput();
					// 파일로 내보내준다.
					return "true";
					// 리턴 트루
				}else {
					// 비밀번호가 틀리면
					return "false#비밀번호가 일치하지 않습니다.";
					// 리턴 false
				}
			} else {
				// 현재 접속중인 유저에 id가 포함되있으면
				return "false#이미 접속중인 아이디입니다.";
				// 리턴 false
			}
		} else {
			// Map에 아이디가 없으면
			return "false#존재하지 않는 아이디입니다.";
			// 리턴 false
		}
	}
	
	public boolean logOut(Account user) {
		// 현재유저를 받아
		if(user == null) {
			// 유저가 없으면
			return false;
			// 리턴 false
		}
		user.setSocketAddress(null);
		// user의 주소 null 설정해주고
		if(currentUser.remove(user)) {
			// 만약 현재유저가 지워졌다면[지워졌으면 true? 안지워졌으면 false?]
			return true;
			// 리턴 true
		}
		return false;
		// 그게 아니면 false
	}
	
	public void fileOutput() {				// 파일 출력 메소드
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(address));
			oos.writeObject(accountMap);
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}
	
	// Getter and Setter ========================================
	public Map<String, Account> getAccountMap() {
		return accountMap;
	}

	public void setAccountMap(Map<String, Account> accountMap) {
		this.accountMap = accountMap;
	}

	public Set<Account> getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(Set<Account> currentUser) {
		this.currentUser = currentUser;
	}

	public File getAddress() {
		return address;
	}

	public void setAddress(File address) {
		this.address = address;
	}
	//============================================================
}
