package game;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import client.ClientUI;
import clientPanel.SelectPanel;

public class GameFrame extends JFrame {
	static final int GAME_WIDTH = 1280;
	static final int GAME_HIDTH = 800;
	private boolean isGameScreen = true;

	private Graphics screenGraphic;
	private Image frameImage;
	private Image selectedImage;
	private Image gameImage;
	public ClientUI sp;
	public static Game game;
	public boolean twouser;
	public GameFrame(ClientUI c) {
		this.sp = c;

		game = new Game(sp.pnMusicChoice.p.trackList.get(sp.pnMusicChoice.p.nowSelected).getTitleName(),
				sp.pnMusicChoice.p.trackList.get(sp.pnMusicChoice.p.nowSelected).getGameMusic(), this);
		game.start();
		twouser = false;
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(GAME_WIDTH, GAME_HIDTH);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setFocusable(true);
		addKeyListener(new KeyListener());
		gameImage = new ImageIcon(getClass().getClassLoader().getResource("images/" + sp.pnMusicChoice.p.trackList.get(sp.pnMusicChoice.p.nowSelected).getGameImage())).getImage();
		
	}

	public void paint(Graphics g) {
		frameImage = createImage(GAME_WIDTH, GAME_HIDTH);
		screenGraphic = frameImage.getGraphics();
		screenDraw((Graphics2D) screenGraphic);
		g.drawImage(frameImage, 0, 0, null);
	}

	public void screenDraw(Graphics2D g) {
		g.drawImage(gameImage, 0, 0, null);
		if (isGameScreen) {
			game.screenDraw(g);
		}
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.repaint();
	}
}
