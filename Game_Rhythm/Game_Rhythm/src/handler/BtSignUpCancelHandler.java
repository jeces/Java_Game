package handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.ClientUI;

public class BtSignUpCancelHandler implements ActionListener {
	ClientUI ui;
	public BtSignUpCancelHandler(ClientUI c) {
		ui = c;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		ui.setTitle("Drop the beat!! - Login");
		ui.setSize(496, 748);
		ui.setContentPane(ui.pnLogin);
	}
}
