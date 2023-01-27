package handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.ClientUI;

public class BtSignupFinishHandler implements ActionListener {
	ClientUI ui;
	public BtSignupFinishHandler(ClientUI c) {
		ui = c;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String nick = ui.pnSignup.tfId.getText().trim();
		String name = ui.pnSignup.tfname.getText().trim();
		String pass = String.valueOf(ui.pnSignup.tfpw.getPassword());
		String repass = String.valueOf(ui.pnSignup.tfrpw.getPassword());
		ui.net.sendCreateRequest(nick, pass, name, repass);
	}
	
}
