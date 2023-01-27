package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.ListCellRenderer;

import Common.Account;
import Common.Message;
import Common.Room;
import clientPanel.InviteUsetListFrame;
import clientPanel.MessageListFrame;
import clientPanel.MusicChoicePanel;
import game.GameFrame;

public class ClientNetwork extends Thread {
	private Socket soc; // 핵심 연결 소켓
	private ObjectOutputStream oos = null;
	private ObjectInputStream ois = null;

	private DatagramSocket ds; // 서브 소켓(receive용)
	private ClientUI ui;
	private Account user;
	private String nick;	// user을 저장하기 위한 nick

	public ClientNetwork(ClientUI c) {
		this.ui = c;
		System.out.println("연결중");
		try {
			soc = new Socket(c.ip, 56789);
			System.out.println("??");
			oos = new ObjectOutputStream(soc.getOutputStream());
			ois = new ObjectInputStream(soc.getInputStream());
			System.out.println("[client] connected to server");
			ds = new DatagramSocket(soc.getLocalPort()); // // TCP와 UDP는 같은 포트로 사용할 수 있음.

		} catch (IOException e) {
			System.out.println("[client] network error " + e.toString());
		}
		start();
	}

	@Override
	public void run() {
		while (!ds.isClosed()) {
			DatagramPacket dp = new DatagramPacket(new byte[2048], 2048);
			try {
				ds.receive(dp);
				System.out.println("client UDP received");
				String data = new String(dp.getData(), 0, dp.getLength());
				System.out.println(data);
				String[] str = data.split("#");
				switch (str[0]) {
				case "userListChange":	// 로그인 유저 목록
					// UDP로 서버로부터 받아서 
					sendUserListRequest();
					// sendUserListRequest 수행
					break;
				case "changerooms":		// 방 목록 최신화
					sendStateRoomRequest();
					// sendStateRoomRequest 수행
					break;
				case "changechat":		// 받은 채팅 띄우기
					// changechat#[nick] : txt를 받아서
					System.out.println(str[1]);
					String txt = str[1];
					// [nick] : txt를 txt에 저장
					ui.pnMusicChoice.txArea.append(txt + "\n");
					// textArea에 띄워준다.
					break;
				case "changecreater":	// 방장이 바뀐 경우
					ui.pnMusicChoice.lbCrownRight.setIcon(null);
					// 오른쪽에 왕관 이미지 없애고
					ui.pnMusicChoice.lbCrownLeft.setIcon(new ImageIcon(getClass().getClassLoader().getResource("clientPanel/imge/crown1.png")));
					// 왼쪽에 이미지 띄움 [자기자신은 왼쪽임]
					ui.pnMusicChoice.btReady.setEnabled(false);
					// 레디 비활성화
					ui.pnMusicChoice.btStart.setEnabled(false);
					// 스타트 활성화
					ui.pnMusicChoice.p.leftButton.setEnabled(true);
					// 곡선택 가능
					ui.pnMusicChoice.p.rightButton.setEnabled(true);
					// 곡선택 가능
					ui.pnMusicChoice.btInvite.setEnabled(true);
					// 초대 활성화
					ui.pnMusicChoice.btExpel.setEnabled(false);
					// 강퇴 활성화 ? 상대방이 나갔을 때만 check함
					ui.pnMusicChoice.btRoomSetting.setEnabled(true);
					// 룸 재세팅 활성화
					break;
				case "readysuccess":	// 상대방 준비 완료시
					// 방장임
					System.out.println("받음");
					ui.pnMusicChoice.p.leftButton.setEnabled(false);
					// 방장 곡선택 막음
					ui.pnMusicChoice.p.rightButton.setEnabled(false);
					// 방장 곡선택 막음
					ui.pnMusicChoice.btReady.setEnabled(false);
					// 방장 레디 버튼 false
					ui.pnMusicChoice.btStart.setEnabled(true);
					// 스타트버튼 활성화
					ui.pnMusicChoice.btRoomSetting.setEnabled(false);
					// 룸 재세팅 막음
					break;
				case "readycancel":		// 상대방 준비 취소시
					ui.pnMusicChoice.p.leftButton.setEnabled(true);
					// [방장이 받음] 곡선택가능
					ui.pnMusicChoice.p.rightButton.setEnabled(true);
					// [방장이 받음] 곡선택가능
					ui.pnMusicChoice.btReady.setEnabled(false);
					// 레디 비활성화
					ui.pnMusicChoice.btStart.setEnabled(false);
					// 스타트 비활성화
					ui.pnMusicChoice.btRoomSetting.setEnabled(false);
					// 룸 재세팅 비활성화
					break;
				case "roominfo":	// 상대방이 들어왔을 때 [자신은 방장임]
					System.out.println("roominfo");
					ui.pnMusicChoice.tfusernick2.setText(str[1]);
					// 오른쪽에 상대방 정보를 띄움
					ui.pnMusicChoice.tfuserinfo2.setText(str[2]);
					// 오른쪽에 상대방 정보를 띄움
					ui.pnMusicChoice.btInvite.setEnabled(false);
					// 초대기능 비활성화
					ui.pnMusicChoice.btExpel.setEnabled(true);
					// 강퇴기능 활성화
					ui.pnMusicChoice.btRoomSetting.setEnabled(false);
					// 룸 재세팅 비활성화
					break;
				case "reroominfo":	// 상대방이 나갔을 때 
					System.out.println("상대방이 나갔음@@@@@@@");
					// 그방에 남게 된 방장이 받음
					ui.pnMusicChoice.tfusernick2.setText("???");
					// 오른쪽에 사용자 없음
					ui.pnMusicChoice.tfuserinfo2.setText("");
					// 오른쪽 정보없음
					ui.pnMusicChoice.btInvite.setEnabled(true);
					// 초대 활성화
					ui.pnMusicChoice.btExpel.setEnabled(false);
					// 강퇴기능 false
					ui.pnMusicChoice.lbCrownRight.setIcon(null);
					// 오른쪽에 왕관지우고
					ui.pnMusicChoice.lbCrownLeft.setIcon(new ImageIcon(getClass().getClassLoader().getResource("clientPanel/imge/crown1.png")));
					// 왼쪽에 왕관 띄우고
					System.out.println("비활성화 됬음?");
					// 강퇴 비활성화
					ui.pnMusicChoice.btRoomSetting.setEnabled(true);
					// 룸 재세팅 가능
//					sendCheckCreaterRequest();
					// 방장 바뀌었는지 확인
					break;
				case "startgame":	// 게임 시작시 게임 프레임 호출
					ui.pnMusicChoice.p.isRoomScreen = false;
					// ??
					GameFrame g = new GameFrame(ui);
					// GameFrame 생성
					ui.pnMusicChoice.p.selectedMusic.close();
					// ui.pnMusicChoice.p.selectedMusic 닫고[게임시작했으니]
					if(str[2].equals("1")) {
						// 방사이즈가 1이면
						g.twouser = false;
						// twouser를 false
					} else {
						// 방사이즈가 2이면
						g.twouser = true;
						// twouser를 true로 바꾸고
					}
					g.setLocationRelativeTo(null);
					// 가운데 띄우고
					g.setTitle("Drop the beat!! - Game");
					// 타이틀 띄우고
					g.setVisible(true);
					// 게임프레임 띄우고
					ui.setVisible(false);
					// ui끄고
					break;
				case "right":	// 곡 선택 오른쪽 이동
					System.out.println("곡 바뀜 받음");
					ui.pnMusicChoice.p.selectRight();
					break;
				case "left":	// 곡 선택 왼쪽 이동
					ui.pnMusicChoice.p.selectLeft();
					break;
				case "score":	// 상대방 스코어 받아서 셋팅
					String score = str[1];
					GameFrame.game.vsScore = Integer.valueOf(score);
					break;
				case "gameresultSetted":	// 상대방도 게임이 끝났을 때 방에 정보 갱신
					sendReRoomUserInfoRequest();
					break;
				case "sendmessage":			// 쪽지 받았을 때 갯수 갱신
					sendMessageStateRequest();
					// sendMessageStateRequest 수행
					break;
				case "resetmessage":		// 쪽지 리스트 갱신
					sendShowMessageListRequest();
					// sendShowMessageListRequest 실행
					break;
				case "invite":				// 초대 받았을 때
					int idx = JOptionPane.showConfirmDialog(ui, str[1] + "님이 초대하셨습니다. \n 수락하시겠습니까?" );
					// 보낸사람 nick과 함께 띄워주고 이걸 idx에 저장함
					if(idx == 0) {
						// idx가 0이면 [수락했으면]
						sendStateRoomRequest();
						// 방상태 체크해주고
						if(!(ui.pnRoom.btList.get(Integer.valueOf(str[2])).isEnabled())) {
							// 만약 초대받은 방이 열려있지 않으면
							JOptionPane.showMessageDialog(ui, "방이 꽉 찾습니다.");
							// JOption 띄워주고
							return;
							// 메소드 종료
						}
						sendEnterRoomRequest(Integer.valueOf(str[2]));
						// 그 방으로 들어감
					}
					break;
				case "expel":				// 강퇴 당할 때
					// 이걸 받아서
					sendLeaveRequest();
					// 방 떠나면 되고
					JOptionPane.showMessageDialog(ui, "강퇴당하였습니다.");
					// JOption 띄워줌
					break;
				}
			} catch (IOException e) {
				System.out.println("dp failed .. " + e.toString());
				ds.close();
				break;
			}

		}
	}
	
	// 회원가입
	public void sendCreateRequest(String nick, String pass, String name, String repass) {
		// nick, pass, name, repass를 받음
		String resp = null;
		// 저장할 변수 String 생성
		System.out.println(" [client] request : ");
		if (nick.trim().equals("") || pass.trim().equals("")) {
			// 받은 nick이 없거나 pass가 없다면 들어온다. 
			JOptionPane.showMessageDialog(ui, "아이디와 비밀번호를 입력하세요.");
			// JOption 메세지 띄워준다.
			return;
			// 메소드 처음으로 돌아간다.
		}
		if (!pass.equals(repass)) {
			// 만약 비밀번호와, 재비밀번호가 일치하지 않는다면 들어온다
			JOptionPane.showMessageDialog(ui, "비밀번호를 확인하세요");
			// JOptionPane 띄워준다.
			return;
			// 메소드 처음으로 돌아간다.
		}
		if (!ui.pnSignup.rbAgree.isSelected()) {
			// 회원가입의 동의버튼이 안눌러져있으면 들어온다.
			JOptionPane.showMessageDialog(ui, "약관을 읽고 동의해주세요.");
			// JOptionPane 띄워준다.
			return;
			// 메소드 처음으로 돌아간다.
		}
		synchronized (oos) {
			// 동기화
			try {
				oos.writeObject("create#" + nick + "#" + pass + "#" + name);
				// [TCP] 서버로 mode와 nick과 pass, name을 보내준다.
				resp = (String) ois.readObject();
				// 서버로부터 true, false 받고
				System.out.println("[client] response : " + resp);
				String[] data = resp.split("#");
				// 받은 resp를 split으로 찍는다.
				
				// 여기서 ui 제어.
				if (data[0].equals("true")) {
					// 받은 resp가 true면
					ui.pnSignup.tfId.setText("");
					// 아이디 입력부분 비워준다.
					ui.pnSignup.tfpw.setText("");
					// 비밀번호 입력부분 비워준다.
					ui.pnSignup.tfname.setText("");
					// 이름 입력부분 비워준다.
					ui.pnSignup.tfrpw.setText("");
					// 비밀번호 재입력부분 비워준다.
					JOptionPane.showMessageDialog(ui, "아이디가 생성되었습니다.");
					// 로그인 페이지로 이동.
					ui.setSize(496, 748);
					ui.pnLogin.tfid.setText(nick);
					// 로그인 페이지
					ui.setTitle("Drop the beat!! - Login");
					// 로그인 페이지 타이틀 설정
					ui.setContentPane(ui.pnLogin);
					// 로그인 페이지 띄워준다.

				} else {
					// false면
					JOptionPane.showMessageDialog(ui, data[1]);
					// false#data[1]을 찍어준다.
				}

			} catch (ClassNotFoundException | IOException e) {
				System.out.println("[client] network error " + e.toString());
			}
		}
	}
	
	// 로그인 요청
	public void sendLoginRequest(String nick, String pass) {
		// 로그인창의 nick과 pass를 받아서
		this.nick = nick;
		// 이 클래스 nick에 nick을 저장하고
		String resp = null;
		// 변수 생성
		System.out.println("[client] request : ");
		if (nick.trim().equals("") || pass.trim().equals("")) {
			// nick이 없거나, pass가 없으면 들어온다.
			JOptionPane.showMessageDialog(ui, "아이디와 비밀번호를 입력하세요.");
			/// JOptionPane 띄워준다.
			return;
			// 메소드 처음으로 돌아간다.
		}
		synchronized (oos) {
			// 동기화
			try {
				oos.writeObject("join#" + nick + "#" + pass);
				// [TCP] 서버로 mode와 nick과 pass를 보내준다. 
				resp = (String) ois.readObject();
				
				System.out.println("[client] response : " + resp);

				// 여기서 ui 제어.
				String[] data = resp.split("#");
				// 리턴값이 true#, false#으로 나오고
				if (data[0].equals("true")) {
					// true 면
					System.out.println("come");
					ui.pnLogin.tfid.setText("");
					// 로그인화면의 아이디부분 지워주고
					ui.pnLogin.tfpw.setText("");
					// 로그인화면의 pw부분 지워주고
					ui.setSize(800, 800);
					// 사이즈 설정
					ui.setTitle("Drop the beat!! - Wating Room");
					// 타이틀 설정
					ui.setLocationRelativeTo(null);
					// 가운데 화면 띄우기
					ui.setContentPane(ui.pnRoom);
					// pnRoom 띄운다.
					sendStateRoomRequest();
					// sendStateRoomRequest수행[방상태]
					sendMessageStateRequest();
					// sendMessageStateRequest수행[메세지]
				} else {
					// false 면
					ui.pnLogin.tfid.setText("");
					// 로그인화면의 아이디부분 지워주고
					ui.pnLogin.tfpw.setText("");
					// 로그인화면의 pw부분 지워주고
					JOptionPane.showMessageDialog(ui, data[1]);
					// false#~의 ~부분 JOption으로 띄워준다.
				}

			} catch (ClassNotFoundException | IOException e) {
				System.out.println("[client] network error " + e.toString());
			}
		}
	}
	
	// 방 나갈 때 or 추방당했을 때
	public void sendLeaveRequest() {
		String resp = null;
		// 변수 설정
		synchronized (oos) {
			// 동기화
			try {
				System.out.println("방 나갔음.");
				oos.writeObject("leave");
				// [TCP] 서버로 mode 보냄
				resp = (String) ois.readObject();
				// 무조건 NULL 받음
				if (resp == null) {
					// null로 들어와서
					ui.setSize(800, 800);
					// 크기설정
					ui.setTitle("Drop the beat!! - Wating Room");
					// 타이틀 설정
					ui.setLocationRelativeTo(null);
					// 가운데 띄우고
					ui.setContentPane(ui.pnRoom);
					// pnRoom 띄워주고
					sendUserListRequest();
					// 유저목록 리스트 재설정
					ui.pnMusicChoice.p.selectedMusic.close();
					// 틀어져있는 뮤직 끄고
				}
			} catch (ClassNotFoundException | IOException e) {
			}
		}
	}
	
	// 로그인 유저 목록과 내 정보 갱신 or 변경
	public void sendUserListRequest() {
		Set<Account> resp = null;
		// Set 컬렉션 생성
		synchronized (oos) {
			// 동기화
			try {
				oos.writeObject("get");
				// [TCP] 서버로 mode를 보내준다. 
				resp = (Set<Account>) ois.readObject();
				// Set형태로 현재유저목록을 받는다.
				System.out.println(resp);
				String[] ar = new String[resp.size()];
				// 현재 접속중인 유저 목록 size만큼 배열을 만들고
				int i = 0;
				for (Account a : resp) {
					// 현재접속중인만큼 a에 넣으면서 확인
					if(a.getNick().equals(nick)) {
						// 만약 현재접속중인 유저들을 확인하면서 자신의 nick과 같은 지점에
						user = a;
						// user에 a 넣고 ?
						ui.pnRoom.pnInfo.lbId.setText(a.getNick());
						// 방리스트 화면의 유저상태창에 닉을 써준다.
						ui.pnRoom.pnInfo.lbState.setText(a.tell());
						// 방리스트 화면의 유저상태창에 정보를 써준다.
					}
					ar[i++] = a.toString();
					// 현재 접속중인 유저를 ar배열에 저장
				}
				ui.pnRoom.userList.setListData(ar);
				// userList목록에 띄워준다.
			} catch (Exception e) {
				System.out.println(e.toString());
			}

		}
	}
	
	// 방 목록 불러오기 or 갱신
	public void sendStateRoomRequest() {
		
		List<Room> resp = null;
		// resp 생성
		synchronized (oos) {
			// 동기화
			try {
				oos.writeObject("roomlist");
				// [TCP] 서버로 roomlist mode를 보내주고
				resp = (List<Room>) ois.readObject();
				// 서버로부터 룸을 받아서
				int i = 0;
				// 변서 설정
				System.out.println(resp);
				if (!resp.isEmpty()) {
					// 룸이 비어있지 않으면 들어온다. 룸이 하나라도 있으면
					do {
						// 생성된 룸하나씩 살펴본다.
						System.out.println("룸 !null");
						ui.pnRoom.btList.get(i).setText("");
						// 룸마다 TEXT 비워주고
						ui.pnRoom.btList.get(i).setEnabled(true);
						// 룸 클릭버튼 활성화 무조건 있으니깐 없으면 룸사이즈가 나가짐
						ui.pnRoom.btList.get(i).setText("<html>제목 : " +resp.get(i).getTitle() + "<br/>" + "방장 : " + resp.get(i).getJoiner().get(0).getNick() + "<br/>" + "인원 : " + resp.get(i).getJoiner().size() + " / 2" + "<br/>" + "암호방 : " + (resp.get(i).getPass().equals("") ? "NO" : "YES") + "<br/>" + "방 상태 : " + (resp.get(i).isGameStart() ? "게임 중.." : "대기 중..")+  "</html>");               
						// 룸 버튼에 정보 띄워주고
						if(resp.get(i).getJoiner().size() == 2) {
							// 만약 풀방이라면
							ui.pnRoom.btList.get(i).setEnabled(false);
							// 버튼 비활성화
						}
						if(!resp.get(i).isTwoUserRoom()) {
							// 2인용이 아니라면
							ui.pnRoom.btList.get(i).setText("<html>제목 : " +resp.get(i).getTitle() + "<br/>" + "방장 : " + resp.get(i).getJoiner().get(0).getNick() + "<br/>" + "인원 : " + resp.get(i).getJoiner().size() + " / 1" + "<br/>" + "암호방 : " + (resp.get(i).getPass().equals("") ? "NO" : "YES") + "<br/>" + "방 상태 : " + (resp.get(i).isGameStart() ? "게임 중.." : "대기 중..")+  "</html>");
							// 정보띄워주고
							ui.pnRoom.btList.get(i).setEnabled(false);
							// 버튼 비활성화
						}
						i++;
					} while (i < resp.size());
					// 생성된 룸하나씩 체크한다.
				}
				while (i < 8) {
					// 만약 최종적으로 i가 8보다 작았다면
					System.out.println("룸 null");
					ui.pnRoom.btList.get(i).setEnabled(false);
					// 생성된 맨 마지막방 뒤에 전부 버튼 비활성화하고
					ui.pnRoom.btList.get(i).setText("");
					// 텍스트 지움
					i++;
					// 하나씩 설정
				}

			} catch (ClassNotFoundException | IOException e) {
			}
		}
	}
	
	// 로그아웃시에 처리될 동작
	public void sendLogoutRequest() {
		// 로그아웃 받음
		boolean resp = false;
		// 변수 설정
		synchronized (oos) {
			// 동기화
			try {
				oos.writeObject("logout");
				// [TCP] logout 보냄
				resp = (Boolean) ois.readObject();
				// 로그아웃되면 true 아니면 false
				if (resp == true) {
					// 로그아웃 됬다면
					JOptionPane.showMessageDialog(ui, "로그아웃되었습니다.");
					// 창 띄워주고
					ui.setSize(496, 748);
					// 크기 설정
					ui.setTitle("Drop the beat!! - Login");
					// 타이틀 설정
					ui.setLocationRelativeTo(null);
					// 가운데 설정
					ui.setContentPane(ui.pnLogin);
					// 로그인 패널 띄워줌
				} else {

				}
			} catch (ClassNotFoundException | IOException e) {
			}
		}
	}


	
	// 방 입장
	public void sendEnterRoomRequest(int idx) {
		// 몇번방인지 받아서
		synchronized (oos) {
			// 동기화
			try {
				System.out.println("enter" + idx);
				oos.writeObject("enterroom#" + idx);
				/// [TCP] 서버로 mode와 idx를 보내준다. 
				Room resp = (Room) ois.readObject();
				// 그 방을 받아서
				if (!(resp == null)) {		// 비번방이 아니고 성공시
					// 그 방이 빈방이 아니면 들어온다.
					ui.setSize(1400, 730);
					// 사이즈 설정
					ui.pnMusicChoice = new MusicChoicePanel(ui);
					// MusicChoicePanel 생성
					ui.setTitle("Drop the beat!! - Room");
					// 타이틀 설정
					ui.setContentPane(ui.pnMusicChoice);
					// MusicChoicePanel 띄우고
					sendChatRequest(" 입장했습니다.");
					// 상대와 나한테 ~가 입장했습니다 띄워주고
					ui.pnMusicChoice.lbCrownRight.setIcon(new ImageIcon(getClass().getClassLoader().getResource("clientPanel/imge/crown1.png")));
					// [자기자신은 항상 왼쪽임] 왕관을 오른쪽에 띄워주고
					ui.pnMusicChoice.btReady.setEnabled(true);
					// 레디버튼 활성화
					ui.pnMusicChoice.btStart.setEnabled(false);
					// 스타트 비활성화
					ui.pnMusicChoice.btInvite.setEnabled(false);
					// 초대버튼 비활성화
					ui.pnMusicChoice.btExpel.setEnabled(false);
					// 강퇴버튼 비활성화
					ui.pnMusicChoice.tfusernick2.setText(resp.getJoiner().get(0).getNick());
					// [자기자신은 항상 왼쪽이므로] 방장은 오른쪽에 닉 띄워주고
					ui.pnMusicChoice.tfuserinfo2.setText(resp.getJoiner().get(0).tell());
					// 방장은 오른쪽에 정보 띄워주고
					ui.pnMusicChoice.tfusernick1.setText(resp.getJoiner().get(1).getNick());
					// 자기자신은 왼쪽에 닉
					ui.pnMusicChoice.tfuserinfo1.setText(resp.getJoiner().get(1).tell());
					// 자기자신은 왼쪽이 정보
					ui.pnMusicChoice.p.leftButton.setEnabled(false);
					// 방장아니므로 곡 선택 x
					ui.pnMusicChoice.p.rightButton.setEnabled(false);
					// 방장아니므로 곡 선택 x
					sendRoomUserInfoRequest();
					// 룸 안의 유저들 상태를 재세팅
					ui.pnMusicChoice.p.selectedMusic.close();
					// ui.pnMusicChoice.p.selectedMusic 닫고
					ui.pnMusicChoice.p.nowSelected = resp.getSelectMusic();
					// 현재곡에 방에 선택된 곡을 저장
					ui.pnMusicChoice.p.selectTrack(resp.getSelectMusic());
					// 트렉에 저장해서 띄워줌 ?? 뮤직틈
					ui.pnMusicChoice.btRoomSetting.setEnabled(false);
					// 룸 재세팅 비활성화
					ui.pnMusicChoice.p.isRoomScreen = true;
					// ??
					
					} else {		// 비번방일 때
						String pw = JOptionPane.showInputDialog("비밀번호를 입력하세요.");
						// 비밀번호 입력창 띄우고 입력값을 pw에 저장
						if (!(pw.equals(""))) {
							// 비밀번호가 "" 아니라면
								oos.writeObject("secretroom#" + pw);
								// [TCP] 서버로 mode와 pw 보내고
								resp = (Room) ois.readObject();
								// 그방을 받거나[입장됨] null[비번다름]을 받음
								if(resp != null) {	// 입장 성공시
									ui.setSize(1400, 730);
									// 크기 설정
									ui.pnMusicChoice = new MusicChoicePanel(ui);
									// MusicChoicePanel 생성
									ui.setTitle("Drop the beat!! - Room");
									// 타이틀 설정
									ui.setContentPane(ui.pnMusicChoice);
									// pnMusicChoice 띄워주고
									System.out.println("sendChat 호출");
									sendChatRequest("이/가 입장했습니다.");
									// 방안에 사람들에게 전부 띄워주고 [닉] 가 입장했습니다.
									ui.pnMusicChoice.btReady.setEnabled(true);
									// 레디버튼 활성화
									ui.pnMusicChoice.btStart.setEnabled(false);
									// 스타트 비활성화
									ui.pnMusicChoice.btInvite.setEnabled(false);
									// 초대 비활성화
									ui.pnMusicChoice.btExpel.setEnabled(false);
									// 강퇴 비활성화
									ui.pnMusicChoice.lbCrownRight.setIcon(new ImageIcon(getClass().getClassLoader().getResource("clientPanel/imge/crown1.png")));
									ui.pnMusicChoice.tfusernick2.setText(resp.getJoiner().get(0).getNick());
									// 들어온 입장이므로 무조건 오른쪽에 방장 띄우고 [자신은 왼쪽임]
									ui.pnMusicChoice.tfuserinfo2.setText(resp.getJoiner().get(0).tell());
									// 들어온 입장이므로 무조건 오른쪽에 방장 띄우고 [자신은 왼쪽임]
									ui.pnMusicChoice.tfusernick1.setText(resp.getJoiner().get(1).getNick());
									// 왼쪽에 자기자신을 띄운다.
									ui.pnMusicChoice.tfuserinfo1.setText(resp.getJoiner().get(1).tell());
									// 왼쪽에 자기자신을 띄운다.
									ui.pnMusicChoice.p.leftButton.setEnabled(false);
									// 곡선택 불가
									ui.pnMusicChoice.p.rightButton.setEnabled(false);
									// 곡선택 불가
									ui.pnMusicChoice.btRoomSetting.setEnabled(false);
									// 룸 재세팅 불가
									sendRoomUserInfoRequest();
									// 룸안의 유저의 정보를 재세팅
									ui.pnMusicChoice.p.selectedMusic.close();
									// ui.pnMusicChoice.p.selectedMusic 끄고
									ui.pnMusicChoice.p.nowSelected = resp.getSelectMusic();
									// 현재곡에 방에 선택된 곡을 저장
									ui.pnMusicChoice.p.selectTrack(resp.getSelectMusic());
									// 트렉에 저장해서 띄워줌 틀어줌
									ui.pnMusicChoice.p.isRoomScreen = true;
									// ??
								 
								}else {
									// 비밀번호가 다를 시
									JOptionPane.showMessageDialog(ui, "비밀번호가 틀렸습니다.");
								}
							} else {
								// 비밀번호를 입력안했을 시
								JOptionPane.showMessageDialog(ui, "비밀번호를 입력해주세요.");
							}
						}
				}catch (ClassNotFoundException | IOException e) {
					System.out.println(e.toString());
			}
		}
	}
	
	// 방 만들기 
	public void sendCreateRoomRequest(String title, String pw, boolean twouser) {
		// 타이틀과 pw, twouser를 받아서(1인용인지 2인용인지)
		Room resp = null;
		// Room 변수 생성
		synchronized (oos) {
			// 동기화
			try {
				oos.writeObject("createroom#" + title + "#" + twouser + "#" + pw);
				// [TCP] 서버로 타이틀 2인용인지 1인용인지 비밀번호 보내준다.
				resp = (Room) ois.readObject();
				System.out.println(resp);

				if (resp == null) {
					// 만약 방이 꽉차있다면
					JOptionPane.showMessageDialog(ui, "이미 모든 방이 생성되어있습니다.");
					// 꽉차있다고 띄워준다.
				} else if(!twouser){
					// 방생성 가능하고 1인용이라면
					ui.setSize(1400, 730);
					// 사이즈 설정
					ui.pnMusicChoice = new MusicChoicePanel(ui);
					// MusicChoicePanel 생성
					ui.setTitle("Drop the beat!! - Room(" + title + ")");
					// 타이틀 설정
					ui.setContentPane(ui.pnMusicChoice);
					// MusicChoicePanel 띄워주고
					ui.pnMusicChoice.btReady.setEnabled(false);
					// 레디 비활성화
					ui.pnMusicChoice.btStart.setEnabled(true);
					// 스타트 활성화
					ui.pnMusicChoice.tfusernick1.setText(resp.getJoiner().get(0).getNick());
					// 왼쪽에 방장 Nick 띄워준다.
					ui.pnMusicChoice.tfuserinfo1.setText(resp.getJoiner().get(0).tell());
					// 왼쪽에 방장 정보 띄워준다.
					ui.pnMusicChoice.tfusernick2.setText("사용자 없음");
					// 오른쪽에 사용자 없음[굳이 할필요없음] ??
					ui.pnMusicChoice.lbCrownLeft.setIcon(new ImageIcon(getClass().getClassLoader().getResource("clientPanel/imge/crown1.png")));
					// 왼쪽에 왕관 아이콘 설정
					ui.pnMusicChoice.rightPanel.setVisible(false);
					// 오른쪽 상태창 패널 없애주고
					ui.pnMusicChoice.p.leftButton.setEnabled(true);
					// 곡선택 버튼 활성화
					ui.pnMusicChoice.p.rightButton.setEnabled(true);
					// 곡선택 버튼 활성화
					ui.pnMusicChoice.btInvite.setEnabled(false);
					// 초대 기능 비활성화
					ui.pnMusicChoice.p.isRoomScreen = true;
					// ?? 그냥 설정??
				} else {
					// 2인용이라면
					ui.setSize(1400, 730);
					// 사이즈 설정
					ui.pnMusicChoice = new MusicChoicePanel(ui);
					// MusicChoicePanel 생성
					ui.setTitle("Drop the beat!! - Room(" + title + ")");
					// 타이틀 설정
					ui.setContentPane(ui.pnMusicChoice);
					// MusicChoicePanel 띄워주고
					ui.pnMusicChoice.btReady.setEnabled(false);
					// 레디 비활성화
					ui.pnMusicChoice.btStart.setEnabled(false);
					// 스타트 비활성화
					ui.pnMusicChoice.tfusernick1.setText(resp.getJoiner().get(0).getNick());
					// 방생성한사람이 방장이니까 방장닉 왼쪽에 띄워주고
					ui.pnMusicChoice.tfuserinfo1.setText(resp.getJoiner().get(0).tell());
					// 왼쪽에 방장 정보 띄워주고
					ui.pnMusicChoice.tfusernick2.setText("사용자 없음");
					// 오른쪽 사용자 없음
					ui.pnMusicChoice.p.leftButton.setEnabled(true);
					// 곡선택 활성화
					ui.pnMusicChoice.p.rightButton.setEnabled(true);
					// 곡선택 활성화
					ui.pnMusicChoice.lbCrownLeft.setIcon(new ImageIcon(getClass().getClassLoader().getResource("clientPanel/imge/crown1.png")));
					// 왕관 왼쪽에 띄워주고
					ui.pnMusicChoice.btExpel.setEnabled(false);
					// 강퇴기능 비활성화
					ui.pnMusicChoice.p.isRoomScreen = true;
					//  ?? 그냥 설정??
				}
			} catch (ClassNotFoundException | IOException e) {
				System.out.println(e.toString());
			}
		}
	}
	
	// 방장이 바뀌었는지 확인
	public void sendCheckCreaterRequest() {
		// 상대방이 나갔을 때 check함
		synchronized (oos) {
			try {
				oos.writeObject("check");
				// [TCP] 서버로 mode 보냄
			} catch (IOException e) {
				System.out.println(e.toString());
			}
		}
	}
	
	// 채팅 보내기
	public void sendChatRequest(String txt) {
		// txt에 입력한 txt를 받아서
		String resp = null;
		// 변수 생성
		synchronized (oos) {
			// 동기화
			try {
				oos.writeObject("chat#" + txt);
				// [TCP] 서버에 mode와 txt를 보내준다.
				ui.pnMusicChoice.txInput.setText("");
				// 채팅입력창 비워주고
			} catch (IOException e) {
				System.out.println(e.toString());
			}
		}
	}
	
	// 준비 상태 보내기
	public void sendReadyRequest() {
		// 준비 액션리스터 받아서
		synchronized (oos) {
			try {
				// 동기화
				if (ui.pnMusicChoice.btReady.isSelected()) {
					// 레디버튼이 눌러져있다면
					oos.writeObject("ready");
					// [TCP] 레디를 보내줌
					sendChatRequest("Ready");
					// 레디했다고 Chat으로 띄워줌
				} else {
					oos.writeObject("readycancel");
					// 레디버튼 안눌렀으면
					sendChatRequest("Ready Cancel");
					// 레디캔슬됬다고 chat 띄워줌
				}
			} catch (IOException e) {
				System.out.println(e.toString());
			}
		}
	}
	
	// 방 입장시 상대방에게 정보 갱신 요청
	public void sendRoomUserInfoRequest() {
		synchronized (oos) {
			// 동기화
			try {
				oos.writeObject("roomuserinfo");
				// [TCP] 서버로 mode 보냄
			} catch (IOException e) {
				System.out.println(e.toString());
			}
		}
	}
	
	// 게임이 끝나고 유저 정보 갱신
	public void sendReRoomUserInfoRequest() {
		synchronized (oos) {
			try {
				oos.writeObject("reroomuserinfo");
				Room ac = (Room) ois.readObject();
				if (ac.getJoiner().get(0).getNick().equals(ui.pnMusicChoice.tfusernick1.getText())) {
					ui.pnMusicChoice.tfuserinfo1.setText(ac.getJoiner().get(0).tell());
				} else{
					ui.pnMusicChoice.tfuserinfo1.setText(ac.getJoiner().get(1).tell());
				}
				if (ac.getJoiner().get(0).getNick().equals(ui.pnMusicChoice.tfusernick2.getText())) {
					ui.pnMusicChoice.tfuserinfo2.setText(ac.getJoiner().get(0).tell());
					System.out.println("1 " + ac.getJoiner().get(0).tell());
				} else{
					ui.pnMusicChoice.tfuserinfo2.setText(ac.getJoiner().get(1).tell());
					System.out.println("2 " + ac.getJoiner().get(1).tell());
				}
			} catch (IOException | ClassNotFoundException e) {
				System.out.println(e.toString());
			}
		}
	}
	
	// 게임 시작했을 때
	public void sendStartGameRequest() {
		// 게임버튼 누르고 시작했을 때
		int num = ui.pnMusicChoice.p.nowSelected;
		// 지금 선택된 곡 num으로 저장
		synchronized (oos) {
			// 동기화
			try {
				oos.writeObject("startgame#" + num);
				// [TCP]로 게임시작 알리고 
			} catch (IOException e) {
				System.out.println(e.toString());
			}
		}
	}
	
	// 방에서 선택 뮤직 변경시
	public void sendChangeMusicRequest(String txt) {
		synchronized (oos) {
			try {
				System.out.println("곡 바뀜 보냄");
				oos.writeObject(txt);
			} catch (IOException e) {
				System.out.println(e.toString());
			}
		}
	}
	
	// 실시간 점수 갱신
	public void sendScoreRequest(int score) {
		synchronized (oos) {
			try {
				oos.writeObject("score#" + score);
			} catch (IOException e) {
				System.out.println(e.toString());
			}
		}
	}
	
	// 게임 결과 요청
	public void sendGameResultRequest(int result) {
		synchronized (oos) {
			try {
				oos.writeObject("gameresult#" + result);
			} catch (IOException e) {
				System.out.println(e.toString());
			}
		}
	}
	
	// 빠른 입장 요청
	public void sendQuickRoomRequest() {
		// 빠른입장버튼이 눌려졌을 때
		synchronized (oos) {
			// 동기화
			try {
				oos.writeObject("QuickRoom");
				// [TCP]로 mode 보냄ㄱ
				int resp = (Integer)ois.readObject();
				// 들어갈수있는 방 d, 못들어가면 -1을 받아서
				if(resp<0) {
					JOptionPane.showMessageDialog(ui, "현재 참가할 방이 없습니다.");
				} else {
					sendEnterRoomRequest(resp);
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}
	
	// 메세지 보내기 요청
	public void sendMessageRequest(String name, String ment) {
		// 수신할 name과 ment 가져와서
		synchronized (oos) {
			// 동기화
			try {
				oos.writeObject("sendMessage#" + name + "#" + ment);
				// [TCP] mode와 name, ment 서버로 보내고
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}
	
	// 메세지 갯수 갱신
	public void sendMessageStateRequest() {
		synchronized (oos) {
			// 동기화
			try {
				oos.writeObject("notreadingmessage");
				// [TCP]로 notreadingmessage 보냄
				int l = (Integer)ois.readObject();
				// 읽지 않은 개수 받아서 l에 저장
				ui.pnRoom.pnInfo.btMessage.setText(String.valueOf(l));
				// 버튼에 띄워줌
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}
	
	// 메세지 목록 요청
	public void sendShowMessageListRequest() {
		synchronized (oos) {
			// 동기화
			try {
				oos.writeObject("messagelist");
				// [TCP] MODE 보내고
				List<Message> resp = (List<Message>)ois.readObject();
				// List로 받아서
				if(resp != null) {
					// 메세지가 있다면
					String[] ar = new String[resp.size()];
					// 메세지 사이즈를 ar에 넣음
					int i = 0;
					for (Message a : resp) {
						// 메세지를 하나씩 확인할꺼
						ar[i++] = (a.isRead() ? "○" : "     ") + "  [ " + a.sendToString() + " " + (a.sendToString().equals("수신") ? "◀" : "▶") + " ] " + a.getName() + "                                      " + a.getDate();  
						// 수신했는지 안했는지, 누가 보냈는지, 누가보냈는지 날짜를 각각넣음
					}
					ui.m.messageList.setListData(ar);
					// 그리고 메세지 리스트에 띄워줌
				}
			} catch (Exception e) {
				System.out.println(e.toString());
				System.out.println(e.getStackTrace());
			}
		}
	}
	
	// 읽은 메세지 상태 변경
	public void sendCheckReadMessageRequest(int idx) {
		// 선택된 메시지 idx를 받아와서
		synchronized (oos) {
			// 동기화
			try {
				oos.writeObject("checkreadmessage#" + idx);
				// [TCP] idx값 보냄
				boolean l = (Boolean)ois.readObject();
				// 안읽었으면 true 읽었으면 false
				if(l) {
					// 안읽었다면
					sendMessageStateRequest();
					// 읽음처리했으니 리스트 정리 다시해줌
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}
	
	// 읽을 메세지 불러옴
	public Message sendShowMessageRequest(int idx) {
		// 선택된 메세지 idx를 받아서
		synchronized (oos) {
			// 동기화
			try {
				oos.writeObject("readmessage#" + idx);
				// idx를 보내주고
				Message m = (Message)ois.readObject();
				// 메세지 객체로 받아서
				return m;
				// 다시 ShowMessageHandler로 리턴 시킴
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}
		}
	}
	
	// 초대 가능한 유저 리스트 호출
	public void sendInviteUserListRequest() {
		synchronized (oos) {
			// 동기화
			try {
				oos.writeObject("availableinviteuser");
				// [TCP] 초대 MODE 보내고
				List<Account> resp = (List<Account>)ois.readObject();
				// 방에 없는 유저 찾고
				InviteUsetListFrame n = new InviteUsetListFrame();
				// InviteUsetListFrame 생성
				n.setSize(400, 500);
				// 크기 설정
				n.setLocationRelativeTo(null);
				// 가운데 띄우고
				String[] ar = new String[resp.size()];
				// 방에 안들어가있는사람 size만큼 ar에 넣어서
				int i = 0;
				for (Account a : resp) {
					// 그사람만큼 확인하겠다
					ar[i++] = a.toString();
					// ar에 정보를 넣고
				}
				n.setVisible(true);
				// InviteUsetListFrame 띄워주고
				n.userList.setListData(ar);
				// 리스트 띄워줌
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}
	
	// 유저에게 초대메세지 보내기
	public void sendinviteRequest(String user) {
		// nick을 받아서
		synchronized (oos) {
			// 동기화
			try {
				oos.writeObject("inviteuser#" + user);
				// [TCP]로 유저 보내고
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}
	
	// 강퇴하기
	public void sendExpelRequest() {
		// 강퇴 버튼 클릭 시
		synchronized (oos) {
			// 동기화
			try {
				String name = ui.pnMusicChoice.tfusernick2.getText();
				// 상대편 닉을 name에 저장하고
				oos.writeObject("expeluser#" + name);
				// [TCP]로 보냄
				ui.pnMusicChoice.txArea.append("[시스템] " + name + "님을 강퇴하였습니다.\n");
				// 강퇴 띄워줌.
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}
	
	// 1인용 게임 결과전달
	public void sendSingleGameResultRequest(int score) {
		synchronized (oos) {
			try {
				oos.writeObject("singlegameresult#" + score);
				Account b = (Account)ois.readObject();
				if(b!=null) {
					ui.pnMusicChoice.tfuserinfo1.setText(b.tell());
				} 
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}
	
	// 방 설정 변경 요청
	public void sendChangeRoomSettingRequest(String title, String pw, boolean twouser) {
		synchronized (oos) {
			try {
				oos.writeObject("changeroomsetting#" + title + "#" + twouser + "#" + pw);
				if(twouser) {
					ui.pnMusicChoice.btReady.setEnabled(false);
					ui.pnMusicChoice.btStart.setEnabled(false);
					ui.pnMusicChoice.btInvite.setEnabled(true);
					ui.pnMusicChoice.rightPanel.setVisible(true);
					ui.setTitle("Drop the beat!! - Room(" + title + ")");
				} else {
					ui.pnMusicChoice.btReady.setEnabled(false);
					ui.pnMusicChoice.btStart.setEnabled(true);
					ui.pnMusicChoice.btInvite.setEnabled(false);
					ui.pnMusicChoice.rightPanel.setVisible(false);
					ui.setTitle("Drop the beat!! - Room(" + title + ")");
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}
	
}