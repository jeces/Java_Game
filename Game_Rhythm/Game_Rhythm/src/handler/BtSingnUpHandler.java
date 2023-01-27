package handler;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.ClientUI;
import clientPanel.LoginPanel;

public class BtSingnUpHandler implements ActionListener{
	ClientUI ui;
	
	public BtSingnUpHandler(ClientUI c) {
		ui = c;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		ui.setTitle("Drop the beat!! - Signup");
		ui.setLocationRelativeTo(null);
		ui.setSize(450, 700);
		ui.setContentPane(ui.pnSignup);
	}
}
