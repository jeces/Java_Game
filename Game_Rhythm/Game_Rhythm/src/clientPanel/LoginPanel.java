package clientPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import client.ClientUI;
import handler.BtSingnUpHandler;

public class LoginPanel extends JPanel {
	public JTextField tfid;
	public JButton btlogin;
	public JButton btsign;
	public JPasswordField tfpw;
	public JLabel logo;
	private Graphics screenGraphic;
	private Image panelImage;
	private Image selectedImage ;
	private Image backgroundImage = new ImageIcon(getClass().getResource("imge/123.gif")).getImage();
	public LoginPanel() {
		init();
	}
	
	private void init() {
		setSize(496, 748);
		setLayout(null);
		
		logo = new JLabel("");
		logo.setBounds(125, 178, 243, 203);
		URL url;
			url = getClass().getResource("imge/logo.png");
		logo.setIcon(new ImageIcon(url));
		add(logo);
		
		tfid = new JTextField();
		tfid.setBounds(252, 453, 116, 24);
		add(tfid);
		tfid.setColumns(10);
		
		tfpw = new JPasswordField();
		tfpw.setBounds(252, 508, 116, 24);
		add(tfpw);
		
		JLabel lblNewLabel_1 = new JLabel("\uC544\uC774\uB514 : ");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setFont(new Font("«‘√ ∑“πŸ≈¡", Font.BOLD | Font.ITALIC, 15));
		lblNewLabel_1.setBounds(167, 455, 62, 18);
		add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("\uD328\uC2A4\uC6CC\uB4DC : ");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setFont(new Font("«‘√ ∑“πŸ≈¡", Font.BOLD | Font.ITALIC, 15));
		lblNewLabel_2.setBounds(138, 510, 91, 18);
		add(lblNewLabel_2);
		
		btlogin = new JButton("");
		URL url1 = getClass().getResource("imge/login.png");
		btlogin.setBounds(125, 600, 100, 33);
		btlogin.setIcon(new ImageIcon(url1));
		btlogin.setBorderPainted(false);
		btlogin.setFocusPainted(false);
		btlogin.setBackground(new Color(255, 0, 0, 0));
		add(btlogin);
		
		btsign = new JButton("");
		URL url2 = getClass().getResource("imge/signup.png");
		btsign.setBounds(289, 600, 100, 33);
		btsign.setIcon(new ImageIcon(url2));
		btsign.setBorderPainted(false);
		btsign.setFocusPainted(false);
		btsign.setBackground(new Color(255, 0, 0, 0));
		add(btsign);
		
		
		
		
		
		
		
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
