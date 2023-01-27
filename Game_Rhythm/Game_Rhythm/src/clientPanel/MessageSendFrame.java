package clientPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import handler.BtSendMessageHandler;

public class MessageSendFrame extends JFrame{
	public JTextField tfId;
	public JButton btSend;
	public JButton btCancel;
	public JTextArea tfMessage;
	public MessageSendFrame() {
		setSize(450, 300);
		setTitle("\uBA54\uC2DC\uC9C0 \uBCF4\uB0B4\uAE30");
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\uC218\uC2E0\uC790 : ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setBounds(52, 35, 51, 15);
		getContentPane().add(lblNewLabel);
		
		tfId = new JTextField();
		tfId.setEditable(false);
		tfId.setBounds(104, 32, 116, 21);
		getContentPane().add(tfId);
		tfId.setColumns(10);

		setLocationRelativeTo(null);
		JLabel label = new JLabel("\uB0B4\uC6A9 : ");
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setBounds(52, 71, 74, 15);
		getContentPane().add(label);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(49, 96, 329, 108);
		getContentPane().add(scrollPane);
		
		tfMessage = new JTextArea();
		tfMessage.setLineWrap(true);
		scrollPane.setViewportView(tfMessage);
		
		btSend = new JButton("\uBCF4\uB0B4\uAE30");
		btSend.setBounds(95, 228, 97, 23);
		getContentPane().add(btSend);
		
		btCancel = new JButton("\uCDE8\uC18C\uD558\uAE30");
		btCancel.setBounds(236, 228, 97, 23);
		getContentPane().add(btCancel);
		

		ActionListener bsmh = new BtSendMessageHandler(this);
		btSend.addActionListener(bsmh);
		
		btCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
	}
}
