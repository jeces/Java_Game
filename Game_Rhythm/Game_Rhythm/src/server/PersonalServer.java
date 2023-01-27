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
	static UserAccountPool accountPool; 	// 전체 유저와 현재 접속중인 유저 관리
	static DatagramSocket ds;				// UDP를 위한 소켓
	static List<Room> rooms;				// 생성된 방을 관리
	 
	static {
		accountPool = new UserAccountPool();
		rooms = new ArrayList<>();

		try {
			ds = new DatagramSocket(56789);
		}catch(IOException e) {
			System.out.println("alramSocket create failed.. " + e.toString());
		}
	}
	
	static void sendAlramToAll(String alram) {			// 모든 유저에게 UDP전송
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
	
	static void sendAlramToUser(SocketAddress sa, String alram) {		// 특정 유저에게 UDP 전송
		DatagramPacket dp = new DatagramPacket(alram.getBytes(), alram.getBytes().length);
		dp.setSocketAddress(sa);
		try {
			System.out.println("server UDP send");
			ds.send(dp);
		}catch(IOException e) {
			System.out.println("[server-Thread] send alarm failed .. " + e.toString());
		}
		
	}
	
	static void sendAlramToUsers(Room r, String alram) {			// 방에 있는 사람에게 UDP 전송
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
	private Account user;		//  현재 계정 객체 저장
	
	// 생성자 ================================
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
			}catch(IOException | ClassNotFoundException e) {	// 비정상 종료시
				user.setSocketAddress(null);	// 해당 아이디의 ip null
				accountPool.getCurrentUser().remove(user);	// 현재 로그인 유저 제거
				if(user.getJoinRoomIndex() != -1) {		// 유저가 방에 있었을 때 처리
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
				// Client로부터 TCP를 통해 받는 부분
				case "create":			// 회원가입
					resp = accountPool.create(command[1], command[2], command[3]);
					// 리턴값이 true#~, false#~로 받는다.
					sendToClient(resp);
					// TCP로 resp를 Client 보내준다.
					break;	
				case "join":			// 로그인
					String result = accountPool.login(command[1], command[2], socket.getRemoteSocketAddress());
					// 리턴값이 true#~, flase#~로 받는다.
					user = accountPool.getAccountMap().get(command[1]);
					// user에 현재 접속하려는 nick을 저장
					sendToClient(result);
					// TCP로 result를 Client 보내준다.
					if(result.equals("true")) {
						// 리턴값이 true면 
						sendAlramToAll("userListChange");
						// Client에 UDP로 userList 갱신을 해달라고 보낸다.
					}
					break;
				case "get":				// RoomPanel 유저 목록
					resp = accountPool.getCurrentUser();
					// 저장되어있는 현재유저들 목록을 resp에 저장[Set형태]
					sendToClient(resp);
					// TCP로 resp를 Client 보내준다. 
					break;
				case "logout":			// 로그아웃
					boolean logOutResult = accountPool.logOut(user);
					// 로그아웃됬으면 true 아니면 false로 저장
					if(logOutResult) {
						// 로그아웃됬다면
						sendAlramToAll("userListChange");
						// 모두에게 UDP로 userListChange 보냄
					}
					sendToClient(logOutResult);
					// TCP로 로그아웃결과 boolean으로 보냄
					break;
				case "enterroom":		// 방 입장
					System.out.println("enterroom");
					user.setJoinRoomIndex(Integer.valueOf(command[1]));
					// 받은 방번호를 현재 유저가 들어가있는 방번호에 세팅시키고
					int roomIndex = user.getJoinRoomIndex();
					// 그 번호를 가져와서 roomIndex에 저장
					if(rooms.get(roomIndex).getJoiner().size() != 2 && rooms.get(roomIndex).getPass().equals("")) {
						// 그 방에 2명이 없고, 비밀번호가 없다면
						rooms.get(roomIndex).enterAccount(user);
						// 그 방의 joiner에 현재유저를 추가시켜준다.
						resp = rooms.get(roomIndex);
						// 그 방을 가져와서 resp에 저장
						sendToClient(resp);
						// TCP로 방을 보내준다.
						sendAlramToAll("changerooms");
					} else {
						resp = null;
						sendToClient(resp);
					}
					break;
				case "secretroom":	// 비번방 입장시
					if(command[1].equals((rooms.get(user.getJoinRoomIndex()).getPass()))) {
						// 비밀번호가 현재들어가려고하는 방의 비밀번호와 같으면
						rooms.get(user.getJoinRoomIndex()).enterAccount(user);
						// 그 방에 유저를 추가시키고
						resp = rooms.get(user.getJoinRoomIndex());
						// 그 방을 resp에 저장하고
						sendAlramToAll("changerooms");
						// 방목록 갱신 UDP로 보냄
					} else {
						// 비밀번호가 다르면
						resp = null;
						// null설정하고
					}
					sendToClient(resp);
					// TCP로 null 보냄
					break;
				case "createroom":	// 방 생성
					if(rooms.size() >=8) {
						// 만약 방이 꽉차있다면 들어온다.
						sendToClient(null);
						// TCP로 NULL을 보내준다.
						sendToClient(0);
					} else {
						user.setJoinRoomIndex(rooms.size());
						// command의 길이가 3이라면 비번방이 아니고 1인용이나 2인용
						// command의 길이가 4라면 비번방이기 때문에 무조건 2인용
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
				case "roomlist":	// 방 목록 불러오기
					sendToClient(rooms);
					// 룸을 TCP로 보내준다.
					break;	
				case "leave":		// 방에서 나감
					// 풀방이면 상대방에게 알려줘야하고 혼자있으면 그냥 방없애면됨
					rooms.get(user.getJoinRoomIndex()).getJoiner().remove(user);
					// 현재 유저를 그방에서 제거시키고 
					if(rooms.get(user.getJoinRoomIndex()).getJoiner().size() == 1) {
						// 현재 방의 joiner size가 2이면 풀방이면 
						System.out.println("사이즈 2 맞니?");
						sendAlramToUser(getOtherRoomUserSocketAddress(), "reroominfo");
						// 상대방의 주소로 UDP로 보냄 reroominfo
					}

					
					if(rooms.get(user.getJoinRoomIndex()).getJoiner().size()==0) {
						// 만약 제거시키고 size가 0이면
						rooms.remove(user.getJoinRoomIndex());
						// 그 방 제거 시키고
						for(Room r : rooms) {
							// 생성된방체크 할려고 한다.
							for(Account a : r.getJoiner()) {
								// 그 방에 사람을 a에 넣음
								if(a.getJoinRoomIndex() > user.getJoinRoomIndex()) {
									// 만약 그 방의 번호가 생성된어져있는 방의 번호랑 비교해서 생성되있는 방 번호가 크면
									a.setJoinRoomIndex(a.getJoinRoomIndex() -1);
									// 하나씩 앞으로 땡겨줌
								}
							}
						}
					}
					sendToClient(null);
					// TCP로 NULL 보내줌
					sendAlramToAll("changerooms");
					System.out.println("??");
					user.setJoinRoomIndex(-1);
					break;
				case "chat":		// 방유저에게 채팅 뿌려줌
					Room r = rooms.get(user.getJoinRoomIndex());
					// room 객체를 생성하는데 현재 유저가 들어가있는 방을 r에 저장
					String str = "["+user.getNick()+"] : ";
					// 그 방의 user nick을 str에 저장
					sendAlramToUsers(r, "changechat#"+str+command[1]);
					// 그 방유저들에게 전달, UDP로 changechat#[nick] : txt를
					break;
				case "check":	// 방장이 변경되었는지 확인
					if(user == rooms.get(user.getJoinRoomIndex()).getJoiner().get(0)) {
						// 현재 유저가 방장[배열의 0번째인지]이면 들어옴, 상대방이 나갔을때 체크하기 때문에 무조건 자신이 방장임
						sendAlramToUsers(rooms.get(user.getJoinRoomIndex()), "changecreater");
						// 그 방의 상대방에게 보내줌 changecreater UDP로
					}
					break;
				case "ready":	// 준비완료
					sendAlramToUser(getOtherRoomUserSocketAddress(), "readysuccess");
					// 상대방에게[방장] READYSUCCESS 보냄
					System.out.println("보냄?");
					break;
				case "readycancel":	// 준비취소
					sendAlramToUser(getOtherRoomUserSocketAddress(), "readycancel");
					// 상대방에게[방장] readycancel 보내줌
					break;
				case "roomuserinfo":	// 방 입장시 방장에게 방 유저 목록 갱신 요청
					String req = "roominfo#"+user.getNick()+"#"+user.tell();
					// mode와 현재 유저 nick과 정보를 req에 저장
					sendAlramToUser(getOtherRoomUserSocketAddress(), req);
					// UDP로 상대방의 주소에 자신의 req를 보내줌
					break;
				case "startgame":	// 방 유저들에게 게임 시작 알림
					Room r1 = rooms.get(user.getJoinRoomIndex());
					// 현재유저방을 R1에 저장하고
					r1.setGameStart(true);
					// 게임시작했다고 true로 설정
					sendAlramToAll("changerooms");
					// 모든 유저에게 알림[방목록 최신화]
					sendAlramToUsers(r1, received +"#" + r1.getJoiner().size());
					// r1의 모든 유저에게 곡번호 받고 # 몇명있는지를 보냄
					break;
				case "right":	// 곡 선택 오른쪽 이동
					Room r2 = rooms.get(user.getJoinRoomIndex());
					if(r2.getSelectMusic() == 3) {
						r2.setSelectMusic(0);
					} else {
						r2.setSelectMusic(r2.getSelectMusic()+1);
					}
					sendAlramToUsers(r2, received);
					break;
				case "left":	// 곡 선택 왼쪽 이동
					Room r3 = rooms.get(user.getJoinRoomIndex());
					if(r3.getSelectMusic() == 0) {
						r3.setSelectMusic(3);
					} else {
						r3.setSelectMusic(r3.getSelectMusic()-1);
					}
					sendAlramToUsers(r3, received);
					break;
				case "score":	// 점수 받아서 상대방에게 전송
					sendAlramToUser(getOtherRoomUserSocketAddress(), received);
					break;
				case "gameresult":	// 2인용 게임 결과 유저에게 전송하고 대기방으로 변경
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
				case "reroomuserinfo":	// 게임 끝나고 유저 정보 갱신
					Room r5 = rooms.get(user.getJoinRoomIndex());
					sendToClient(r5);
					System.out.println("보냄?");
					break;
				case "QuickRoom":	// 빠른 입장
					List<Room> cur = new ArrayList<>(); 
					// List 컬렉션 cur 만들고
					for(Room s : rooms) {
						// 현재 생성된 룸을 하나씩 보자
						if(s.getPass().equals("") && s.getJoiner().size() != 2) {
							// 만약 비번방이 아니고 풀방이 아니라면
							cur.add(s);
							// cur에 그 방을 넣음
						}
					}
					// 그러면 들어갈 수 있는 방 설정됨
					if(cur.isEmpty()) {
						// cur이 비어있다면[들어갈수있는방이 없다면]
						resp = -1;
						// resp -1설정
						sendToClient(resp);
						// [TCP]로 -1을 보냄
					} else {
						// 들어갈 수 있는 방이 있다면
						int ran = (int)(Math.random()*cur.size());
						// 들어갈수있는방 랜덤으로 뽑아서 
						int d = rooms.indexOf(cur.get(ran));
						// 랜덤으로 뽑힌 그 방의 번호를 d에 저장하고
						sendToClient(d);
						// 그 d를 보냄
					}
					break;
				case "sendMessage":	// 쪽지 보내기
					String date = new SimpleDateFormat("yyyy-MM-dd aaa hh:mm ").format(new Date());
					// 현재 날짜를 저장?
					Account rec = accountPool.getAccountMap().get(command[1]);
					// 수신할 name을 rec에 저장
					rec.getMessagelist().add(new Message(false, user.getNick(), command[2], false, date));
					// rec의 메세지 리스트에 보냈는지 아닌지 / 보낸사람 유저의 닉 / 멘트 / 읽었는지 안읽었는지 / 날짜 를 넣어줌
					Message m = new Message(true, command[1], command[2], true,  date);
					// 메세지 객체를 만들고 불러옴(보낸거고 / 받을 유저의 닉 / 멘트 / 읽은거고 / 날짜 )
					user.getMessagelist().add(m);
					// 현재 유저의 메세지 리스트에 저장
					accountPool.fileOutput();
					// 파일로 보내줌
					sendAlramToUser(rec.getSocketAddress(), "resetmessage");
					// 상대방에게 보냄 UDP로 resetmessage
					sendAlramToUser(rec.getSocketAddress(), "sendmessage");
					// 상대방에게 보냄 UDP로 sendmessage
					break;
				case "messagelist": // 쪽지 리스트 띄우기
					sendToClient(user.getMessagelist());
					// 현재 유저의 messagelist를 TCP로 보냄
					break;
				case "notreadingmessage":	// 읽지 않은 쪽지 갯수
					int num = 0;
					// 변수 설정
					for(Message m1 : user.getMessagelist()) {
						// 현재 유저의 메세지 리스트를 가져옴
						if(m1.isRead() == false ) {
							// 메세지가 안읽어있으면
							num++;
							// num 증가
						}
					}
					// num에는 읽지않은 개수 나오고
					sendToClient(num);
					// num을 TCP로 보냄
					break;
				case "checkreadmessage":	// 쪽지 읽었을 때 처리
					if(user.getMessagelist().get(Integer.valueOf(command[1])).isRead() == false) {
						// 더블클릭해서 아직 안읽었으니까
						user.getMessagelist().get(Integer.valueOf(command[1])).setRead(true);
						// 읽음으로 바꿔주고
						accountPool.fileOutput();
						// 파일로 보냄
						sendToClient(true);
						// 트루로 보내주고
					} else {
						// 원래 읽었으면
						sendToClient(false);
						// false로 처리
					}
					break;
				case "readmessage":			// 특정 쪽지 내용
					resp = user.getMessagelist().get(Integer.valueOf(command[1]));
					// 선택된 메세지 idx번째를 받아서 resp에 넣고 그 메세지 리스트에서 뽑아냄
					sendToClient(resp);
					// 다시 resp를 TCP로 보내고
					break;
				case "availableinviteuser":	// 초대 가능한 사람 띄우기
					List<Account> li = new ArrayList<>();
					// List 컬렉션 만들고
					for(Account b : accountPool.getCurrentUser()) {
						// 현재 접속자유저 한명씩 확인하겠다
						if(b.getJoinRoomIndex() == -1) {
							// 현재 접속자 중 방에 안들어가있는사람 찾음
							li.add(b);
							// li에 넣고
						}
					}
					sendToClient(li);
					// 그 리시트를 TCP로 보냄
					break;
				case "inviteuser":	// 유저 초대
					SocketAddress s1 = accountPool.getAccountMap().get(command[1]).getSocketAddress();
					// 받은 유저 주소를 S1에 저장
					sendAlramToUser(s1, "invite#" + user.getNick() + "#" + user.getJoinRoomIndex());
					// UDP로 invite 보내고 현재 유저닉과 어디방에 있는지 보내고
					break;
				case "expeluser":	// 유저 강퇴
					Account a4 = rooms.get(user.getJoinRoomIndex()).getJoiner().get(1);
					// 방장이 아닌사람을 즉 상대방을 뽑아 a4에 넣고
					SocketAddress s2 = a4.getSocketAddress();
					// 그사람의 주소값을 s2에 저장
					sendAlramToUser(s2, "expel");
					// 그사람한테 expel 보냄
					break;
				case "singlegameresult":	// 1인용 게임 결과 처리
					if(user.getMaxScore() < Integer.valueOf(command[1])){
						user.setMaxScore(Integer.valueOf(command[1]));
						sendAlramToAll("userListChange");
						resp = user;
					} else {
						resp = null;
					}
					sendToClient(resp);
					break;
				case "changeroomsetting":	// 방 설정 변경
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
		// r에 현재 유저 방을 저장
		int idx = 0;
		// 변수 설정
		if(r.isTwoUserRoom()) {
			// r이 2인용이면 들어온다.
			idx = r.getJoiner().indexOf(user) == 0 ? 1 :0;
			// idx에 상대방을 저장
		}
		return r.getJoiner().get(idx).getSocketAddress();
		// 상대방의 주소를 리턴시켜줌
	}
	
}
