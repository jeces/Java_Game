package handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.ClientUI;
import clientPanel.InviteUsetListFrame;

public class BtInviteUserHandler implements ActionListener {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		ClientUI.net.sendInviteUserListRequest();
		
	}

}
