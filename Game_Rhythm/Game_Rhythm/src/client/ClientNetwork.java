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
	private Socket soc; // �ٽ� ���� ����
	private ObjectOutputStream oos = null;
	private ObjectInputStream ois = null;

	private DatagramSocket ds; // ���� ����(receive��)
	private ClientUI ui;
	private Account user;
	private String nick;	// user�� �����ϱ� ���� nick

	public ClientNetwork(ClientUI c) {
		this.ui = c;
		System.out.println("������");
		try {
			soc = new Socket(c.ip, 56789);
			System.out.println("??");
			oos = new ObjectOutputStream(soc.getOutputStream());
			ois = new ObjectInputStream(soc.getInputStream());
			System.out.println("[client] connected to server");
			ds = new DatagramSocket(soc.getLocalPort()); // // TCP�� UDP�� ���� ��Ʈ�� ����� �� ����.

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
				case "userListChange":	// �α��� ���� ���
					// UDP�� �����κ��� �޾Ƽ� 
					sendUserListRequest();
					// sendUserListRequest ����
					break;
				case "changerooms":		// �� ��� �ֽ�ȭ
					sendStateRoomRequest();
					// sendStateRoomRequest ����
					break;
				case "changechat":		// ���� ä�� ����
					// changechat#[nick] : txt�� �޾Ƽ�
					System.out.println(str[1]);
					String txt = str[1];
					// [nick] : txt�� txt�� ����
					ui.pnMusicChoice.txArea.append(txt + "\n");
					// textArea�� ����ش�.
					break;
				case "changecreater":	// ������ �ٲ� ���
					ui.pnMusicChoice.lbCrownRight.setIcon(null);
					// �����ʿ� �հ� �̹��� ���ְ�
					ui.pnMusicChoice.lbCrownLeft.setIcon(new ImageIcon(getClass().getClassLoader().getResource("clientPanel/imge/crown1.png")));
					// ���ʿ� �̹��� ��� [�ڱ��ڽ��� ������]
					ui.pnMusicChoice.btReady.setEnabled(false);
					// ���� ��Ȱ��ȭ
					ui.pnMusicChoice.btStart.setEnabled(false);
					// ��ŸƮ Ȱ��ȭ
					ui.pnMusicChoice.p.leftButton.setEnabled(true);
					// ��� ����
					ui.pnMusicChoice.p.rightButton.setEnabled(true);
					// ��� ����
					ui.pnMusicChoice.btInvite.setEnabled(true);
					// �ʴ� Ȱ��ȭ
					ui.pnMusicChoice.btExpel.setEnabled(false);
					// ���� Ȱ��ȭ ? ������ ������ ���� check��
					ui.pnMusicChoice.btRoomSetting.setEnabled(true);
					// �� �缼�� Ȱ��ȭ
					break;
				case "readysuccess":	// ���� �غ� �Ϸ��
					// ������
					System.out.println("����");
					ui.pnMusicChoice.p.leftButton.setEnabled(false);
					// ���� ��� ����
					ui.pnMusicChoice.p.rightButton.setEnabled(false);
					// ���� ��� ����
					ui.pnMusicChoice.btReady.setEnabled(false);
					// ���� ���� ��ư false
					ui.pnMusicChoice.btStart.setEnabled(true);
					// ��ŸƮ��ư Ȱ��ȭ
					ui.pnMusicChoice.btRoomSetting.setEnabled(false);
					// �� �缼�� ����
					break;
				case "readycancel":		// ���� �غ� ��ҽ�
					ui.pnMusicChoice.p.leftButton.setEnabled(true);
					// [������ ����] ��ð���
					ui.pnMusicChoice.p.rightButton.setEnabled(true);
					// [������ ����] ��ð���
					ui.pnMusicChoice.btReady.setEnabled(false);
					// ���� ��Ȱ��ȭ
					ui.pnMusicChoice.btStart.setEnabled(false);
					// ��ŸƮ ��Ȱ��ȭ
					ui.pnMusicChoice.btRoomSetting.setEnabled(false);
					// �� �缼�� ��Ȱ��ȭ
					break;
				case "roominfo":	// ������ ������ �� [�ڽ��� ������]
					System.out.println("roominfo");
					ui.pnMusicChoice.tfusernick2.setText(str[1]);
					// �����ʿ� ���� ������ ���
					ui.pnMusicChoice.tfuserinfo2.setText(str[2]);
					// �����ʿ� ���� ������ ���
					ui.pnMusicChoice.btInvite.setEnabled(false);
					// �ʴ��� ��Ȱ��ȭ
					ui.pnMusicChoice.btExpel.setEnabled(true);
					// ������ Ȱ��ȭ
					ui.pnMusicChoice.btRoomSetting.setEnabled(false);
					// �� �缼�� ��Ȱ��ȭ
					break;
				case "reroominfo":	// ������ ������ �� 
					System.out.println("������ ������@@@@@@@");
					// �׹濡 ���� �� ������ ����
					ui.pnMusicChoice.tfusernick2.setText("???");
					// �����ʿ� ����� ����
					ui.pnMusicChoice.tfuserinfo2.setText("");
					// ������ ��������
					ui.pnMusicChoice.btInvite.setEnabled(true);
					// �ʴ� Ȱ��ȭ
					ui.pnMusicChoice.btExpel.setEnabled(false);
					// ������ false
					ui.pnMusicChoice.lbCrownRight.setIcon(null);
					// �����ʿ� �հ������
					ui.pnMusicChoice.lbCrownLeft.setIcon(new ImageIcon(getClass().getClassLoader().getResource("clientPanel/imge/crown1.png")));
					// ���ʿ� �հ� ����
					System.out.println("��Ȱ��ȭ ����?");
					// ���� ��Ȱ��ȭ
					ui.pnMusicChoice.btRoomSetting.setEnabled(true);
					// �� �缼�� ����
//					sendCheckCreaterRequest();
					// ���� �ٲ������ Ȯ��
					break;
				case "startgame":	// ���� ���۽� ���� ������ ȣ��
					ui.pnMusicChoice.p.isRoomScreen = false;
					// ??
					GameFrame g = new GameFrame(ui);
					// GameFrame ����
					ui.pnMusicChoice.p.selectedMusic.close();
					// ui.pnMusicChoice.p.selectedMusic �ݰ�[���ӽ���������]
					if(str[2].equals("1")) {
						// ������ 1�̸�
						g.twouser = false;
						// twouser�� false
					} else {
						// ������ 2�̸�
						g.twouser = true;
						// twouser�� true�� �ٲٰ�
					}
					g.setLocationRelativeTo(null);
					// ��� ����
					g.setTitle("Drop the beat!! - Game");
					// Ÿ��Ʋ ����
					g.setVisible(true);
					// ���������� ����
					ui.setVisible(false);
					// ui����
					break;
				case "right":	// �� ���� ������ �̵�
					System.out.println("�� �ٲ� ����");
					ui.pnMusicChoice.p.selectRight();
					break;
				case "left":	// �� ���� ���� �̵�
					ui.pnMusicChoice.p.selectLeft();
					break;
				case "score":	// ���� ���ھ� �޾Ƽ� ����
					String score = str[1];
					GameFrame.game.vsScore = Integer.valueOf(score);
					break;
				case "gameresultSetted":	// ���浵 ������ ������ �� �濡 ���� ����
					sendReRoomUserInfoRequest();
					break;
				case "sendmessage":			// ���� �޾��� �� ���� ����
					sendMessageStateRequest();
					// sendMessageStateRequest ����
					break;
				case "resetmessage":		// ���� ����Ʈ ����
					sendShowMessageListRequest();
					// sendShowMessageListRequest ����
					break;
				case "invite":				// �ʴ� �޾��� ��
					int idx = JOptionPane.showConfirmDialog(ui, str[1] + "���� �ʴ��ϼ̽��ϴ�. \n �����Ͻðڽ��ϱ�?" );
					// ������� nick�� �Բ� ����ְ� �̰� idx�� ������
					if(idx == 0) {
						// idx�� 0�̸� [����������]
						sendStateRoomRequest();
						// ����� üũ���ְ�
						if(!(ui.pnRoom.btList.get(Integer.valueOf(str[2])).isEnabled())) {
							// ���� �ʴ���� ���� �������� ������
							JOptionPane.showMessageDialog(ui, "���� �� ã���ϴ�.");
							// JOption ����ְ�
							return;
							// �޼ҵ� ����
						}
						sendEnterRoomRequest(Integer.valueOf(str[2]));
						// �� ������ ��
					}
					break;
				case "expel":				// ���� ���� ��
					// �̰� �޾Ƽ�
					sendLeaveRequest();
					// �� ������ �ǰ�
					JOptionPane.showMessageDialog(ui, "������Ͽ����ϴ�.");
					// JOption �����
					break;
				}
			} catch (IOException e) {
				System.out.println("dp failed .. " + e.toString());
				ds.close();
				break;
			}

		}
	}
	
	// ȸ������
	public void sendCreateRequest(String nick, String pass, String name, String repass) {
		// nick, pass, name, repass�� ����
		String resp = null;
		// ������ ���� String ����
		System.out.println(" [client] request : ");
		if (nick.trim().equals("") || pass.trim().equals("")) {
			// ���� nick�� ���ų� pass�� ���ٸ� ���´�. 
			JOptionPane.showMessageDialog(ui, "���̵�� ��й�ȣ�� �Է��ϼ���.");
			// JOption �޼��� ����ش�.
			return;
			// �޼ҵ� ó������ ���ư���.
		}
		if (!pass.equals(repass)) {
			// ���� ��й�ȣ��, ���й�ȣ�� ��ġ���� �ʴ´ٸ� ���´�
			JOptionPane.showMessageDialog(ui, "��й�ȣ�� Ȯ���ϼ���");
			// JOptionPane ����ش�.
			return;
			// �޼ҵ� ó������ ���ư���.
		}
		if (!ui.pnSignup.rbAgree.isSelected()) {
			// ȸ�������� ���ǹ�ư�� �ȴ����������� ���´�.
			JOptionPane.showMessageDialog(ui, "����� �а� �������ּ���.");
			// JOptionPane ����ش�.
			return;
			// �޼ҵ� ó������ ���ư���.
		}
		synchronized (oos) {
			// ����ȭ
			try {
				oos.writeObject("create#" + nick + "#" + pass + "#" + name);
				// [TCP] ������ mode�� nick�� pass, name�� �����ش�.
				resp = (String) ois.readObject();
				// �����κ��� true, false �ް�
				System.out.println("[client] response : " + resp);
				String[] data = resp.split("#");
				// ���� resp�� split���� ��´�.
				
				// ���⼭ ui ����.
				if (data[0].equals("true")) {
					// ���� resp�� true��
					ui.pnSignup.tfId.setText("");
					// ���̵� �Էºκ� ����ش�.
					ui.pnSignup.tfpw.setText("");
					// ��й�ȣ �Էºκ� ����ش�.
					ui.pnSignup.tfname.setText("");
					// �̸� �Էºκ� ����ش�.
					ui.pnSignup.tfrpw.setText("");
					// ��й�ȣ ���Էºκ� ����ش�.
					JOptionPane.showMessageDialog(ui, "���̵� �����Ǿ����ϴ�.");
					// �α��� �������� �̵�.
					ui.setSize(496, 748);
					ui.pnLogin.tfid.setText(nick);
					// �α��� ������
					ui.setTitle("Drop the beat!! - Login");
					// �α��� ������ Ÿ��Ʋ ����
					ui.setContentPane(ui.pnLogin);
					// �α��� ������ ����ش�.

				} else {
					// false��
					JOptionPane.showMessageDialog(ui, data[1]);
					// false#data[1]�� ����ش�.
				}

			} catch (ClassNotFoundException | IOException e) {
				System.out.println("[client] network error " + e.toString());
			}
		}
	}
	
	// �α��� ��û
	public void sendLoginRequest(String nick, String pass) {
		// �α���â�� nick�� pass�� �޾Ƽ�
		this.nick = nick;
		// �� Ŭ���� nick�� nick�� �����ϰ�
		String resp = null;
		// ���� ����
		System.out.println("[client] request : ");
		if (nick.trim().equals("") || pass.trim().equals("")) {
			// nick�� ���ų�, pass�� ������ ���´�.
			JOptionPane.showMessageDialog(ui, "���̵�� ��й�ȣ�� �Է��ϼ���.");
			/// JOptionPane ����ش�.
			return;
			// �޼ҵ� ó������ ���ư���.
		}
		synchronized (oos) {
			// ����ȭ
			try {
				oos.writeObject("join#" + nick + "#" + pass);
				// [TCP] ������ mode�� nick�� pass�� �����ش�. 
				resp = (String) ois.readObject();
				
				System.out.println("[client] response : " + resp);

				// ���⼭ ui ����.
				String[] data = resp.split("#");
				// ���ϰ��� true#, false#���� ������
				if (data[0].equals("true")) {
					// true ��
					System.out.println("come");
					ui.pnLogin.tfid.setText("");
					// �α���ȭ���� ���̵�κ� �����ְ�
					ui.pnLogin.tfpw.setText("");
					// �α���ȭ���� pw�κ� �����ְ�
					ui.setSize(800, 800);
					// ������ ����
					ui.setTitle("Drop the beat!! - Wating Room");
					// Ÿ��Ʋ ����
					ui.setLocationRelativeTo(null);
					// ��� ȭ�� ����
					ui.setContentPane(ui.pnRoom);
					// pnRoom ����.
					sendStateRoomRequest();
					// sendStateRoomRequest����[�����]
					sendMessageStateRequest();
					// sendMessageStateRequest����[�޼���]
				} else {
					// false ��
					ui.pnLogin.tfid.setText("");
					// �α���ȭ���� ���̵�κ� �����ְ�
					ui.pnLogin.tfpw.setText("");
					// �α���ȭ���� pw�κ� �����ְ�
					JOptionPane.showMessageDialog(ui, data[1]);
					// false#~�� ~�κ� JOption���� ����ش�.
				}

			} catch (ClassNotFoundException | IOException e) {
				System.out.println("[client] network error " + e.toString());
			}
		}
	}
	
	// �� ���� �� or �߹������ ��
	public void sendLeaveRequest() {
		String resp = null;
		// ���� ����
		synchronized (oos) {
			// ����ȭ
			try {
				System.out.println("�� ������.");
				oos.writeObject("leave");
				// [TCP] ������ mode ����
				resp = (String) ois.readObject();
				// ������ NULL ����
				if (resp == null) {
					// null�� ���ͼ�
					ui.setSize(800, 800);
					// ũ�⼳��
					ui.setTitle("Drop the beat!! - Wating Room");
					// Ÿ��Ʋ ����
					ui.setLocationRelativeTo(null);
					// ��� ����
					ui.setContentPane(ui.pnRoom);
					// pnRoom ����ְ�
					sendUserListRequest();
					// ������� ����Ʈ �缳��
					ui.pnMusicChoice.p.selectedMusic.close();
					// Ʋ�����ִ� ���� ����
				}
			} catch (ClassNotFoundException | IOException e) {
			}
		}
	}
	
	// �α��� ���� ��ϰ� �� ���� ���� or ����
	public void sendUserListRequest() {
		Set<Account> resp = null;
		// Set �÷��� ����
		synchronized (oos) {
			// ����ȭ
			try {
				oos.writeObject("get");
				// [TCP] ������ mode�� �����ش�. 
				resp = (Set<Account>) ois.readObject();
				// Set���·� ������������� �޴´�.
				System.out.println(resp);
				String[] ar = new String[resp.size()];
				// ���� �������� ���� ��� size��ŭ �迭�� �����
				int i = 0;
				for (Account a : resp) {
					// �����������θ�ŭ a�� �����鼭 Ȯ��
					if(a.getNick().equals(nick)) {
						// ���� ������������ �������� Ȯ���ϸ鼭 �ڽ��� nick�� ���� ������
						user = a;
						// user�� a �ְ� ?
						ui.pnRoom.pnInfo.lbId.setText(a.getNick());
						// �渮��Ʈ ȭ���� ��������â�� ���� ���ش�.
						ui.pnRoom.pnInfo.lbState.setText(a.tell());
						// �渮��Ʈ ȭ���� ��������â�� ������ ���ش�.
					}
					ar[i++] = a.toString();
					// ���� �������� ������ ar�迭�� ����
				}
				ui.pnRoom.userList.setListData(ar);
				// userList��Ͽ� ����ش�.
			} catch (Exception e) {
				System.out.println(e.toString());
			}

		}
	}
	
	// �� ��� �ҷ����� or ����
	public void sendStateRoomRequest() {
		
		List<Room> resp = null;
		// resp ����
		synchronized (oos) {
			// ����ȭ
			try {
				oos.writeObject("roomlist");
				// [TCP] ������ roomlist mode�� �����ְ�
				resp = (List<Room>) ois.readObject();
				// �����κ��� ���� �޾Ƽ�
				int i = 0;
				// ���� ����
				System.out.println(resp);
				if (!resp.isEmpty()) {
					// ���� ������� ������ ���´�. ���� �ϳ��� ������
					do {
						// ������ ���ϳ��� ���캻��.
						System.out.println("�� !null");
						ui.pnRoom.btList.get(i).setText("");
						// �븶�� TEXT ����ְ�
						ui.pnRoom.btList.get(i).setEnabled(true);
						// �� Ŭ����ư Ȱ��ȭ ������ �����ϱ� ������ ������ ������
						ui.pnRoom.btList.get(i).setText("<html>���� : " +resp.get(i).getTitle() + "<br/>" + "���� : " + resp.get(i).getJoiner().get(0).getNick() + "<br/>" + "�ο� : " + resp.get(i).getJoiner().size() + " / 2" + "<br/>" + "��ȣ�� : " + (resp.get(i).getPass().equals("") ? "NO" : "YES") + "<br/>" + "�� ���� : " + (resp.get(i).isGameStart() ? "���� ��.." : "��� ��..")+  "</html>");               
						// �� ��ư�� ���� ����ְ�
						if(resp.get(i).getJoiner().size() == 2) {
							// ���� Ǯ���̶��
							ui.pnRoom.btList.get(i).setEnabled(false);
							// ��ư ��Ȱ��ȭ
						}
						if(!resp.get(i).isTwoUserRoom()) {
							// 2�ο��� �ƴ϶��
							ui.pnRoom.btList.get(i).setText("<html>���� : " +resp.get(i).getTitle() + "<br/>" + "���� : " + resp.get(i).getJoiner().get(0).getNick() + "<br/>" + "�ο� : " + resp.get(i).getJoiner().size() + " / 1" + "<br/>" + "��ȣ�� : " + (resp.get(i).getPass().equals("") ? "NO" : "YES") + "<br/>" + "�� ���� : " + (resp.get(i).isGameStart() ? "���� ��.." : "��� ��..")+  "</html>");
							// ��������ְ�
							ui.pnRoom.btList.get(i).setEnabled(false);
							// ��ư ��Ȱ��ȭ
						}
						i++;
					} while (i < resp.size());
					// ������ ���ϳ��� üũ�Ѵ�.
				}
				while (i < 8) {
					// ���� ���������� i�� 8���� �۾Ҵٸ�
					System.out.println("�� null");
					ui.pnRoom.btList.get(i).setEnabled(false);
					// ������ �� �������� �ڿ� ���� ��ư ��Ȱ��ȭ�ϰ�
					ui.pnRoom.btList.get(i).setText("");
					// �ؽ�Ʈ ����
					i++;
					// �ϳ��� ����
				}

			} catch (ClassNotFoundException | IOException e) {
			}
		}
	}
	
	// �α׾ƿ��ÿ� ó���� ����
	public void sendLogoutRequest() {
		// �α׾ƿ� ����
		boolean resp = false;
		// ���� ����
		synchronized (oos) {
			// ����ȭ
			try {
				oos.writeObject("logout");
				// [TCP] logout ����
				resp = (Boolean) ois.readObject();
				// �α׾ƿ��Ǹ� true �ƴϸ� false
				if (resp == true) {
					// �α׾ƿ� ��ٸ�
					JOptionPane.showMessageDialog(ui, "�α׾ƿ��Ǿ����ϴ�.");
					// â ����ְ�
					ui.setSize(496, 748);
					// ũ�� ����
					ui.setTitle("Drop the beat!! - Login");
					// Ÿ��Ʋ ����
					ui.setLocationRelativeTo(null);
					// ��� ����
					ui.setContentPane(ui.pnLogin);
					// �α��� �г� �����
				} else {

				}
			} catch (ClassNotFoundException | IOException e) {
			}
		}
	}


	
	// �� ����
	public void sendEnterRoomRequest(int idx) {
		// ��������� �޾Ƽ�
		synchronized (oos) {
			// ����ȭ
			try {
				System.out.println("enter" + idx);
				oos.writeObject("enterroom#" + idx);
				/// [TCP] ������ mode�� idx�� �����ش�. 
				Room resp = (Room) ois.readObject();
				// �� ���� �޾Ƽ�
				if (!(resp == null)) {		// ������� �ƴϰ� ������
					// �� ���� ����� �ƴϸ� ���´�.
					ui.setSize(1400, 730);
					// ������ ����
					ui.pnMusicChoice = new MusicChoicePanel(ui);
					// MusicChoicePanel ����
					ui.setTitle("Drop the beat!! - Room");
					// Ÿ��Ʋ ����
					ui.setContentPane(ui.pnMusicChoice);
					// MusicChoicePanel ����
					sendChatRequest(" �����߽��ϴ�.");
					// ���� ������ ~�� �����߽��ϴ� ����ְ�
					ui.pnMusicChoice.lbCrownRight.setIcon(new ImageIcon(getClass().getClassLoader().getResource("clientPanel/imge/crown1.png")));
					// [�ڱ��ڽ��� �׻� ������] �հ��� �����ʿ� ����ְ�
					ui.pnMusicChoice.btReady.setEnabled(true);
					// �����ư Ȱ��ȭ
					ui.pnMusicChoice.btStart.setEnabled(false);
					// ��ŸƮ ��Ȱ��ȭ
					ui.pnMusicChoice.btInvite.setEnabled(false);
					// �ʴ��ư ��Ȱ��ȭ
					ui.pnMusicChoice.btExpel.setEnabled(false);
					// �����ư ��Ȱ��ȭ
					ui.pnMusicChoice.tfusernick2.setText(resp.getJoiner().get(0).getNick());
					// [�ڱ��ڽ��� �׻� �����̹Ƿ�] ������ �����ʿ� �� ����ְ�
					ui.pnMusicChoice.tfuserinfo2.setText(resp.getJoiner().get(0).tell());
					// ������ �����ʿ� ���� ����ְ�
					ui.pnMusicChoice.tfusernick1.setText(resp.getJoiner().get(1).getNick());
					// �ڱ��ڽ��� ���ʿ� ��
					ui.pnMusicChoice.tfuserinfo1.setText(resp.getJoiner().get(1).tell());
					// �ڱ��ڽ��� ������ ����
					ui.pnMusicChoice.p.leftButton.setEnabled(false);
					// ����ƴϹǷ� �� ���� x
					ui.pnMusicChoice.p.rightButton.setEnabled(false);
					// ����ƴϹǷ� �� ���� x
					sendRoomUserInfoRequest();
					// �� ���� ������ ���¸� �缼��
					ui.pnMusicChoice.p.selectedMusic.close();
					// ui.pnMusicChoice.p.selectedMusic �ݰ�
					ui.pnMusicChoice.p.nowSelected = resp.getSelectMusic();
					// ���� �濡 ���õ� ���� ����
					ui.pnMusicChoice.p.selectTrack(resp.getSelectMusic());
					// Ʈ���� �����ؼ� ����� ?? ����ƴ
					ui.pnMusicChoice.btRoomSetting.setEnabled(false);
					// �� �缼�� ��Ȱ��ȭ
					ui.pnMusicChoice.p.isRoomScreen = true;
					// ??
					
					} else {		// ������� ��
						String pw = JOptionPane.showInputDialog("��й�ȣ�� �Է��ϼ���.");
						// ��й�ȣ �Է�â ���� �Է°��� pw�� ����
						if (!(pw.equals(""))) {
							// ��й�ȣ�� "" �ƴ϶��
								oos.writeObject("secretroom#" + pw);
								// [TCP] ������ mode�� pw ������
								resp = (Room) ois.readObject();
								// �׹��� �ްų�[�����] null[����ٸ�]�� ����
								if(resp != null) {	// ���� ������
									ui.setSize(1400, 730);
									// ũ�� ����
									ui.pnMusicChoice = new MusicChoicePanel(ui);
									// MusicChoicePanel ����
									ui.setTitle("Drop the beat!! - Room");
									// Ÿ��Ʋ ����
									ui.setContentPane(ui.pnMusicChoice);
									// pnMusicChoice ����ְ�
									System.out.println("sendChat ȣ��");
									sendChatRequest("��/�� �����߽��ϴ�.");
									// ��ȿ� ����鿡�� ���� ����ְ� [��] �� �����߽��ϴ�.
									ui.pnMusicChoice.btReady.setEnabled(true);
									// �����ư Ȱ��ȭ
									ui.pnMusicChoice.btStart.setEnabled(false);
									// ��ŸƮ ��Ȱ��ȭ
									ui.pnMusicChoice.btInvite.setEnabled(false);
									// �ʴ� ��Ȱ��ȭ
									ui.pnMusicChoice.btExpel.setEnabled(false);
									// ���� ��Ȱ��ȭ
									ui.pnMusicChoice.lbCrownRight.setIcon(new ImageIcon(getClass().getClassLoader().getResource("clientPanel/imge/crown1.png")));
									ui.pnMusicChoice.tfusernick2.setText(resp.getJoiner().get(0).getNick());
									// ���� �����̹Ƿ� ������ �����ʿ� ���� ���� [�ڽ��� ������]
									ui.pnMusicChoice.tfuserinfo2.setText(resp.getJoiner().get(0).tell());
									// ���� �����̹Ƿ� ������ �����ʿ� ���� ���� [�ڽ��� ������]
									ui.pnMusicChoice.tfusernick1.setText(resp.getJoiner().get(1).getNick());
									// ���ʿ� �ڱ��ڽ��� ����.
									ui.pnMusicChoice.tfuserinfo1.setText(resp.getJoiner().get(1).tell());
									// ���ʿ� �ڱ��ڽ��� ����.
									ui.pnMusicChoice.p.leftButton.setEnabled(false);
									// ��� �Ұ�
									ui.pnMusicChoice.p.rightButton.setEnabled(false);
									// ��� �Ұ�
									ui.pnMusicChoice.btRoomSetting.setEnabled(false);
									// �� �缼�� �Ұ�
									sendRoomUserInfoRequest();
									// ����� ������ ������ �缼��
									ui.pnMusicChoice.p.selectedMusic.close();
									// ui.pnMusicChoice.p.selectedMusic ����
									ui.pnMusicChoice.p.nowSelected = resp.getSelectMusic();
									// ���� �濡 ���õ� ���� ����
									ui.pnMusicChoice.p.selectTrack(resp.getSelectMusic());
									// Ʈ���� �����ؼ� ����� Ʋ����
									ui.pnMusicChoice.p.isRoomScreen = true;
									// ??
								 
								}else {
									// ��й�ȣ�� �ٸ� ��
									JOptionPane.showMessageDialog(ui, "��й�ȣ�� Ʋ�Ƚ��ϴ�.");
								}
							} else {
								// ��й�ȣ�� �Է¾����� ��
								JOptionPane.showMessageDialog(ui, "��й�ȣ�� �Է����ּ���.");
							}
						}
				}catch (ClassNotFoundException | IOException e) {
					System.out.println(e.toString());
			}
		}
	}
	
	// �� ����� 
	public void sendCreateRoomRequest(String title, String pw, boolean twouser) {
		// Ÿ��Ʋ�� pw, twouser�� �޾Ƽ�(1�ο����� 2�ο�����)
		Room resp = null;
		// Room ���� ����
		synchronized (oos) {
			// ����ȭ
			try {
				oos.writeObject("createroom#" + title + "#" + twouser + "#" + pw);
				// [TCP] ������ Ÿ��Ʋ 2�ο����� 1�ο����� ��й�ȣ �����ش�.
				resp = (Room) ois.readObject();
				System.out.println(resp);

				if (resp == null) {
					// ���� ���� �����ִٸ�
					JOptionPane.showMessageDialog(ui, "�̹� ��� ���� �����Ǿ��ֽ��ϴ�.");
					// �����ִٰ� ����ش�.
				} else if(!twouser){
					// ����� �����ϰ� 1�ο��̶��
					ui.setSize(1400, 730);
					// ������ ����
					ui.pnMusicChoice = new MusicChoicePanel(ui);
					// MusicChoicePanel ����
					ui.setTitle("Drop the beat!! - Room(" + title + ")");
					// Ÿ��Ʋ ����
					ui.setContentPane(ui.pnMusicChoice);
					// MusicChoicePanel ����ְ�
					ui.pnMusicChoice.btReady.setEnabled(false);
					// ���� ��Ȱ��ȭ
					ui.pnMusicChoice.btStart.setEnabled(true);
					// ��ŸƮ Ȱ��ȭ
					ui.pnMusicChoice.tfusernick1.setText(resp.getJoiner().get(0).getNick());
					// ���ʿ� ���� Nick ����ش�.
					ui.pnMusicChoice.tfuserinfo1.setText(resp.getJoiner().get(0).tell());
					// ���ʿ� ���� ���� ����ش�.
					ui.pnMusicChoice.tfusernick2.setText("����� ����");
					// �����ʿ� ����� ����[���� ���ʿ����] ??
					ui.pnMusicChoice.lbCrownLeft.setIcon(new ImageIcon(getClass().getClassLoader().getResource("clientPanel/imge/crown1.png")));
					// ���ʿ� �հ� ������ ����
					ui.pnMusicChoice.rightPanel.setVisible(false);
					// ������ ����â �г� �����ְ�
					ui.pnMusicChoice.p.leftButton.setEnabled(true);
					// ��� ��ư Ȱ��ȭ
					ui.pnMusicChoice.p.rightButton.setEnabled(true);
					// ��� ��ư Ȱ��ȭ
					ui.pnMusicChoice.btInvite.setEnabled(false);
					// �ʴ� ��� ��Ȱ��ȭ
					ui.pnMusicChoice.p.isRoomScreen = true;
					// ?? �׳� ����??
				} else {
					// 2�ο��̶��
					ui.setSize(1400, 730);
					// ������ ����
					ui.pnMusicChoice = new MusicChoicePanel(ui);
					// MusicChoicePanel ����
					ui.setTitle("Drop the beat!! - Room(" + title + ")");
					// Ÿ��Ʋ ����
					ui.setContentPane(ui.pnMusicChoice);
					// MusicChoicePanel ����ְ�
					ui.pnMusicChoice.btReady.setEnabled(false);
					// ���� ��Ȱ��ȭ
					ui.pnMusicChoice.btStart.setEnabled(false);
					// ��ŸƮ ��Ȱ��ȭ
					ui.pnMusicChoice.tfusernick1.setText(resp.getJoiner().get(0).getNick());
					// ������ѻ���� �����̴ϱ� ����� ���ʿ� ����ְ�
					ui.pnMusicChoice.tfuserinfo1.setText(resp.getJoiner().get(0).tell());
					// ���ʿ� ���� ���� ����ְ�
					ui.pnMusicChoice.tfusernick2.setText("����� ����");
					// ������ ����� ����
					ui.pnMusicChoice.p.leftButton.setEnabled(true);
					// ��� Ȱ��ȭ
					ui.pnMusicChoice.p.rightButton.setEnabled(true);
					// ��� Ȱ��ȭ
					ui.pnMusicChoice.lbCrownLeft.setIcon(new ImageIcon(getClass().getClassLoader().getResource("clientPanel/imge/crown1.png")));
					// �հ� ���ʿ� ����ְ�
					ui.pnMusicChoice.btExpel.setEnabled(false);
					// ������ ��Ȱ��ȭ
					ui.pnMusicChoice.p.isRoomScreen = true;
					//  ?? �׳� ����??
				}
			} catch (ClassNotFoundException | IOException e) {
				System.out.println(e.toString());
			}
		}
	}
	
	// ������ �ٲ������ Ȯ��
	public void sendCheckCreaterRequest() {
		// ������ ������ �� check��
		synchronized (oos) {
			try {
				oos.writeObject("check");
				// [TCP] ������ mode ����
			} catch (IOException e) {
				System.out.println(e.toString());
			}
		}
	}
	
	// ä�� ������
	public void sendChatRequest(String txt) {
		// txt�� �Է��� txt�� �޾Ƽ�
		String resp = null;
		// ���� ����
		synchronized (oos) {
			// ����ȭ
			try {
				oos.writeObject("chat#" + txt);
				// [TCP] ������ mode�� txt�� �����ش�.
				ui.pnMusicChoice.txInput.setText("");
				// ä���Է�â ����ְ�
			} catch (IOException e) {
				System.out.println(e.toString());
			}
		}
	}
	
	// �غ� ���� ������
	public void sendReadyRequest() {
		// �غ� �׼Ǹ����� �޾Ƽ�
		synchronized (oos) {
			try {
				// ����ȭ
				if (ui.pnMusicChoice.btReady.isSelected()) {
					// �����ư�� �������ִٸ�
					oos.writeObject("ready");
					// [TCP] ���� ������
					sendChatRequest("Ready");
					// �����ߴٰ� Chat���� �����
				} else {
					oos.writeObject("readycancel");
					// �����ư �ȴ�������
					sendChatRequest("Ready Cancel");
					// ����ĵ����ٰ� chat �����
				}
			} catch (IOException e) {
				System.out.println(e.toString());
			}
		}
	}
	
	// �� ����� ���濡�� ���� ���� ��û
	public void sendRoomUserInfoRequest() {
		synchronized (oos) {
			// ����ȭ
			try {
				oos.writeObject("roomuserinfo");
				// [TCP] ������ mode ����
			} catch (IOException e) {
				System.out.println(e.toString());
			}
		}
	}
	
	// ������ ������ ���� ���� ����
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
	
	// ���� �������� ��
	public void sendStartGameRequest() {
		// ���ӹ�ư ������ �������� ��
		int num = ui.pnMusicChoice.p.nowSelected;
		// ���� ���õ� �� num���� ����
		synchronized (oos) {
			// ����ȭ
			try {
				oos.writeObject("startgame#" + num);
				// [TCP]�� ���ӽ��� �˸��� 
			} catch (IOException e) {
				System.out.println(e.toString());
			}
		}
	}
	
	// �濡�� ���� ���� �����
	public void sendChangeMusicRequest(String txt) {
		synchronized (oos) {
			try {
				System.out.println("�� �ٲ� ����");
				oos.writeObject(txt);
			} catch (IOException e) {
				System.out.println(e.toString());
			}
		}
	}
	
	// �ǽð� ���� ����
	public void sendScoreRequest(int score) {
		synchronized (oos) {
			try {
				oos.writeObject("score#" + score);
			} catch (IOException e) {
				System.out.println(e.toString());
			}
		}
	}
	
	// ���� ��� ��û
	public void sendGameResultRequest(int result) {
		synchronized (oos) {
			try {
				oos.writeObject("gameresult#" + result);
			} catch (IOException e) {
				System.out.println(e.toString());
			}
		}
	}
	
	// ���� ���� ��û
	public void sendQuickRoomRequest() {
		// ���������ư�� �������� ��
		synchronized (oos) {
			// ����ȭ
			try {
				oos.writeObject("QuickRoom");
				// [TCP]�� mode ������
				int resp = (Integer)ois.readObject();
				// �����ִ� �� d, ������ -1�� �޾Ƽ�
				if(resp<0) {
					JOptionPane.showMessageDialog(ui, "���� ������ ���� �����ϴ�.");
				} else {
					sendEnterRoomRequest(resp);
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}
	
	// �޼��� ������ ��û
	public void sendMessageRequest(String name, String ment) {
		// ������ name�� ment �����ͼ�
		synchronized (oos) {
			// ����ȭ
			try {
				oos.writeObject("sendMessage#" + name + "#" + ment);
				// [TCP] mode�� name, ment ������ ������
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}
	
	// �޼��� ���� ����
	public void sendMessageStateRequest() {
		synchronized (oos) {
			// ����ȭ
			try {
				oos.writeObject("notreadingmessage");
				// [TCP]�� notreadingmessage ����
				int l = (Integer)ois.readObject();
				// ���� ���� ���� �޾Ƽ� l�� ����
				ui.pnRoom.pnInfo.btMessage.setText(String.valueOf(l));
				// ��ư�� �����
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}
	
	// �޼��� ��� ��û
	public void sendShowMessageListRequest() {
		synchronized (oos) {
			// ����ȭ
			try {
				oos.writeObject("messagelist");
				// [TCP] MODE ������
				List<Message> resp = (List<Message>)ois.readObject();
				// List�� �޾Ƽ�
				if(resp != null) {
					// �޼����� �ִٸ�
					String[] ar = new String[resp.size()];
					// �޼��� ����� ar�� ����
					int i = 0;
					for (Message a : resp) {
						// �޼����� �ϳ��� Ȯ���Ҳ�
						ar[i++] = (a.isRead() ? "��" : "     ") + "  [ " + a.sendToString() + " " + (a.sendToString().equals("����") ? "��" : "��") + " ] " + a.getName() + "                                      " + a.getDate();  
						// �����ߴ��� ���ߴ���, ���� ���´���, �������´��� ��¥�� ��������
					}
					ui.m.messageList.setListData(ar);
					// �׸��� �޼��� ����Ʈ�� �����
				}
			} catch (Exception e) {
				System.out.println(e.toString());
				System.out.println(e.getStackTrace());
			}
		}
	}
	
	// ���� �޼��� ���� ����
	public void sendCheckReadMessageRequest(int idx) {
		// ���õ� �޽��� idx�� �޾ƿͼ�
		synchronized (oos) {
			// ����ȭ
			try {
				oos.writeObject("checkreadmessage#" + idx);
				// [TCP] idx�� ����
				boolean l = (Boolean)ois.readObject();
				// ���о����� true �о����� false
				if(l) {
					// ���о��ٸ�
					sendMessageStateRequest();
					// ����ó�������� ����Ʈ ���� �ٽ�����
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}
	
	// ���� �޼��� �ҷ���
	public Message sendShowMessageRequest(int idx) {
		// ���õ� �޼��� idx�� �޾Ƽ�
		synchronized (oos) {
			// ����ȭ
			try {
				oos.writeObject("readmessage#" + idx);
				// idx�� �����ְ�
				Message m = (Message)ois.readObject();
				// �޼��� ��ü�� �޾Ƽ�
				return m;
				// �ٽ� ShowMessageHandler�� ���� ��Ŵ
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}
		}
	}
	
	// �ʴ� ������ ���� ����Ʈ ȣ��
	public void sendInviteUserListRequest() {
		synchronized (oos) {
			// ����ȭ
			try {
				oos.writeObject("availableinviteuser");
				// [TCP] �ʴ� MODE ������
				List<Account> resp = (List<Account>)ois.readObject();
				// �濡 ���� ���� ã��
				InviteUsetListFrame n = new InviteUsetListFrame();
				// InviteUsetListFrame ����
				n.setSize(400, 500);
				// ũ�� ����
				n.setLocationRelativeTo(null);
				// ��� ����
				String[] ar = new String[resp.size()];
				// �濡 �ȵ��ִ»�� size��ŭ ar�� �־
				int i = 0;
				for (Account a : resp) {
					// �׻����ŭ Ȯ���ϰڴ�
					ar[i++] = a.toString();
					// ar�� ������ �ְ�
				}
				n.setVisible(true);
				// InviteUsetListFrame ����ְ�
				n.userList.setListData(ar);
				// ����Ʈ �����
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}
	
	// �������� �ʴ�޼��� ������
	public void sendinviteRequest(String user) {
		// nick�� �޾Ƽ�
		synchronized (oos) {
			// ����ȭ
			try {
				oos.writeObject("inviteuser#" + user);
				// [TCP]�� ���� ������
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}
	
	// �����ϱ�
	public void sendExpelRequest() {
		// ���� ��ư Ŭ�� ��
		synchronized (oos) {
			// ����ȭ
			try {
				String name = ui.pnMusicChoice.tfusernick2.getText();
				// ����� ���� name�� �����ϰ�
				oos.writeObject("expeluser#" + name);
				// [TCP]�� ����
				ui.pnMusicChoice.txArea.append("[�ý���] " + name + "���� �����Ͽ����ϴ�.\n");
				// ���� �����.
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}
	
	// 1�ο� ���� �������
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
	
	// �� ���� ���� ��û
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