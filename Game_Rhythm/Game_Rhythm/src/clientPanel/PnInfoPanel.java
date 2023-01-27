package clientPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import handler.BtShowMessageListHandler;
import javax.swing.border.LineBorder;
import java.awt.event.ActionEvent;

public class PnInfoPanel extends JPanel{
	private Graphics screenGraphic;
	private Image panelImage;
	private Image selectedImage ;
	private Image backgroundImage = new ImageIcon(getClass().getResource("imge/Info.gif")).getImage();
	public JLabel lbId;
	public JLabel lbState;
	public JButton btMessage;
	public PnInfoPanel(){
		setSize(200, 139);
		setBounds(30, 487, 192, 142);
		setLayout(null);
		
		lbId = new JLabel("");
		lbId.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 12));
		lbId.setHorizontalAlignment(SwingConstants.CENTER);
		lbId.setBounds(48, 83, 99, 22);
		add(lbId);
		
		lbState = new JLabel("");
		lbState.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 12));
		lbState.setHorizontalAlignment(SwingConstants.CENTER);
		lbState.setBounds(0, 107, 192, 25);
		add(lbState);
		
		btMessage = new JButton("");
		btMessage.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		btMessage.setMargin(new Insets(1, 1, 1, 1));
		btMessage.setBackground(Color.RED);
		btMessage.setBounds(150, 20, 30, 30);
		add(btMessage);
		btMessage.setFocusPainted(false);
		
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
