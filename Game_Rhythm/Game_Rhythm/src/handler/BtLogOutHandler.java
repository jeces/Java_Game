package handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.ClientUI;

public class BtLogOutHandler implements ActionListener{
	ClientUI ui;
	public BtLogOutHandler(ClientUI c) {
		ui = c;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		ui.net.sendLogoutRequest();
	}

}
