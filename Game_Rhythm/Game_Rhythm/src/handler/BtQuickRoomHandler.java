package handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.ClientUI;

public class BtQuickRoomHandler implements ActionListener {
	ClientUI ui;
	public BtQuickRoomHandler(ClientUI c) {
		ui = c;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		ui.net.sendQuickRoomRequest();
	}
	
}
