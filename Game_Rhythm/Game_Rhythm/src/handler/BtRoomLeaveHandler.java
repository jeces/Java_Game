package handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.ClientUI;
import clientPanel.MusicChoicePanel;

public class BtRoomLeaveHandler implements ActionListener{
	MusicChoicePanel target;
	public BtRoomLeaveHandler(MusicChoicePanel musicChoicePanel) {
		target = musicChoicePanel;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		target.ui.pnMusicChoice.p.isRoomScreen = false;
		target.ui.net.sendChatRequest(" ³ª°¬½À´Ï´Ù.");
		target.ui.net.sendLeaveRequest();
	}
	
}
