package handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.ClientUI;
import clientPanel.MusicChoicePanel;

public class BtStartHandler implements ActionListener {
	MusicChoicePanel target;
	public BtStartHandler(MusicChoicePanel musicChoicePanel) {
		target = musicChoicePanel;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		target.ui.net.sendStartGameRequest();
	}
	
}
