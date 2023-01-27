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
	private Map<String,Account> accountMap;		// ������ ���� ����
	private Set<Account> currentUser;			// ���� �������� ����
	private File address;						
	
	// ������ ==========================================================
	public UserAccountPool() {
		
		// ������ ����� ���� ���� ȣ�� =====================================
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
	
	public String create(String id, String pass, String name) {			// ���̵� ���� 
		// Server���� ���� id, pass, name�� �޾Ƽ�
		if(accountMap.containsKey(id)) {
			// ����Ǿ��ִ� id�� ���� id�� ������ ���´�.
			return "false#�̹� ���̵� �����մϴ�.";
			// false#~�� ����
		} else {
			// ������
			accountMap.put(id, new Account(id, pass, name));
			// Map�� �����Ѵ�.
			System.out.println(accountMap);
			fileOutput();
			// ���Ϸ� �������ش�.
			return "true";
			// true�� ����
		}
	}
	
	public String login(String id, String pass, SocketAddress sa) {		// �α���
		// id�� pass �����Ϸ��� ����� �ּҸ� �޾Ƽ�
		if(accountMap.containsKey(id)) {
			// Map�� id�� �ִ��� Ȯ��(ȸ�����Ե� ���̵�����)
			System.out.println("getcontains");
			if(!currentUser.contains(new Account(id, "", ""))) {
				// ���� �������� ������ id�� ������ �ȵǾ������� ���´�.
				if(accountMap.get(id).getPass().equals(pass)) {
					// ��й�ȣ�� ������ ���´�.
					currentUser.add(accountMap.get(id));
					// ���� �������� Set�÷��ǿ� id�� �����ϰ�
					accountMap.get(id).setSocketAddress(sa);
					// Map�� �ּ� ����
					System.out.println("???");
					fileOutput();
					// ���Ϸ� �������ش�.
					return "true";
					// ���� Ʈ��
				}else {
					// ��й�ȣ�� Ʋ����
					return "false#��й�ȣ�� ��ġ���� �ʽ��ϴ�.";
					// ���� false
				}
			} else {
				// ���� �������� ������ id�� ���Ե�������
				return "false#�̹� �������� ���̵��Դϴ�.";
				// ���� false
			}
		} else {
			// Map�� ���̵� ������
			return "false#�������� �ʴ� ���̵��Դϴ�.";
			// ���� false
		}
	}
	
	public boolean logOut(Account user) {
		// ���������� �޾�
		if(user == null) {
			// ������ ������
			return false;
			// ���� false
		}
		user.setSocketAddress(null);
		// user�� �ּ� null �������ְ�
		if(currentUser.remove(user)) {
			// ���� ���������� �������ٸ�[���������� true? ������������ false?]
			return true;
			// ���� true
		}
		return false;
		// �װ� �ƴϸ� false
	}
	
	public void fileOutput() {				// ���� ��� �޼ҵ�
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
