package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import Common.Account;
import Common.Message;
import Common.Room;

public class PersonalServer extends Thread {
	
	// static ================================================================
	static UserAccountPool accountPool; 	// ��ü ������ ���� �������� ���� ����
	static DatagramSocket ds;				// UDP�� ���� ����
	static List<Room> rooms;				// ������ ���� ����
	 
	static {
		accountPool = new UserAccountPool();
		rooms = new ArrayList<>();

		try {
			ds = new DatagramSocket(56789);
		}catch(IOException e) {
			System.out.println("alramSocket create failed.. " + e.toString());
		}
	}
	
	static void sendAlramToAll(String alram) {			// ��� �������� UDP����
		DatagramPacket dp = new DatagramPacket(alram.getBytes(), alram.getBytes().length);
		for(Account a : accountPool.getCurrentUser()) {
			dp.setSocketAddress(a.getSocketAddress());
			try {
				System.out.println("server UDP send");
				
				ds.send(dp);
			}catch(IOException e) {
				System.out.println("[server-Thread] send alarm failed .. " + e.toString());
			}
		}
	}	
	
	static void sendAlramToUser(SocketAddress sa, String alram) {		// Ư�� �������� UDP ����
		DatagramPacket dp = new DatagramPacket(alram.getBytes(), alram.getBytes().length);
		dp.setSocketAddress(sa);
		try {
			System.out.println("server UDP send");
			ds.send(dp);
		}catch(IOException e) {
			System.out.println("[server-Thread] send alarm failed .. " + e.toString());
		}
		
	}
	
	static void sendAlramToUsers(Room r, String alram) {			// �濡 �ִ� ������� UDP ����
		DatagramPacket dp = new DatagramPacket(alram.getBytes(), alram.getBytes().length);
	
		for(Account a : r.getJoiner()) {
			SocketAddress sa = a.getSocketAddress();
			dp.setSocketAddress(sa);
			try {
				System.out.println("server UDP send");
				System.out.println("txt"+alram);
				ds.send(dp);
			}catch(IOException e) {
				System.out.println("[server-Thread] send alarm failed .. " + e.toString());
			}
		}
	}
	
	//=========================================================================================
	
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Account user;		//  ���� ���� ��ü ����
	
	// ������ ================================
	public PersonalServer(Socket socket) {
		this.socket = socket;
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
		}catch(IOException e) {}
	}
	//========================================
	@Override
	public void run() {
		String[] command = null;
		while(socket.isConnected()) {
			String received;
			try {
				received = (String)ois.readObject();
			}catch(IOException | ClassNotFoundException e) {	// ������ �����
				user.setSocketAddress(null);	// �ش� ���̵��� ip null
				accountPool.getCurrentUser().remove(user);	// ���� �α��� ���� ����
				if(user.getJoinRoomIndex() != -1) {		// ������ �濡 �־��� �� ó��
					if(rooms.get(user.getJoinRoomIndex()).getJoiner().size() == 2) {
						sendAlramToUser(getOtherRoomUserSocketAddress(), "reroominfo");
					} else {
						
					}
					rooms.get(user.getJoinRoomIndex()).getJoiner().remove(user);
					if(rooms.get(user.getJoinRoomIndex()).getJoiner().size()==0) {
						rooms.remove(user.getJoinRoomIndex());
						for(Room r : rooms) {
							for(Account a : r.getJoiner()) {
								if(a.getJoinRoomIndex() > user.getJoinRoomIndex()) {
									a.setJoinRoomIndex(a.getJoinRoomIndex()-1);
								}
							}
						}
					}
					user.setJoinRoomIndex(user.getJoinRoomIndex()-1);
				}
				accountPool.fileOutput();
				sendAlramToAll("userListChange");
				sendAlramToAll("changerooms");
				break;
			}
			
			System.out.println("[server] received : " + received);
			command = received.split("#");
			Object resp = null;
			System.out.println(command[0]);
			switch(command[0]) {
				// Client�κ��� TCP�� ���� �޴� �κ�
				case "create":			// ȸ������
					resp = accountPool.create(command[1], command[2], command[3]);
					// ���ϰ��� true#~, false#~�� �޴´�.
					sendToClient(resp);
					// TCP�� resp�� Client �����ش�.
					break;	
				case "join":			// �α���
					String result = accountPool.login(command[1], command[2], socket.getRemoteSocketAddress());
					// ���ϰ��� true#~, flase#~�� �޴´�.
					user = accountPool.getAccountMap().get(command[1]);
					// user�� ���� �����Ϸ��� nick�� ����
					sendToClient(result);
					// TCP�� result�� Client �����ش�.
					if(result.equals("true")) {
						// ���ϰ��� true�� 
						sendAlramToAll("userListChange");
						// Client�� UDP�� userList ������ �ش޶�� ������.
					}
					break;
				case "get":				// RoomPanel ���� ���
					resp = accountPool.getCurrentUser();
					// ����Ǿ��ִ� ���������� ����� resp�� ����[Set����]
					sendToClient(resp);
					// TCP�� resp�� Client �����ش�. 
					break;
				case "logout":			// �α׾ƿ�
					boolean logOutResult = accountPool.logOut(user);
					// �α׾ƿ������ true �ƴϸ� false�� ����
					if(logOutResult) {
						// �α׾ƿ��ٸ�
						sendAlramToAll("userListChange");
						// ��ο��� UDP�� userListChange ����
					}
					sendToClient(logOutResult);
					// TCP�� �α׾ƿ���� boolean���� ����
					break;
				case "enterroom":		// �� ����
					System.out.println("enterroom");
					user.setJoinRoomIndex(Integer.valueOf(command[1]));
					// ���� ���ȣ�� ���� ������ ���ִ� ���ȣ�� ���ý�Ű��
					int roomIndex = user.getJoinRoomIndex();
					// �� ��ȣ�� �����ͼ� roomIndex�� ����
					if(rooms.get(roomIndex).getJoiner().size() != 2 && rooms.get(roomIndex).getPass().equals("")) {
						// �� �濡 2���� ����, ��й�ȣ�� ���ٸ�
						rooms.get(roomIndex).enterAccount(user);
						// �� ���� joiner�� ���������� �߰������ش�.
						resp = rooms.get(roomIndex);
						// �� ���� �����ͼ� resp�� ����
						sendToClient(resp);
						// TCP�� ���� �����ش�.
						sendAlramToAll("changerooms");
					} else {
						resp = null;
						sendToClient(resp);
					}
					break;
				case "secretroom":	// ����� �����
					if(command[1].equals((rooms.get(user.getJoinRoomIndex()).getPass()))) {
						// ��й�ȣ�� ����������ϴ� ���� ��й�ȣ�� ������
						rooms.get(user.getJoinRoomIndex()).enterAccount(user);
						// �� �濡 ������ �߰���Ű��
						resp = rooms.get(user.getJoinRoomIndex());
						// �� ���� resp�� �����ϰ�
						sendAlramToAll("changerooms");
						// ���� ���� UDP�� ����
					} else {
						// ��й�ȣ�� �ٸ���
						resp = null;
						// null�����ϰ�
					}
					sendToClient(resp);
					// TCP�� null ����
					break;
				case "createroom":	// �� ����
					if(rooms.size() >=8) {
						// ���� ���� �����ִٸ� ���´�.
						sendToClient(null);
						// TCP�� NULL�� �����ش�.
						sendToClient(0);
					} else {
						user.setJoinRoomIndex(rooms.size());
						// command�� ���̰� 3�̶�� ������� �ƴϰ� 1�ο��̳� 2�ο�
						// command�� ���̰� 4��� ������̱� ������ ������ 2�ο�
						if(command.length == 3) {
							if(command[2].equals("true")) {
								rooms.add(new Room(user,command[1],true,""));
							} else {
								rooms.add(new Room(user,command[1],false,""));
							}
						} else {
							rooms.add(new Room(user,command[1],true,command[2]));
						}
						resp = rooms.get(rooms.size()-1);
						sendToClient(resp);
						sendAlramToAll("changerooms");
					}
					System.out.println("createroom");
					break;
				case "roomlist":	// �� ��� �ҷ�����
					sendToClient(rooms);
					// ���� TCP�� �����ش�.
					break;	
				case "leave":		// �濡�� ����
					// Ǯ���̸� ���濡�� �˷�����ϰ� ȥ�������� �׳� ����ָ��
					rooms.get(user.getJoinRoomIndex()).getJoiner().remove(user);
					// ���� ������ �׹濡�� ���Ž�Ű�� 
					if(rooms.get(user.getJoinRoomIndex()).getJoiner().size() == 1) {
						// ���� ���� joiner size�� 2�̸� Ǯ���̸� 
						System.out.println("������ 2 �´�?");
						sendAlramToUser(getOtherRoomUserSocketAddress(), "reroominfo");
						// ������ �ּҷ� UDP�� ���� reroominfo
					}

					
					if(rooms.get(user.getJoinRoomIndex()).getJoiner().size()==0) {
						// ���� ���Ž�Ű�� size�� 0�̸�
						rooms.remove(user.getJoinRoomIndex());
						// �� �� ���� ��Ű��
						for(Room r : rooms) {
							// �����ȹ�üũ �ҷ��� �Ѵ�.
							for(Account a : r.getJoiner()) {
								// �� �濡 ����� a�� ����
								if(a.getJoinRoomIndex() > user.getJoinRoomIndex()) {
									// ���� �� ���� ��ȣ�� �����Ⱦ����ִ� ���� ��ȣ�� ���ؼ� �������ִ� �� ��ȣ�� ũ��
									a.setJoinRoomIndex(a.getJoinRoomIndex() -1);
									// �ϳ��� ������ ������
								}
							}
						}
					}
					sendToClient(null);
					// TCP�� NULL ������
					sendAlramToAll("changerooms");
					System.out.println("??");
					user.setJoinRoomIndex(-1);
					break;
				case "chat":		// ���������� ä�� �ѷ���
					Room r = rooms.get(user.getJoinRoomIndex());
					// room ��ü�� �����ϴµ� ���� ������ ���ִ� ���� r�� ����
					String str = "["+user.getNick()+"] : ";
					// �� ���� user nick�� str�� ����
					sendAlramToUsers(r, "changechat#"+str+command[1]);
					// �� �������鿡�� ����, UDP�� changechat#[nick] : txt��
					break;
				case "check":	// ������ ����Ǿ����� Ȯ��
					if(user == rooms.get(user.getJoinRoomIndex()).getJoiner().get(0)) {
						// ���� ������ ����[�迭�� 0��°����]�̸� ����, ������ �������� üũ�ϱ� ������ ������ �ڽ��� ������
						sendAlramToUsers(rooms.get(user.getJoinRoomIndex()), "changecreater");
						// �� ���� ���濡�� ������ changecreater UDP��
					}
					break;
				case "ready":	// �غ�Ϸ�
					sendAlramToUser(getOtherRoomUserSocketAddress(), "readysuccess");
					// ���濡��[����] READYSUCCESS ����
					System.out.println("����?");
					break;
				case "readycancel":	// �غ����
					sendAlramToUser(getOtherRoomUserSocketAddress(), "readycancel");
					// ���濡��[����] readycancel ������
					break;
				case "roomuserinfo":	// �� ����� ���忡�� �� ���� ��� ���� ��û
					String req = "roominfo#"+user.getNick()+"#"+user.tell();
					// mode�� ���� ���� nick�� ������ req�� ����
					sendAlramToUser(getOtherRoomUserSocketAddress(), req);
					// UDP�� ������ �ּҿ� �ڽ��� req�� ������
					break;
				case "startgame":	// �� �����鿡�� ���� ���� �˸�
					Room r1 = rooms.get(user.getJoinRoomIndex());
					// ������������ R1�� �����ϰ�
					r1.setGameStart(true);
					// ���ӽ����ߴٰ� true�� ����
					sendAlramToAll("changerooms");
					// ��� �������� �˸�[���� �ֽ�ȭ]
					sendAlramToUsers(r1, received +"#" + r1.getJoiner().size());
					// r1�� ��� �������� ���ȣ �ް� # ����ִ����� ����
					break;
				case "right":	// �� ���� ������ �̵�
					Room r2 = rooms.get(user.getJoinRoomIndex());
					if(r2.getSelectMusic() == 3) {
						r2.setSelectMusic(0);
					} else {
						r2.setSelectMusic(r2.getSelectMusic()+1);
					}
					sendAlramToUsers(r2, received);
					break;
				case "left":	// �� ���� ���� �̵�
					Room r3 = rooms.get(user.getJoinRoomIndex());
					if(r3.getSelectMusic() == 0) {
						r3.setSelectMusic(3);
					} else {
						r3.setSelectMusic(r3.getSelectMusic()-1);
					}
					sendAlramToUsers(r3, received);
					break;
				case "score":	// ���� �޾Ƽ� ���濡�� ����
					sendAlramToUser(getOtherRoomUserSocketAddress(), received);
					break;
				case "gameresult":	// 2�ο� ���� ��� �������� �����ϰ� �������� ����
					if(command[1].equals("0")) {
						user.setWin(user.getWin()+1);
					} else if(command[1].equals("2")) {
						user.setDraw(user.getDraw()+1);
					} else if(command[1].equals("1")) {
						user.setLose(user.getLose()+1);
					}
					accountPool.fileOutput();
					Room r4 = rooms.get(user.getJoinRoomIndex());
					if(r4.isResultSetting()) {
						sendAlramToUsers(r4, "gameresultSetted");
						r4.setGameStart(false);
						r4.setResultSetting(false);
					} else {
						r4.setResultSetting(true);
					}
					break;
				case "reroomuserinfo":	// ���� ������ ���� ���� ����
					Room r5 = rooms.get(user.getJoinRoomIndex());
					sendToClient(r5);
					System.out.println("����?");
					break;
				case "QuickRoom":	// ���� ����
					List<Room> cur = new ArrayList<>(); 
					// List �÷��� cur �����
					for(Room s : rooms) {
						// ���� ������ ���� �ϳ��� ����
						if(s.getPass().equals("") && s.getJoiner().size() != 2) {
							// ���� ������� �ƴϰ� Ǯ���� �ƴ϶��
							cur.add(s);
							// cur�� �� ���� ����
						}
					}
					// �׷��� �� �� �ִ� �� ������
					if(cur.isEmpty()) {
						// cur�� ����ִٸ�[�����ִ¹��� ���ٸ�]
						resp = -1;
						// resp -1����
						sendToClient(resp);
						// [TCP]�� -1�� ����
					} else {
						// �� �� �ִ� ���� �ִٸ�
						int ran = (int)(Math.random()*cur.size());
						// �����ִ¹� �������� �̾Ƽ� 
						int d = rooms.indexOf(cur.get(ran));
						// �������� ���� �� ���� ��ȣ�� d�� �����ϰ�
						sendToClient(d);
						// �� d�� ����
					}
					break;
				case "sendMessage":	// ���� ������
					String date = new SimpleDateFormat("yyyy-MM-dd aaa hh:mm ").format(new Date());
					// ���� ��¥�� ����?
					Account rec = accountPool.getAccountMap().get(command[1]);
					// ������ name�� rec�� ����
					rec.getMessagelist().add(new Message(false, user.getNick(), command[2], false, date));
					// rec�� �޼��� ����Ʈ�� ���´��� �ƴ��� / ������� ������ �� / ��Ʈ / �о����� ���о����� / ��¥ �� �־���
					Message m = new Message(true, command[1], command[2], true,  date);
					// �޼��� ��ü�� ����� �ҷ���(�����Ű� / ���� ������ �� / ��Ʈ / �����Ű� / ��¥ )
					user.getMessagelist().add(m);
					// ���� ������ �޼��� ����Ʈ�� ����
					accountPool.fileOutput();
					// ���Ϸ� ������
					sendAlramToUser(rec.getSocketAddress(), "resetmessage");
					// ���濡�� ���� UDP�� resetmessage
					sendAlramToUser(rec.getSocketAddress(), "sendmessage");
					// ���濡�� ���� UDP�� sendmessage
					break;
				case "messagelist": // ���� ����Ʈ ����
					sendToClient(user.getMessagelist());
					// ���� ������ messagelist�� TCP�� ����
					break;
				case "notreadingmessage":	// ���� ���� ���� ����
					int num = 0;
					// ���� ����
					for(Message m1 : user.getMessagelist()) {
						// ���� ������ �޼��� ����Ʈ�� ������
						if(m1.isRead() == false ) {
							// �޼����� ���о�������
							num++;
							// num ����
						}
					}
					// num���� �������� ���� ������
					sendToClient(num);
					// num�� TCP�� ����
					break;
				case "checkreadmessage":	// ���� �о��� �� ó��
					if(user.getMessagelist().get(Integer.valueOf(command[1])).isRead() == false) {
						// ����Ŭ���ؼ� ���� ���о����ϱ�
						user.getMessagelist().get(Integer.valueOf(command[1])).setRead(true);
						// �������� �ٲ��ְ�
						accountPool.fileOutput();
						// ���Ϸ� ����
						sendToClient(true);
						// Ʈ��� �����ְ�
					} else {
						// ���� �о�����
						sendToClient(false);
						// false�� ó��
					}
					break;
				case "readmessage":			// Ư�� ���� ����
					resp = user.getMessagelist().get(Integer.valueOf(command[1]));
					// ���õ� �޼��� idx��°�� �޾Ƽ� resp�� �ְ� �� �޼��� ����Ʈ���� �̾Ƴ�
					sendToClient(resp);
					// �ٽ� resp�� TCP�� ������
					break;
				case "availableinviteuser":	// �ʴ� ������ ��� ����
					List<Account> li = new ArrayList<>();
					// List �÷��� �����
					for(Account b : accountPool.getCurrentUser()) {
						// ���� ���������� �Ѹ� Ȯ���ϰڴ�
						if(b.getJoinRoomIndex() == -1) {
							// ���� ������ �� �濡 �ȵ��ִ»�� ã��
							li.add(b);
							// li�� �ְ�
						}
					}
					sendToClient(li);
					// �� ����Ʈ�� TCP�� ����
					break;
				case "inviteuser":	// ���� �ʴ�
					SocketAddress s1 = accountPool.getAccountMap().get(command[1]).getSocketAddress();
					// ���� ���� �ּҸ� S1�� ����
					sendAlramToUser(s1, "invite#" + user.getNick() + "#" + user.getJoinRoomIndex());
					// UDP�� invite ������ ���� �����а� ���濡 �ִ��� ������
					break;
				case "expeluser":	// ���� ����
					Account a4 = rooms.get(user.getJoinRoomIndex()).getJoiner().get(1);
					// ������ �ƴѻ���� �� ������ �̾� a4�� �ְ�
					SocketAddress s2 = a4.getSocketAddress();
					// �׻���� �ּҰ��� s2�� ����
					sendAlramToUser(s2, "expel");
					// �׻������ expel ����
					break;
				case "singlegameresult":	// 1�ο� ���� ��� ó��
					if(user.getMaxScore() < Integer.valueOf(command[1])){
						user.setMaxScore(Integer.valueOf(command[1]));
						sendAlramToAll("userListChange");
						resp = user;
					} else {
						resp = null;
					}
					sendToClient(resp);
					break;
				case "changeroomsetting":	// �� ���� ����
					Room t = rooms.get(user.getJoinRoomIndex());
					t.setTitle(command[1]);
					t.setTwoUserRoom(Boolean.valueOf(command[2]));
					if(command.length == 4) {
						t.setPass(command[3]);
					} else {
						t.setPass("");
					}
					sendAlramToAll("changerooms");
					break;
					
			}
		}
	}

	private void sendToClient(Object resp) {
		try {
			oos.reset();	
			oos.writeObject(resp);
			System.out.println(resp);
		}catch(IOException e) {
			System.out.println("server write fail.. " + e.toString());
		}
	}
	
	private SocketAddress getOtherRoomUserSocketAddress() {
		Room r = rooms.get(user.getJoinRoomIndex());
		// r�� ���� ���� ���� ����
		int idx = 0;
		// ���� ����
		if(r.isTwoUserRoom()) {
			// r�� 2�ο��̸� ���´�.
			idx = r.getJoiner().indexOf(user) == 0 ? 1 :0;
			// idx�� ������ ����
		}
		return r.getJoiner().get(idx).getSocketAddress();
		// ������ �ּҸ� ���Ͻ�����
	}
	
}
