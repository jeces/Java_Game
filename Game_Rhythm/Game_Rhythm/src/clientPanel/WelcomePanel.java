package clientPanel;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;

public class WelcomePanel extends  JPanel{
	public LoadingPanel pnLoading;
	private Graphics screenGraphic;
	private Image panelImage;
	private Image selectedImage ;
	private Image backgroundImage = new ImageIcon(getClass().getResource("imge/123.gif")).getImage();
	public WelcomePanel() {
		setSize(496, 748);
		setLayout(null);
		pnLoading = new LoadingPanel();
		pnLoading.setOpaque(false);
		pnLoading.setSize(320, 320);
		pnLoading.setLocation(132, 403);
		add(pnLoading);
		JLabel lblNewLabel = new JLabel("Welcome To ");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("³ª´®°íµñ", Font.BOLD, 37));
		lblNewLabel.setBounds(132, 191, 245, 75);
		add(lblNewLabel);
		
		JLabel lblDropTheBeat = new JLabel("Drop The Beat !!");
		lblDropTheBeat.setForeground(new Color(255, 255, 255));
		lblDropTheBeat.setHorizontalAlignment(SwingConstants.CENTER);
		lblDropTheBeat.setFont(new Font("³ª´®°íµñ", Font.BOLD, 37));
		lblDropTheBeat.setBounds(88, 276, 335, 75);
		add(lblDropTheBeat);
		
	}
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(backgroundImage, 0, 0, this);
		this.repaint();
	}
/*
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
*/
}
