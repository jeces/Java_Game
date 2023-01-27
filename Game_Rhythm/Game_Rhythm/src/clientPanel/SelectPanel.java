package clientPanel;


import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.sound.midi.Track;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import client.ClientUI;
import game.GameFrame;

import java.awt.Color;

public class SelectPanel extends JPanel{

	static final int PANEL_WIDTH= 1000;
	static final int PANEL_HEIGHT= 500;
	
	private Graphics screenGraphic;
	private Image panelImage;
	public Image selectedImage ;
	private Image backgroundImage;
	
	private ImageIcon leftButtonImage = new ImageIcon(getClass().getClassLoader().getResource("images/backButton.png"));
	private ImageIcon leftButtonedImage = new ImageIcon(getClass().getClassLoader().getResource("images/backButtoned.png"));
	private ImageIcon rightButtonImage = new ImageIcon(getClass().getClassLoader().getResource("images/nextButton.png"));
	private ImageIcon rightButtonedImage = new ImageIcon(getClass().getClassLoader().getResource("images/nextButtoned.png"));
	
	
	
	public boolean isRoomScreen = false;
	
	public JButton leftButton = new JButton(leftButtonImage);
	public JButton rightButton = new JButton(rightButtonImage);
	
	public Music selectedMusic;
	public ArrayList<clientPanel.Track> trackList = new ArrayList<>();
	public int nowSelected = 0;
	public ClientUI ui;
	
	public SelectPanel(ClientUI c) {
		setBorder(new LineBorder(new Color(0, 0, 0), 3));
 		setSize(1000, 500);
 		ui = c;
		trackList.add(new clientPanel.Track("vibeSelect.png", "vibeMain.jpg", "fallCut.mp3", "fall.mp3","VIBE - 가을타나봐"));
		trackList.add(new clientPanel.Track("buskerSelect.png", "buskerMain.jpg", "buskerCut.mp3", "busker.mp3","버스커버스커 - 벚꽃엔딩"));
		trackList.add(new clientPanel.Track("wuSelect.png", "wuMain.jpg", "wuCut.mp3", "wu_Nafla.mp3","WU -NAFLA"));
		trackList.add(new clientPanel.Track("paraSelect.jpg", "paraMain.jpg", "paraCut.mp3", "CODE KUNST - PARACHUTE.mp3","CODE KUNST - PARACHUTE"));
		selectTrack(nowSelected);
		leftButton.setBounds(12, 201, 96, 72);
		setBackground(new Color(255, 0, 0, 0));
		
	
		leftButton.setBorderPainted(false);
		leftButton.setContentAreaFilled(false);
		leftButton.setFocusPainted(false);
		leftButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				leftButton.setIcon(leftButtonedImage);
				leftButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				//버튼 음악 추가
			}

			@Override
			public void mouseExited(MouseEvent e) {
				leftButton.setIcon(leftButtonImage);
				leftButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}
			
		});
		rightButton.setBounds(642, 201, 92, 68);
		rightButton.setBorderPainted(false);
		rightButton.setContentAreaFilled(false);
		rightButton.setFocusPainted(false);
		rightButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				rightButton.setIcon(rightButtonedImage);
				rightButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				//버튼 음악 추가
			}

			@Override
			public void mouseExited(MouseEvent e) {
				rightButton.setIcon(rightButtonImage);
				rightButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}
			
		});
		rightButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ui.net.sendChangeMusicRequest("right");
				
			}
		});
		leftButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ui.net.sendChangeMusicRequest("left");
			}
		});
		setLayout(null);
		add(leftButton);
		add(rightButton);
		
	}
	
	public void paint(Graphics g) {
		panelImage = createImage(PANEL_WIDTH, PANEL_HEIGHT);
		screenGraphic= panelImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(panelImage, 0, 0, null);
	}
	public void screenDraw(Graphics g) {
		g.drawImage(backgroundImage, 0, 0, null);
		
		g.drawImage(selectedImage, 200, 100, null);
		
		paintComponents(g);
		this.repaint();
	}
	public void selectTrack(int nowSelected) {   
		if(selectedMusic != null) {
			selectedMusic.close();
		}
		selectedImage = new ImageIcon(getClass().getClassLoader().getResource("images/"+trackList.get(nowSelected).getStartImage())).getImage();
		selectedMusic = new Music(trackList.get(nowSelected).getStartMusic(), true);
		selectedMusic.start();
	}
	
	public void selectLeft() {
		if(nowSelected ==0)
			nowSelected=trackList.size()-1;
		else
			nowSelected--;
		selectTrack(nowSelected);
	}
	public void selectRight() {
		if(nowSelected == trackList.size()-1)
			nowSelected=0;
		else
			nowSelected++;
		selectTrack(nowSelected);
	}
}
