package clientPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class LoadingPanel extends JPanel {
	private Graphics screenGraphic;
	private Image panelImage;
	private Image selectedImage;
	private Image backgroundImage;

	public LoadingPanel() {
		setOpaque(false);
		setSize(320, 320);

		setLayout(null);
		try {
			// backgroundImage = ImageIO.read(getClass().getResource("imge/gg2.gif"));
			backgroundImage = new ImageIcon(getClass().getResource("imge/gg2.gif")).getImage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// backgroundImage = new
		// ImageIcon(getClass().getResource("imge/gg.gif")).getImage();
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(backgroundImage, 0, 0, this);
		this.repaint();
	}

	
	/*
	public void paint(Graphics g) {
		panelImage = createImage(this.getWidth(), this.getHeight());
		screenGraphic = panelImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(panelImage, 0, 0, null);
	}

	public void screenDraw(Graphics g) {
		g.drawImage(backgroundImage, 0, 0, null);
		paintComponents(g);
		this.repaint();
	}
	*/
	
	
}