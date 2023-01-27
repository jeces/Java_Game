package game;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class GameCounter extends Thread {
	Game target;
	int count;
	public GameCounter(Game g) {
		target = g;
		count = 4;
	}
	
	@Override
	public void run() {
		target.suspend();
		count--;
		while(true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(count == -1) {
				interrupt();
				target.resume();
				break;
			}
			count--;
		}
	}	
	public void screenDraw(Graphics2D g) {
		if(count == 3) {
			Image resultImage = new ImageIcon(getClass().getClassLoader().getResource("images/4.png")).getImage();
			g.drawImage(resultImage	,550, 300, null);
		} else if(count == 2) {
			Image resultImage = new ImageIcon(getClass().getClassLoader().getResource("images/2.png")).getImage();
			g.drawImage(resultImage	,550, 300, null);
		} else if(count == 1) {
			Image resultImage = new ImageIcon(getClass().getClassLoader().getResource("images/1.png")).getImage();
			g.drawImage(resultImage	,550, 300, null);
		} else if(count == 0) {
			Image resultImage = new ImageIcon(getClass().getClassLoader().getResource("images/start.png")).getImage();
			g.drawImage(resultImage	,500, 250, null);
		}
	}


}
