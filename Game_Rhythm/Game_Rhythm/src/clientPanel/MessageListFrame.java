package clientPanel;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.BorderLayout;
import javax.swing.JTextArea;

import client.ClientUI;
import handler.ShowMessageHandler;
import java.awt.Color;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;

public class MessageListFrame extends JFrame{
	public JButton btCancel;
	public JList messageList;
	public MessageListFrame() {
		setTitle("\uCABD\uC9C0\uD568");
		setSize(400, 500);
		getContentPane().setLayout(null);
		
		btCancel = new JButton("\uB4A4\uB85C\uAC00\uAE30");
		btCancel.setBounds(139, 414, 97, 23);
		getContentPane().add(btCancel);
		
		Panel panel = new Panel();
		panel.setBounds(10, 10, 364, 373);
		getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);
		
		messageList = new JList();
		messageList.setBorder(new LineBorder(new Color(0, 0, 0)));
		messageList.setSelectionBackground(Color.LIGHT_GRAY);
		scrollPane.setViewportView(messageList);
		
		MouseListener smh = new ShowMessageHandler(this);
		messageList.addMouseListener(smh);
			
		btCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
		
	}
}
