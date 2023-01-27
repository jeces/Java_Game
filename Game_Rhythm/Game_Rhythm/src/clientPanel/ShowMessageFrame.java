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

public class ShowMessageFrame extends JFrame{
	public JTextField tfId;
	public JButton btOK;
	public JTextArea tfMessage;
	public JLabel lbText;
	public ShowMessageFrame() {
		setSize(450, 300);
		setTitle("\uBA54\uC138\uC9C0");
		getContentPane().setLayout(null);
		
		lbText = new JLabel("\uBC1B\uB294 \uC0AC\uB78C : ");
		lbText.setHorizontalAlignment(SwingConstants.RIGHT);
		lbText.setBounds(29, 35, 74, 15);
		getContentPane().add(lbText);
		
		tfId = new JTextField();
		tfId.setEditable(false);
		tfId.setBounds(115, 35, 116, 21);
		getContentPane().add(tfId);
		tfId.setColumns(10);
		setLocationRelativeTo(null);
		JLabel label = new JLabel("\uB0B4\uC6A9 : ");
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setBounds(39, 71, 74, 15);
		getContentPane().add(label);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(49, 96, 329, 108);
		getContentPane().add(scrollPane);
		
		tfMessage = new JTextArea();
		tfMessage.setEditable(false);
		tfMessage.setLineWrap(true);
		scrollPane.setViewportView(tfMessage);
		
		btOK = new JButton("\uD655\uC778");
		btOK.setBounds(162, 228, 97, 23);
		getContentPane().add(btOK);
		
		btOK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});

	}
}
