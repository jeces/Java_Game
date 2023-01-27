package clientPanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import client.ClientUI;
import handler.BtExpelUserHandler;
import handler.BtInviteUserHandler;
import handler.BtReadyHandler;
import handler.BtRoomLeaveHandler;
import handler.BtStartHandler;
import handler.BtTextInputHandler;
public class MusicChoicePanel extends JPanel {
	public JTextField txInput;
	public JTextField tfusernick1;
	public JTextField tfuserinfo1;
	public JTextField tfusernick2;
	public JTextField tfuserinfo2;
	public ClientUI ui;
	public SelectPanel p;
	public JButton btLeave;
	public JTextArea txArea;
	public JScrollPane scrollPane;
	public JButton btStart;
	public JToggleButton btReady;
	private Graphics screenGraphic;
	private Image panelImage;
	private Image selectedImage ;
	private Image backgroundImage = new ImageIcon(getClass().getResource("imge/gameroomimage.jpg")).getImage();
	public JLabel lbCrownLeft;
	public JLabel lbCrownRight;
	public JButton btInvite;
	public JButton btExpel;
	public JPanel rightPanel;
	public JButton btRoomSetting;
	public MusicChoicePanel(ClientUI c) {
		setAlignmentX(Component.RIGHT_ALIGNMENT);
		setBackground(new Color(204, 255, 51));
		ui = c;
		setLayout(null);
		setVisible(true);
		ui.setLocationRelativeTo(null);
		
		setSize(1400,749);
		scrollPane = new JScrollPane();
		scrollPane.setBackground(new Color(255, 153, 255));
		scrollPane.setBounds(293, 511, 769, 123);
		add(scrollPane);
		
		p = new SelectPanel(ui);
		p.setOpaque(false);
		p.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		add(p);
		p.setBounds(293,10,769,481);
		p.setBackground(new Color(255, 0, 0, 0));
		
		txArea = new JTextArea();
		txArea.setEditable(false);
		txArea.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		txArea.setForeground(Color.BLACK);
		txArea.setBackground(Color.LIGHT_GRAY);
		txArea.setLineWrap(true);
		scrollPane.setViewportView(txArea);
		txArea.setFont(new Font("바탕", Font.BOLD, 16));
		
		txInput = new JTextField();
		txInput.setBackground(new Color(255, 255, 255));
		txInput.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		txInput.setBounds(293, 644, 769, 27);
		add(txInput);
		txInput.setColumns(10);
		
		btLeave = new JButton("");
		URL url3 = getClass().getResource("imge/back.jpg");
		btLeave.setIcon(new ImageIcon(url3));
		btLeave.setBorderPainted(false);
		btLeave.setFocusPainted(false);
		btLeave.setBackground(new Color(255, 0, 0, 0));
		btLeave.setBounds(29, 556, 224, 109);
		add(btLeave);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 153, 0));
		panel_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_1.setBounds(44, 180, 195, 175);
		add(panel_1);
		panel_1.setLayout(null);
		
		tfusernick1 = new JTextField();
		tfusernick1.setEditable(false);
		tfusernick1.setHorizontalAlignment(SwingConstants.CENTER);
		tfusernick1.setBounds(12, 100, 173, 24);
		panel_1.add(tfusernick1);
		tfusernick1.setColumns(10);
		
		tfuserinfo1 = new JTextField();
		tfuserinfo1.setEditable(false);
		tfuserinfo1.setHorizontalAlignment(SwingConstants.CENTER);
		tfuserinfo1.setBounds(12, 134, 173, 24);
		panel_1.add(tfuserinfo1);
		tfuserinfo1.setColumns(10);
		
		lbCrownLeft = new JLabel("");
		lbCrownLeft.setBounds(70, 30, 60, 60);
		panel_1.add(lbCrownLeft);
		
		rightPanel = new JPanel();
		rightPanel.setBackground(new Color(255, 153, 0));
		rightPanel.setForeground(new Color(0, 0, 0));
		rightPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		rightPanel.setBounds(1149, 180, 195, 175);
		add(rightPanel);
		rightPanel.setLayout(null);
		
		tfusernick2 = new JTextField();
		tfusernick2.setEditable(false);
		tfusernick2.setHorizontalAlignment(SwingConstants.CENTER);
		tfusernick2.setBounds(12, 107, 173, 24);
		rightPanel.add(tfusernick2);
		tfusernick2.setColumns(10);
		
		tfuserinfo2 = new JTextField();
		tfuserinfo2.setEditable(false);
		tfuserinfo2.setHorizontalAlignment(SwingConstants.CENTER);
		tfuserinfo2.setBounds(12, 141, 173, 24);
		rightPanel.add(tfuserinfo2);
		tfuserinfo2.setColumns(10);
		
		lbCrownRight = new JLabel("");
		lbCrownRight.setBounds(71, 36, 60, 60);
		rightPanel.add(lbCrownRight);
		
		btExpel = new JButton("X");
		btExpel.setEnabled(false);
		btExpel.setBackground(Color.RED);
		btExpel.setForeground(Color.WHITE);
		btExpel.setMargin(new Insets(0, 0, 0, 0));
		btExpel.setBounds(160, 10, 25, 24);
		rightPanel.add(btExpel);
		
		btStart = new JButton("");
		btStart.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btStart.setOpaque(false);
			URL url1 = getClass().getResource("imge/start.png");
			btStart.setIcon(new ImageIcon(url1));
			btStart.setBorderPainted(false);
			btStart.setFocusPainted(false);
			btStart.setBackground(new Color(255, 0, 0, 0));
			btStart.setBounds(1157, 545, 187, 53);
			add(btStart);
		
		btReady = new JToggleButton("");
			URL url2 = getClass().getResource("imge/ready.png");
			btReady.setIcon(new ImageIcon(url2));
			btReady.setBorderPainted(false);
			btReady.setFocusPainted(false);
			btReady.setBackground(new Color(255, 0, 0, 0));
			btReady.setBounds(1157, 620, 187, 51);
			add(btReady);
		
		ActionListener brlh = new BtRoomLeaveHandler(this);
		btLeave.addActionListener(brlh);
		
		ActionListener btih = new BtTextInputHandler(this);
		txInput.addActionListener(btih);
		
		ActionListener brh = new BtReadyHandler(this);
		btReady.addActionListener(brh);
		
		ActionListener bsgh = new BtStartHandler(this);
		
		btInvite = new JButton("I N V I T E");
		btInvite.setBackground(new Color(250, 128, 114));
		btInvite.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 18));
		btInvite.setBounds(44, 95, 195, 53);
		add(btInvite);
		
		btRoomSetting = new JButton("Room Setting Change");
		btRoomSetting.setBackground(new Color(250, 128, 114));
		btRoomSetting.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 18));
		btRoomSetting.setBounds(44, 32, 195, 53);
		add(btRoomSetting);
		btStart.addActionListener(bsgh);
		
		ActionListener biuh = new BtInviteUserHandler();
		btInvite.addActionListener(biuh);
		
		ActionListener bruh = new BtExpelUserHandler();
		btExpel.addActionListener(bruh);
		btInvite.setFocusPainted(false);
		btRoomSetting.setFocusPainted(false);
		
		btRoomSetting.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				RoomSettingChangeFrame r = new RoomSettingChangeFrame(c);
				String [] titles = new String[] { "페어플레이합시다.", "매너게임","일단 들어오세요.","스피드 한게임"};
				r.tfTitle.setText(titles[(int)(Math.random()*titles.length)]);
				r.setLocationRelativeTo(null);
				r.setVisible(true);
			}
		});
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
