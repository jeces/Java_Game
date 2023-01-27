package clientPanel;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import client.ClientUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.net.URL;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

import java.awt.Color;
import javax.swing.border.LineBorder;

public class RoomPanel extends JPanel {
	public JButton btCreateRoom;
	public JButton btLogOut;
	
	
	public List<JButton> btList;
	public JList userList;
	public PnInfoPanel pnInfo;
	public JTextField textField;
	public JButton btExit;
	public JButton btQuickStart;
	private Graphics screenGraphic;
	private Image panelImage;
	private Image selectedImage ;
	private Image backgroundImage = new ImageIcon(getClass().getResource("imge/roomImage.jpg")).getImage();
	private JLabel lbInfoPicture;
	

	@SuppressWarnings("unchecked")
	public RoomPanel(ClientUI c) {
		setBackground(new Color(51, 0, 0));
		setSize(800, 800);
		setLayout(null);
		
		btList = new ArrayList<>();
		pnInfo = c.pnInfo;
		add(pnInfo);
		pnInfo.setBounds(34, 62, 200, 139);
		
		lbInfoPicture = new JLabel("");
		URL url3 = getClass().getResource("imge/mypsa4.png");
		lbInfoPicture.setBounds(74, 23, 50, 50);
		lbInfoPicture.setIcon(new ImageIcon(url3));
		pnInfo.add(lbInfoPicture);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(34, 211, 200, 548);
		add(scrollPane);
		
		userList = new JList();
			userList.setCellRenderer(new DefaultListCellRenderer() {
				@Override
				public int getHorizontalAlignment() {
					// TODO Auto-generated method stub
					return CENTER;
				}
			});
			
		scrollPane.setViewportView(userList);
		
		btCreateRoom = new JButton("");
		URL url2 = getClass().getResource("imge/CreateRoom.jpg");
		btCreateRoom = new JButton("");
		btCreateRoom.setBorder(new LineBorder(new Color(0, 0, 0)));
		btCreateRoom.setBorderPainted(false);
		btCreateRoom.setFocusPainted(false);
		btCreateRoom.setBackground(new Color(255, 0, 0, 0));
		btCreateRoom.setIcon(new ImageIcon(url2));
		btCreateRoom.setBounds(269, 62, 155, 71);
		add(btCreateRoom);
		
		
		
		btLogOut = new JButton("L O G O U T");
		btLogOut.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 18));
		btLogOut.setBackground(Color.RED);
		btLogOut.setBounds(608, 100, 149, 33);
		add(btLogOut);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.CYAN);
		panel.setBounds(269, 143, 488, 616);
		add(panel);
		panel.setPreferredSize(new Dimension(488, 464));
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		btExit = new JButton("E X I T");
		btExit.setBackground(Color.RED);
		btExit.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 18));
		btExit.setBounds(608, 62, 149, 32);
		add(btExit);
		
		URL url1 = getClass().getResource("imge/QuickStart.jpg");
			btQuickStart = new JButton("");
			btQuickStart.setBorder(new LineBorder(new Color(0, 0, 0)));
			btQuickStart.setBorderPainted(false);
			btQuickStart.setFocusPainted(false);
			btQuickStart.setBackground(new Color(255, 0, 0, 0));
			btQuickStart.setBounds(430, 62, 149, 71);
			btQuickStart.setIcon(new ImageIcon(url1));
			add(btQuickStart);
		
		for(int i=1;i<=8;i++) {
			JButton bt = new JButton("");
			bt.setHorizontalAlignment(SwingConstants.CENTER);
			bt.setFont(new Font("³ª´®°íµñÄÚµù", Font.BOLD, 12));
			bt.setEnabled(false);
			bt.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			bt.setFont((new Font("ÇÔÃÊ·Ò¹ÙÅÁ", Font.BOLD | Font.ITALIC, 15)));
			panel.add(bt);
			btList.add(bt);
		}
		btExit.setFocusPainted(false);
		btLogOut.setFocusPainted(false);
		
		
		
		
	}
	public void paint(Graphics g) {
	      panelImage = createImage(this.getWidth(), this.getHeight());
	      screenGraphic= panelImage.getGraphics();
	      screenDraw(screenGraphic);
	      g.drawImage(panelImage, 0, 0, null);
	}
	public void screenDraw(Graphics g) {
	       g.drawImage(backgroundImage, 0, 0, null);
	       paintComponents(g);
	       this.repaint();
}
}
