package clientPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import client.ClientUI;
import handler.InviteUserHandler;
import javax.swing.border.LineBorder;

public class InviteUsetListFrame extends JFrame{
	public JButton btCancel;
	public JList userList;
	public InviteUsetListFrame() {
		setTitle("\uCD08\uB300\uD558\uAE30");
		setSize(400, 500);
		getContentPane().setLayout(null);
		
		btCancel = new JButton("\uCDE8\uC18C");
		btCancel.setBounds(139, 414, 97, 23);
		getContentPane().add(btCancel);
		
		Panel panel = new Panel();
		panel.setBounds(10, 10, 364, 373);
		getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);
		
		userList = new JList();
		userList.setBorder(new LineBorder(new Color(0, 0, 0)));
		userList.setSelectionBackground(Color.LIGHT_GRAY);
		scrollPane.setViewportView(userList);
		
		MouseListener smh = new InviteUserHandler(this);
		userList.addMouseListener(smh);
			
		btCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
		
	}
}
