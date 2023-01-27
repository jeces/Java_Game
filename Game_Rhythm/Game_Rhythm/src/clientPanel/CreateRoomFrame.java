package clientPanel;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import client.ClientUI;
import handler.BtCreateRoomFinishHandler;
import handler.BtRoomSettingChangeHandler;

import javax.swing.JRadioButton;

public class CreateRoomFrame extends JFrame{
	public JTextField tfTitle;
	public JButton btCreate;
	public JPasswordField tfPw;
	public ClientUI ui;
	public JRadioButton rbSecret;
	public JRadioButton rbTwoUser;
	public JRadioButton rbOneUser;
	public CreateRoomFrame(ClientUI c) {
		setTitle("\uBC29 \uB9CC\uB4E4\uAE30");
		ui = c;
		setSize(400,300);
		setLocation(400,400);
		getContentPane().setLayout(null);
		
		tfTitle = new JTextField();
		tfTitle.setBounds(182, 79, 116, 21);
		getContentPane().add(tfTitle);
		tfTitle.setColumns(10);
		c.setLocationRelativeTo(null);
		btCreate = new JButton("\uBC29 \uC0DD\uC131");
		btCreate.setBounds(140, 197, 97, 23);
		getContentPane().add(btCreate);
		
		JLabel lblNewLabel = new JLabel("\uBC29 \uC774\uB984 : ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(102, 82, 57, 15);
		getContentPane().add(lblNewLabel);
		
		tfPw = new JPasswordField();
		tfPw.setEnabled(false);
		tfPw.setEditable(false);
		tfPw.setBounds(183, 118, 115, 21);
		getContentPane().add(tfPw);
		
		JLabel lblNewLabel_1 = new JLabel("\uBE44\uBC00\uBC88\uD638 : ");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(92, 121, 67, 15);
		getContentPane().add(lblNewLabel_1);
		
		rbSecret = new JRadioButton("\uBE44\uBC00\uBC29");
		rbSecret.setBounds(159, 157, 121, 23);
		getContentPane().add(rbSecret);
		
		rbOneUser = new JRadioButton("1\uC778\uC6A9");
		rbOneUser.setBounds(182, 40, 62, 23);
		getContentPane().add(rbOneUser);
		
		rbTwoUser = new JRadioButton("2\uC778\uC6A9");
		rbTwoUser.setBounds(248, 40, 67, 23);
		getContentPane().add(rbTwoUser);
		
		JLabel label = new JLabel("\uCC38\uC5EC \uC778\uC6D0 : ");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(92, 44, 67, 15);
		getContentPane().add(label);
		
		ButtonGroup b = new ButtonGroup();
		b.add(rbOneUser);
		b.add(rbTwoUser);
		rbTwoUser.setSelected(true);
		rbOneUser.setFocusable(false);
		rbTwoUser.setFocusable(false);
		rbSecret.setFocusable(false);
		
		
		
		rbTwoUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				tfTitle.setEditable(true);
				tfPw.setEditable(false);
				rbSecret.setEnabled(true);
				String [] titles = new String[] { "페어플레이합시다.", "매너게임","일단 들어오세요.","스피드 한게임"};
				tfTitle.setText(titles[(int)(Math.random()*titles.length)]);
			}
		});
		rbOneUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				tfTitle.setText("1인용 방입니다.");
				tfTitle.setEditable(false);
				rbSecret.setEnabled(false);
			}
		});
		rbSecret.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(rbSecret.isSelected()) {
					tfPw.setEditable(true);
					tfPw.setEnabled(true);
				} else {
					tfPw.setEditable(false);
					tfPw.setEnabled(false);
				}
			}
		});
		
		
		
		ActionListener bcrfh = new BtCreateRoomFinishHandler(this);
		btCreate.addActionListener(bcrfh);
		
		tfTitle.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode() == 10) {
					btCreate.doClick();
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		tfPw.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode() == 10) {
					btCreate.doClick();
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
