package client;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import clientPanel.LoginPanel;
import clientPanel.MessageListFrame;
import clientPanel.MusicChoicePanel;
import clientPanel.PnInfoPanel;
import clientPanel.RoomPanel;
import clientPanel.SignupPanel;
import clientPanel.WelcomePanel;
import handler.BtCreateRoomHandler;
import handler.BtEnterRoomHandler;
import handler.BtExitHandler;
import handler.BtListSendMessageHandler;
import handler.BtLogOutHandler;
import handler.BtLoginHandler;
import handler.BtQuickRoomHandler;
import handler.BtShowMessageListHandler;
import handler.BtSignUpCancelHandler;
import handler.BtSignupFinishHandler;
import handler.BtSingnUpHandler;

public class ClientUI extends JFrame{
	public static final int NOTE_SPEED=4;
	public static final int NOTE_SLEEP =10;
	public static final int REACH_TIME=1;
	public LoginPanel pnLogin;
	static public ClientNetwork net;
	public MusicChoicePanel pnMusicChoice;
	public RoomPanel pnRoom;
	public SignupPanel pnSignup;
	public MessageListFrame m;
	public PnInfoPanel pnInfo;
	String ip;
	
	public ClientUI(String ip) {
		this.ip = ip;
		setUIcomponent();
		addListeners();
		net = new ClientNetwork(this);
	}


	private void addListeners() {
		ActionListener bsh = new BtSingnUpHandler(this);
		pnLogin.btsign.addActionListener(bsh);
		
		ActionListener bsfh = new BtSignupFinishHandler(this);
		pnSignup.btCreate.addActionListener(bsfh);
		
		ActionListener bch = new BtSignUpCancelHandler(this);
		pnSignup.btCancel.addActionListener(bch);
		
		ActionListener blgh = new BtLoginHandler(this);
		pnLogin.btlogin.addActionListener(blgh);
		
		ActionListener bloh = new BtLogOutHandler(this);
		pnRoom.btLogOut.addActionListener(bloh);
		
		for(int i=0;i<=7;i++) {
			ActionListener berh = new BtEnterRoomHandler(this);
			pnRoom.btList.get(i).addActionListener(berh);
		}
		
		ActionListener bcrh = new BtCreateRoomHandler(this);
		pnRoom.btCreateRoom.addActionListener(bcrh);
		
		
		pnLogin.tfpw.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode() == 10) {
					pnLogin.btlogin.doClick();
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		ActionListener beh = new BtExitHandler(this);
		pnRoom.btExit.addActionListener(beh);
		
		ActionListener bqrh =  new BtQuickRoomHandler(this);
		pnRoom.btQuickStart.addActionListener(bqrh);
		
		MouseListener blmh = new BtListSendMessageHandler(this);
		pnRoom.userList.addMouseListener(blmh);
		
		ActionListener bsmlh = new BtShowMessageListHandler(this);
		pnInfo.btMessage.addActionListener(bsmlh);
		
		
	
	}

	

	private void setUIcomponent() {
		setTitle("Drop the beat!! - Welcome");
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				int rand = (int)(Math.random()*8) *30;
				setLocation(300 + rand, 200+ rand);
				setSize(496, 748);
				
				WelcomePanel w = new WelcomePanel();
				setLocationRelativeTo(null);
				System.out.println(w);
				setVisible(true);
				setContentPane(w);
				System.out.println("w È£Ãâ");
			}
		});
		t.start();
		try {
		Thread.sleep(1500);
		}catch(Exception e) {
			
		}
		t.interrupt();
		
		setTitle("Drop the beat!! - Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(496, 748);
		m = new MessageListFrame();
		pnLogin = new LoginPanel();
		pnSignup = new SignupPanel();
		pnInfo = new PnInfoPanel();
		pnRoom = new RoomPanel(this);
		setContentPane(pnLogin);
	}
}
