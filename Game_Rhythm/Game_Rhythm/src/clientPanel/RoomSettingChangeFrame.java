package clientPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import client.ClientUI;
import handler.BtRoomSettingChangeHandler;

public class RoomSettingChangeFrame extends JFrame{
	public JTextField tfTitle;
	public JButton btRoomSettingChange;
	public JPasswordField tfPw;
	public ClientUI ui;
	public JRadioButton rbSecret;
	public JRadioButton rbTwoUser;
	public JRadioButton rbOneUser;
	public RoomSettingChangeFrame(ClientUI c) {
		setTitle("\uBC29 \uC124\uC815 \uBCC0\uACBD");
		ui = c;
		setSize(400,300);
		setLocation(400,400);
		getContentPane().setLayout(null);
		
		tfTitle = new JTextField();
		tfTitle.setBounds(182, 79, 116, 21);
		getContentPane().add(tfTitle);
		tfTitle.setColumns(10);
		c.setLocationRelativeTo(null);
		btRoomSettingChange = new JButton("\uBC29 \uC124\uC815 \uBCC0\uACBD");
		btRoomSettingChange.setBounds(140, 200, 116, 23);
		getContentPane().add(btRoomSettingChange);
		
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
		rbOneUser.setBounds(182, 40, 67, 23);
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
		rbTwoUser.setFocusPainted(false);
		rbOneUser.setFocusPainted(false);
		rbSecret.setFocusPainted(false);
		
		ActionListener brsch = new BtRoomSettingChangeHandler(this);
		btRoomSettingChange.addActionListener(brsch);
		
		if(ui.pnMusicChoice.rightPanel.isVisible()) {
			rbTwoUser.setSelected(true);
		}
		
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
		
		
		tfTitle.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode() == 10) {
					btRoomSettingChange.doClick();
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
					btRoomSettingChange.doClick();
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
