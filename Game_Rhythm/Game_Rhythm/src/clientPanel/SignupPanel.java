package clientPanel;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.SwingConstants;
import java.awt.Color;

public class SignupPanel extends JPanel{
	public JTextField tfname;
	public JPasswordField tfpw;
	public JPasswordField tfrpw;
	public JTextField tfId;
	public JTextArea textArea;
	public JRadioButton rbAgree;
	public JRadioButton rbdisAgree;
	public JButton btCreate;
	public JButton btCancel;
	private Graphics screenGraphic;
	private Image panelImage;
	private Image selectedImage ;
	private Image backgroundImage = new ImageIcon(getClass().getResource("imge/123.gif")).getImage();
	public SignupPanel() {
		setSize(450, 700);
		setLayout(null);
		
		tfId = new JTextField();
		tfId.setBounds(219, 163, 116, 24);
		add(tfId);
		tfId.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("\uD68C  \uC6D0  \uAC00  \uC785");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("«‘√ ∑“πŸ≈¡", Font.BOLD | Font.ITALIC, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(109, 63, 226, 58);
		add(lblNewLabel);
		
		tfname = new JTextField();
		tfname.setBounds(219, 197, 116, 24);
		add(tfname);
		tfname.setColumns(10);
		
		tfpw = new JPasswordField();
		tfpw.setBounds(219, 231, 116, 24);
		add(tfpw);
		
		tfrpw = new JPasswordField();
		tfrpw.setBounds(219, 265, 116, 24);
		add(tfrpw);
		
		btCreate = new JButton("\uC644\uB8CC");
		btCreate.setBounds(87, 600, 105, 27);
		add(btCreate);
		
		btCancel = new JButton("\uCDE8\uC18C");
		btCancel.setBounds(249, 600, 105, 27);
		add(btCancel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(48, 365, 342, 115);
		add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		
	      textArea.setText("\uC11C\uBE44\uC2A4 \uC774\uC6A9\uC57D\uAD00\r\n\r\n\uC81C1\uC870 (\uC774\uC6A9\uACC4\uC57D\uC758 \uC131\uB9BD)\r\n\uC774\uC6A9\uACC4\uC57D\uC740 \uC774\uC6A9\uC790\uC758 \uC57D\uAD00\uB0B4\uC6A9\uC5D0 \uB300\uD55C \uB3D9\uC758\uC640 \uC774\uC6A9\uC790\uC758 \uC774\uC6A9\uC2E0\uCCAD\uC5D0 \uB300\uD55C  \uC2B9\uB099\uC73C\uB85C \uC131\uB9BD\uD569\uB2C8\uB2E4.\r\n\r\n\uC81C2\uC870 (\uC774\uC6A9\uC2E0\uCCAD)\r\n\uC774\uC6A9\uC2E0\uCCAD\uC740 \uC11C\uBE44\uC2A4\uC758 \uD68C\uC6D0\uC815\uBCF4 \uD654\uBA74\uC5D0\uC11C \uC774\uC6A9\uC790\uAC00  \uC694\uAD6C\uD558\uB294 \uAC00\uC785\uC2E0\uCCAD \uC591\uC2DD\uC5D0 \uAC1C\uC778\uC758 \uC2E0\uC0C1\uC815\uBCF4\uB97C \uAE30\uB85D\uD558\uB294 \uBC29\uC2DD\uC73C\uB85C \uC2E0\uCCAD\uD569\uB2C8\uB2E4.\r\n");
		
		ButtonGroup bg = new ButtonGroup();
		rbAgree = new JRadioButton("\uB3D9\uC758");
		rbAgree.setOpaque(false);
		rbAgree.setForeground(Color.WHITE);
		rbAgree.setBounds(138, 544, 71, 27);
		add(rbAgree);
		
		rbdisAgree = new JRadioButton("\uBE44\uB3D9\uC758");
		rbdisAgree.setOpaque(false);
		rbdisAgree.setForeground(Color.WHITE);
		rbdisAgree.setBounds(230, 544, 139, 27);
		add(rbdisAgree);
		
		bg.add(rbAgree);
		bg.add(rbdisAgree);
		
		JLabel rrag = new JLabel("\uC544\uC774\uB514 : ");
		rrag.setForeground(Color.WHITE);
		rrag.setHorizontalAlignment(SwingConstants.RIGHT);
		rrag.setFont(new Font("«‘√ ∑“πŸ≈¡", Font.BOLD | Font.ITALIC, 15));
		rrag.setBounds(126, 165, 62, 18);
		add(rrag);
		
		JLabel label = new JLabel("\uC774\uB984 : ");
		label.setForeground(Color.WHITE);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setFont(new Font("«‘√ ∑“πŸ≈¡", Font.BOLD | Font.ITALIC, 15));
		label.setBounds(126, 199, 62, 18);
		add(label);
		
		JLabel label_1 = new JLabel("\uBE44\uBC00\uBC88\uD638 : ");
		label_1.setForeground(Color.WHITE);
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setFont(new Font("«‘√ ∑“πŸ≈¡", Font.BOLD | Font.ITALIC, 15));
		label_1.setBounds(83, 233, 105, 18);
		add(label_1);
		
		JLabel label_2 = new JLabel("\uBE44\uBC00\uBC88\uD638 \uD655\uC778 : ");
		label_2.setForeground(Color.WHITE);
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setFont(new Font("«‘√ ∑“πŸ≈¡", Font.BOLD | Font.ITALIC, 15));
		label_2.setBounds(48, 267, 139, 18);
		add(label_2);
		
		JLabel label_3 = new JLabel("[ \uD68C\uC6D0\uC57D\uAD00 ]");
		label_3.setForeground(Color.WHITE);
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 17));
		label_3.setBounds(129, 311, 171, 44);
		add(label_3);
		
		JLabel lblNewLabel_1 = new JLabel("\u203B \uC57D\uAD00\uC744 \uC77D\uACE0 \uB3D9\uC758 \uBC84\uD2BC\uC744 \uB20C\uB7EC\uC8FC\uC138\uC694.");
		lblNewLabel_1.setForeground(Color.RED);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(48, 506, 342, 15);
		add(lblNewLabel_1);
		
		
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
