package handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.ClientUI;
import clientPanel.MusicChoicePanel;

public class BtReadyHandler implements ActionListener {
	MusicChoicePanel target;
	public BtReadyHandler(MusicChoicePanel musicChoicePanel) {
		target = musicChoicePanel;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		target.ui.net.sendReadyRequest();
	}
	
}
